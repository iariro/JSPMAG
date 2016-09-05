package kumagai.mag;

import java.io.*;
import java.nio.*;

/**
 * MAGファイル
 * @author kumagai
 */
public class MagFile
{
	private final byte [] bytes;

	public int screenMode;
	public int width;
	public int height;
	private int memoLength;
	private int startFlagA;
	private int startFlagB;
	private int startPixel;
	public int sizePixel;

	/**
	 * バイト配列の集約とヘッダ部の読み込み
	 * @param bytes MAGファイル内容を保持するバイト配列
	 */
	public MagFile(byte [] bytes)
		throws IOException
	{
		this.bytes = bytes;

		int offset = 0;

		// メモの終端を表す$1Aまでスキップ
		for ( ; bytes[offset] != 0x1a ; offset++)
		{
		}

		memoLength = offset;
		offset++;

		screenMode = bytes[offset + 3];
		width = ByteBuffer.wrap(bytes, offset + 8, 2).order(ByteOrder.LITTLE_ENDIAN).getShort() + 1;
		height = ByteBuffer.wrap(bytes, offset + 10, 2).order(ByteOrder.LITTLE_ENDIAN).getShort() + 1;
		startFlagA = offset + ByteBuffer.wrap(bytes, offset + 12, 4).order(ByteOrder.LITTLE_ENDIAN).getInt();
		startFlagB = offset + ByteBuffer.wrap(bytes, offset + 16, 4).order(ByteOrder.LITTLE_ENDIAN).getInt();
		startPixel = offset + ByteBuffer.wrap(bytes, offset + 24, 4).order(ByteOrder.LITTLE_ENDIAN).getInt();
		sizePixel = ByteBuffer.wrap(bytes, offset + 28, 4).order(ByteOrder.LITTLE_ENDIAN).getInt();
	}

	/**
	 * フラグAのバイト数を取得
	 * @return フラグAのバイト数
	 */
	public int getFlagALength()
	{
		return startFlagB - startFlagA;
	}

	/**
	 * フラグBのバイト数を取得
	 * @return フラグBのバイト数
	 */
	public int getFlagBLength()
	{
		return startPixel - startFlagB;
	}

	/**
	 * メモ文字列を取得
	 * @return メモ文字列
	 */
	public String getMemo() throws UnsupportedEncodingException
	{
		return new String(bytes, 8, memoLength - 8, "sjis");
	}

	/**
	 * パレット情報を取得
	 * @return パレット情報
	 */
	public byte [][] getPalette()
	{
		int offset = memoLength + 1;

		int screenMode = bytes[offset + 3];

		offset += 32;

		if (screenMode == 0)
		{
			// 16色モード

			byte [][] palette = new byte [16][];

			for (int i=0 ; i<16 ; i++)
			{
				palette[i] = new byte [3];

				// GRBをRGBに並べ替え
				palette[i][0] = bytes[offset + 1];
				palette[i][1] = bytes[offset + 0];
				palette[i][2] = bytes[offset + 2];
				offset += 3;
			}

			return palette;
		}
		else
		{
			return null;
		}
	}

