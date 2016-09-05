package kumagai.mag;

import java.io.*;

/**
 * SVG書き出し。
 * @author kumagai
 */
public class SvgWriter
{
	/**
	 * SVG書き出し。
	 * @param filename ファイル名（拡張子を除く）
	 * @param memo メモ情報
	 * @param palette パレット
	 * @param bitmap ビットマップ
	 * @param scale スケール
	 * @param pw 出力ファイル
	 */
	public static void writeSvg(String filename, String memo, int width, int height,
			byte[][] palette, int[][] bitmap, int scale, PrintWriter pw)
	{
		memo = memo.replace("<", "&lt;");
		memo = memo.replace(">", "&gt;");

		pw.println("<svg xmlns=\"http://www.w3.org/2000/svg\">");
		pw.printf("<title>%s</title>\n", filename);
		pw.printf(
			"<text font-family=\"Dotum\" x=\"10\" y=\"30\">%s %s</text>\n",
			filename,
			memo);

		for (int i=0 ; i<16 ; i++)
		{
			pw.printf(
				"<rect x=\"%d\" y=\"%d\" width=\"%d\" height=\"%d\" fill=\"#%s\"/>\n",
				800,
				50 + i * 20,
				50,
				18,
				String.format(
					"%02x%02x%02x",
					palette[i][0],
					palette[i][1],
					palette[i][2]));
		}

		for (int i=0 ; i<height ; i++)
		{
			for (int j=0 ; j<width ; j++)
			{
				int rectwidth;

				for (rectwidth=1 ; (j + rectwidth < width) && (bitmap[j][i] == bitmap[j + rectwidth][i]) ; width++)
				{
				}

				pw.printf(
					"<rect x=\"%d\" y=\"%d\" width=\"%d\" height=\"%d\" fill=\"#%s\"/>\n",
					50 + j * scale,
					50 + i * scale,
					scale * rectwidth,
					scale,
					String.format(
						"%02x%02x%02x",
						palette[bitmap[j][i]][0],
						palette[bitmap[j][i]][1],
						palette[bitmap[j][i]][2]));

				j += rectwidth - 1;
			}
		}

		pw.println("</svg>");
	}
}
