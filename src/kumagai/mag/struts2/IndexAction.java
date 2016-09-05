package kumagai.mag.struts2;

import java.io.*;
import java.util.*;
import javax.servlet.*;
import org.apache.struts2.*;
import org.apache.struts2.convention.annotation.*;

/**
 * ファイルリスト表示アクション。
 * @author kumagai
 */
@Namespace("/mag")
@Result(name="success", location="/mag/index.jsp")
public class IndexAction
{
	public String folder;
	public ArrayList<String> files = new ArrayList<String>();

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

		for (String file : new File(folder).list())
		{
			if (file.endsWith("MAG"))
			{
				files.add(file);
			}
		}

		return "success";
	}
}
