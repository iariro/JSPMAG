package kumagai.mag.struts2;

import java.io.*;
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

		return "success";
	}
}
