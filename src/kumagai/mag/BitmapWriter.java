package kumagai.mag;

import java.io.*;
import java.nio.*;

/**
 * BMPへの書き出し
 * @author kumagai
 */
public class BitmapWriter
{
	/**
	 * BMPへの書き出し
	 * @param width 横幅
	 * @param height 縦幅
	 * @param palette パレット情報
	 * @param bitmap ビットマップ情報
	 * @param stream 出力ストリーム
	 */
	static public void writeBitmap16(int width, int height, byte[][] palette,
		byte[][] bitmap, OutputStream stream)
		throws IOException
	{
		// Bitmapファイルフォーマット
		// http://www.umekkii.jp/data/computer/file_format/bitmap.cgi

		//[0] 	bfType 	2byte 	unsigned int 	ファイルタイプ 	'BM'
		stream.write((byte)'B');
		stream.write((byte)'M');
		//[2] 	bfSize 	4byte 	unsigned long 	ファイルサイズ[byte]
		stream.write(ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN).putInt(width * height / 2).array());
		//[6] 	bfReserved1 	2byte 	unsigned int 	予約領域１ 	常に0
		stream.write(0);
		stream.write(0);
		//[8] 	bfReserved2 	2byte 	unsigned int 	予約領域２ 	常に0
		stream.write(0);
		stream.write(0);
		//[10] 	bfOffBits 	4byte 	unsigned long 	ファイル先頭から画像データまでのオフセット[byte]
		stream.write(ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN).putInt(0x76).array());

		//情報ヘッダ [BITMAPINFOHEADER] -Windows
		//[14] 	biSize 	4byte 	unsigned long 	情報ヘッダサイズ[byte] 	40
		stream.write(ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN).putInt(40).array());
		//[18] 	biWidth 	4byte 	long 	画像の幅[ピクセル]
		stream.write(ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN).putInt(width).array());
		//[22] 	biHeight 	4byte 	long 	画像の高さ[ピクセル]
		stream.write(ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN).putInt(height).array());
		//[26] 	biPlanes 	2byte 	unsigned int 	プレーン数 	常に1
		stream.write(ByteBuffer.allocate(2).order(ByteOrder.LITTLE_ENDIAN).putShort((short)1).array());
		//[28] 	biBitCount 	2byte 	unsigned int 	色ビット数[bit] 	1,4,8,(16),24,32
		stream.write(ByteBuffer.allocate(2).order(ByteOrder.LITTLE_ENDIAN).putShort((short)4).array());
		//[30] 	biCompression 	4byte 	unsigned long 	圧縮形式 	0,1,2,3
		stream.write(ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN).putInt(0).array());
		//[34] 	biSizeImage 	4byte 	unsigned long 	画像データサイズ[byte]
		stream.write(ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN).putInt(width * height / 2).array());
		//[38] 	biXPixPerMeter 	4byte 	long 	水平解像度[dot/m] 	0の場合もある
		stream.write(ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN).putInt(0).array());
		//[42] 	biYPixPerMeter 	4byte 	long 	垂直解像度[dot/m] 	0の場合もある
		stream.write(ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN).putInt(0).array());
		//[46] 	biClrUsed 	4byte 	unsigned long 	格納パレット数[使用色数] 	0の場合もある
		stream.write(ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN).putInt(0).array());
		//[50] 	biCirImportant 	4byte 	unsigned long 	重要色数 	0の場合もある
		stream.write(ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN).putInt(0).array());

		// パレットデータ出力
		for (int i=0 ; i<palette.length ; i++)
		{
			stream.write(palette[i][2]);
			stream.write(palette[i][1]);
			stream.write(palette[i][0]);
			stream.write(0);
		}

		// 画像データ出力
		for (int i=0 ; i<height ; i++)
		{
			for (int j=0 ; j<width ; j+=2)
			{
				stream.write(
					(bitmap[j][height - i - 1] << 4) +
					bitmap[j + 1][height - i - 1]);
			}
		}
	}
}
