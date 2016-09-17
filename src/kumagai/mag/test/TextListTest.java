package kumagai.mag.test;

import java.io.*;
import junit.framework.*;
import kumagai.mag.*;

public class TextListTest
	extends TestCase
{
	public void test1()
		throws IOException
	{
		BufferedReader reader =
			new BufferedReader(
				new InputStreamReader(
					new FileInputStream("C:\\Users\\kumagai\\Pictures\\temp\\MAG\\I_BUNY01.doc"), "sjis"));

		TextList list = new TextList(reader);
		reader.close();

		for (String line : list)
		{
			System.out.println(line);
		}
	}
}
