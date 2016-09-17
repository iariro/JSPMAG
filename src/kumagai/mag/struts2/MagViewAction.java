package kumagai.mag.struts2;

import java.io.*;
import java.util.*;
import javax.servlet.*;
import org.apache.struts2.*;
import org.apache.struts2.convention.annotation.*;
import kumagai.mag.*;

/**
 * MAGファイル表示アクション。
 * @author kumagai
 */
@Namespace("/mag")
@Result(name="success", location="/mag/magview.jsp")
public class MagViewAction
{
	public String filename;
	public String memo;
	public int width;
	public int height;
	public String pixelCount;
	public String flagSize;
	public String compressRatio1;
	public String totalBytes;
	public String bmpSize;
	public String compressRatio2;
	public ArrayList<String> palette = new ArrayList<String>();
	public ArrayList<String> docFileLines;

	/**
	 * MAGファイル表示アクション。
	 * @return 処理結果
	 */
	@Action("magview")
	public String execute()
		throws Exception
	{
		ServletContext context = ServletActionContext.getServletContext();

		String magFolder = context.getInitParameter("MAGFolder");

		// MAGファイル読み込み
		File file = new File(magFolder, filename);

		byte [] bytes = new byte [(int)file.length()];
		FileInputStream fileInputStream = new FileInputStream(file);
		fileInputStream.read(bytes);
		fileInputStream.close();

		MagFile magFile = new MagFile(bytes);

		memo = magFile.getMemo();
		width = magFile.width;
		height = magFile.height;

		byte [][] palette = magFile.getPalette();
		for (int i=0 ; i<palette.length ; i++)
		{
			this.palette.add(
				String.format(
					"#%02x%02x%02x",
					palette[i][0],
					palette[i][1],
					palette[i][2]));
		}

		BitmapAndPixelCount bitmap = magFile.getBitmap();

		int pixelCount = bitmap.pixelCount;
		int flagSize = bitmap.pixelCount + bitmap.referCount;
		this.pixelCount = String.format("%,d", pixelCount);
		this.flagSize = String.format("%,d", flagSize);
		this.compressRatio1 = String.format("%2.2f", (float)pixelCount * 100 / (float)flagSize);

		int totalBytes = bytes.length;
		int bmpSize = 54 + 64 + (width * height / 2);
		this.totalBytes = String.format("%,d", totalBytes);
		this.bmpSize = String.format("%,d", bmpSize);
		this.compressRatio2 = String.format("%2.2f", (float)totalBytes * 100 / (float)bmpSize);

		// DOCファイル読み込み
		File docFile = new File(magFolder, filename.replace(".MAG", ".DOC"));

		if (docFile.isFile())
		{
			docFileLines.add(String.format("%s", docFile.getName()));

			BufferedReader reader =
				new BufferedReader(
					new InputStreamReader(
						new FileInputStream(docFile), "sjis"));

			docFileLines = new TextList(reader);

			reader.close();
		}
		else
		{
			docFileLines.add(String.format("%sなし", docFile.getName()));
		}

		return "success";
	}
}
