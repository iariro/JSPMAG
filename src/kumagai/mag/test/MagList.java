package kumagai.mag.test;

import java.io.*;

import kumagai.mag.BitmapWriter;
import kumagai.mag.MagFile;
import kumagai.mag.SvgWriter;

/**
 * テストコード
 * @author kumagai
 */
public class MagList
{
	/**
	 * テストコード
	 * @param args [0]=フォルダまたはファイルパス
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException
	{
		if (args.length >= 1)
		{
			File file = new File(args[0]);

			if (file.isDirectory())
			{
				for (String path : file.list())
				{
					if (path.endsWith("MAG"))
					{
						readMagAndWriteSvg(new File(args[0], path));
					}
				}
			}
			else
			{
				readMagAndWriteBitmap(file);
			}
		}
	}

	/**
	 * MAG画像をBMPに変換
	 * @param file ファイル
	 */
	private static void readMagAndWriteBitmap(File file) throws IOException
	{
		String filename = file.getName().split("\\.")[0];

		byte [] bytes = new byte [(int)file.length()];
		FileInputStream fileInputStream = new FileInputStream(file);
		fileInputStream.read(bytes);
		fileInputStream.close();

		MagFile magFile = new MagFile(bytes);

		byte [][] palette = magFile.getPalette();
		int [][] bitmap = magFile.getBitmap();

		FileOutputStream stream = new FileOutputStream(filename + ".bmp");

		BitmapWriter.writeBitmap16
			(magFile.width, magFile.height, palette, bitmap, stream);

		stream.close();
		System.out.printf("%s written\n", filename);
	}

	/**
	 * MAG画像をSVGファイルに変換
	 * @param file
	 * @throws IOException
	 */
	private static void readMagAndWriteSvg(File file) throws IOException
	{
		String filename = file.getName().split("\\.")[0];

		byte [] bytes = new byte [(int)file.length()];
		FileInputStream fileInputStream = new FileInputStream(file);
		fileInputStream.read(bytes);
		fileInputStream.close();

		MagFile magFile = new MagFile(bytes);

		byte [][] palette = magFile.getPalette();
		int [][] bitmap = magFile.getBitmap();

		int scale = 1;

		File outfile = new File(filename + ".svg");
		PrintWriter pw =
			new PrintWriter(new BufferedWriter(new FileWriter(outfile)));

		String memo = magFile.getMemo();
		SvgWriter.writeSvg
			(filename, memo, magFile.width, magFile.height, palette, bitmap, scale, pw);
		pw.close();

		System.out.printf("%s written\n", filename);
	}
}
