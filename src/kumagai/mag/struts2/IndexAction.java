package kumagai.mag.struts2;

import java.io.*;
import java.util.*;
import javax.servlet.*;
import org.apache.struts2.*;
import org.apache.struts2.convention.annotation.*;
import kumagai.mag.*;

/**
 * ファイルリスト表示アクション。
 * @author kumagai
 */
@Namespace("/mag")
@Result(name="success", location="/mag/index.jsp")
public class IndexAction
{
	public String folder;
	public ArrayList<MagFileListEntry> files = new ArrayList<MagFileListEntry>();

	/**
	 * ファイルリスト表示アクション。
	 * @return 処理結果
	 */
	@Action("index")
	public String execute()
		throws Exception
	{
		ServletContext context = ServletActionContext.getServletContext();

		folder = context.getInitParameter("MAGFolder");

		for (String filename : new File(folder).list())
		{
			if (filename.endsWith("MAG"))
			{
				File file = new File(folder, filename);
				byte [] bytes = new byte [(int)file.length()];
				FileInputStream fileInputStream = new FileInputStream(file);
				fileInputStream.read(bytes);
				fileInputStream.close();

				MagFile magFile = new MagFile(bytes);
				String memo = magFile.getMemo();

				files.add(new MagFileListEntry(filename, memo, magFile.screenMode));
			}
		}

		return "success";
	}
}