	/**
	 * ビットマップ部を展開して取得
	 * @return ビットマップ部
	 */
	public BitmapAndPixelCount getBitmap()
	{
		// 横１ラインごとのフラグ格納バイト数
		int flagBytesPerLine = width / 4;

		int [] flags = new int [flagBytesPerLine * height];

		int flagOffset = 0;
		int offsetFlagB = 0;

		for (int i=0 ; i<startFlagB - startFlagA ; i++)
		{
			for (int j=0 ; j<8 ; j++)
			{
				if ((bytes[startFlagA + i] & (1 << (7 - j))) == 0)
				{
					// １バイトクリア

					flags[flagOffset] = 0;
					flagOffset++;
					flags[flagOffset] = 0;
					flagOffset++;
				}
				else
				{
					// フラグBを１バイト読み込み

					flags[flagOffset] = (bytes[startFlagB + offsetFlagB] & 0xf0) >> 4;
					flagOffset++;
					flags[flagOffset] = (bytes[startFlagB + offsetFlagB] & 0x0f);
					flagOffset++;

					offsetFlagB++;
				}
			}
		}

		// XORを計算
		for (int i=0 ; i<height-1 ; i++)
		{
			for (int j=0 ; j<flagBytesPerLine ; j++)
			{
				flags[flagBytesPerLine * (i + 1) + j] ^= flags[flagBytesPerLine * i + j];
			}
		}

		int [][] bitmap = new int [width][];

		for (int i=0 ; i<width ; i++)
		{
			bitmap[i] = new int [height];
		}

		int [] indexPixel = new int [flagBytesPerLine * height];

		int offsetPixel = 0;
		int x = 0;
		int y = 0;

		// フラグから圧縮データを展開
		for (int i=0 ; i<flags.length ; i++)
		{
			switch (flags[i])
			{
				case 0:
					indexPixel[i] = offsetPixel;
					offsetPixel += 2;
					break;
				case 1:
					indexPixel[i] = indexPixel[i - 1];
					break;
				case 2:
					indexPixel[i] = indexPixel[i - 2];
					break;
				case 3:
					indexPixel[i] = indexPixel[i - 4];
					break;
				case 4:
					indexPixel[i] = indexPixel[i - flagBytesPerLine];
					break;
				case 5:
					indexPixel[i] = indexPixel[i - flagBytesPerLine - 1];
					break;
				case 6:
					indexPixel[i] = indexPixel[i - flagBytesPerLine * 2];
					break;
				case 7:
					indexPixel[i] = indexPixel[i - flagBytesPerLine * 2 - 1];
					break;
				case 8:
					indexPixel[i] = indexPixel[i - flagBytesPerLine * 2 - 2];
					break;
				case 9:
					indexPixel[i] = indexPixel[i - flagBytesPerLine * 4];
					break;
				case 10:
					indexPixel[i] = indexPixel[i - flagBytesPerLine * 4 - 1];
					break;
				case 11:
					indexPixel[i] = indexPixel[i - flagBytesPerLine * 4 - 2];
					break;
				case 12:
					indexPixel[i] = indexPixel[i - flagBytesPerLine * 8];
					break;
				case 13:
					indexPixel[i] = indexPixel[i - flagBytesPerLine * 8 - 1];
					break;
				case 14:
					indexPixel[i] = indexPixel[i - flagBytesPerLine * 8 - 2];
					break;
				case 15:
					indexPixel[i] = indexPixel[i - flagBytesPerLine * 16];
					break;
			}

			// １ピクセルを４ドットに展開
			int [] paletteCodes = new int [4];

			paletteCodes[0] =
				(bytes[startPixel + indexPixel[i]] & 0xff) >> 4;
			paletteCodes[1] =
				(bytes[startPixel + indexPixel[i]] & 0xff) & 0xf;
			paletteCodes[2] =
				(bytes[startPixel + indexPixel[i] + 1] & 0xff) >> 4;
			paletteCodes[3] =
				(bytes[startPixel + indexPixel[i] + 1] & 0xff) & 0xf;

			for (int j=0 ; j<4 ; j++)
			{
				bitmap[x][y] = paletteCodes[j];

				x++;
				if (x >= width)
				{
					x = 0;
					y++;
				}
			}
		}

		int pixelCount = 0;
		int referCount = 0;
		for (int i=0 ; i<height ; i++)
		{
			for (int j=0 ; j<flagBytesPerLine ; j++)
			{
				if (flags[flagBytesPerLine * i + j] == 0)
				{
					pixelCount++;
				}
				else
				{
					referCount++;
				}
			}
		}

		return new BitmapAndPixelCount(bitmap, pixelCount, referCount);
	}
}
