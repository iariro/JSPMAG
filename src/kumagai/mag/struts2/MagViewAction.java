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
	public int pixelCount;
	public int flagSize;
	public String compressRatio1;
	public int totalBytes;
	public int bmpSize;
	public String compressRatio2;
	public ArrayList<String> docFileLines = new ArrayList<String>();

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

		File file = new File(magFolder, filename);

		byte [] bytes = new byte [(int)file.length()];
		FileInputStream fileInputStream = new FileInputStream(file);
		fileInputStream.read(bytes);
		fileInputStream.close();

		MagFile magFile = new MagFile(bytes);

		memo = magFile.getMemo();
		width = magFile.width;
		height = magFile.height;

		BitmapAndPixelCount bitmap = magFile.getBitmap();

		pixelCount = bitmap.pixelCount;
		flagSize = bitmap.pixelCount + bitmap.referCount;
		compressRatio1 = String.format("%2.2f", (float)pixelCount * 100 / (float)flagSize);
		totalBytes = bytes.length;
		bmpSize = 54 + 64 + (width * height / 2);
		compressRatio2 = String.format("%2.2f", (float)totalBytes * 100 / (float)bmpSize);

		File docFile = new File(magFolder, filename.replace(".MAG", ".DOC"));

		if (docFile.isFile())
		{
			docFileLines.add(String.format("%s", docFile.getName()));

			BufferedReader reader =
				new BufferedReader(
					new InputStreamReader(
						new FileInputStream(docFile), "sjis"));

			String line;

			while ((line = reader.readLine()) != null)
			{
				docFileLines.add(line);
			}

			reader.close();
		}
		else
		{
			docFileLines.add(String.format("%sなし", docFile.getName()));
		}

		return "success";
	}
}
