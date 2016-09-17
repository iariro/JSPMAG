package kumagai.mag;

import java.io.*;
import java.util.*;

/**
 * 文字列リスト
 * @author kumagai
 */
public class TextList
	extends ArrayList<String>
{
	/**
	 * 文字列リストを構築
	 * EOFを除外可能
	 * @param reader 文字列リーダ
	 */
	public TextList(BufferedReader reader)
		throws IOException
	{
		String line;

		while ((line = reader.readLine()) != null)
		{
			if (line.length() == 1 && line.charAt(0) == 0x1a)
			{
				// EOFのみの行
			}
			else
			{
				// それ以外

				add(line);
			}
		}
	}
}
