package kumagai.mag.servlet;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import kumagai.mag.*;

/**
 * MAG画像出力サーブレット。
 * @author kumagai
 */
public class MagToBmpServlet
	extends HttpServlet
{
	/**
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	protected void doGet
		(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException
	{
		request.setCharacterEncoding("utf-8");

		ServletContext context = getServletConfig().getServletContext();
		String magFolder = context.getInitParameter("MAGFolder");
		String filename = request.getParameter("filename");

		File file = new File(magFolder, filename);

		// MAG画像の読み込み
		byte [] bytes = new byte [(int)file.length()];
		FileInputStream fileInputStream = new FileInputStream(file);
		fileInputStream.read(bytes);
		fileInputStream.close();

		MagFile magFile = new MagFile(bytes);

		byte [][] palette = magFile.getPalette();
		int [][] bitmap = magFile.getBitmap();

		// 出力ストリームにBMP画像を出力
		BitmapWriter.writeBitmap16(
			magFile.width,
			magFile.height,
			palette,
			bitmap,
			response.getOutputStream());
	}
}
