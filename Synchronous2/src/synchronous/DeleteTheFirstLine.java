/**
 * 
 */
package synchronous;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author           Administrator
 * @copyright        wgcwgc
 * @date             2016年4月28日
 * @time             下午2:30:56
 * @project_name     Synchronous
 * @package_name     synchronous
 * @file_name        DeleteTheFirstLine.java
 * @type_name        DeleteTheFirstLine
 * @enclosing_type   
 * @tags             
 * @todo             
 * @others           
 *
 */

public class DeleteTheFirstLine
{
String filepath = "";
	
	public DeleteTheFirstLine(String filepath)
	{
		this.filepath = filepath;
		deleteTheFirstLine(filepath);
	}
	
	public static void deleteTheFirstLine(String path)
	{
		int lineDel = 1;
		BufferedReader bufferedReader = null;
		try
		{
			bufferedReader = new BufferedReader(new FileReader(path));
		}
		catch(FileNotFoundException e1)
		{
			String str = "\n DeleteTheFirstLine类：待传文件清单未发现异常！！！\n";
			str += e1.getMessage();
			new Print(str);
			System.out.println(str);
		}
		
		StringBuffer stringBuffer = new StringBuffer(4096);
		String temp = null;
		int line = 0;
		BufferedWriter bufferedWriter = null;
		try
		{
			while( ( temp = bufferedReader.readLine() ) != null)
			{
				line ++ ;
				if(line == lineDel)
					continue;
				stringBuffer.append(temp).append("\r\n");
			}
			bufferedWriter = new BufferedWriter(new FileWriter(path));
			bufferedWriter.write(stringBuffer.toString());
		}
		catch(IOException e)
		{
			String str = "\n DeleteTheFirstLine类：待传文件清单IO异常！！！\n";
			str += e.getMessage();
			new Print(str);
			System.out.println(str);
		}
		finally
		{
			try
			{
				bufferedWriter.close();
				bufferedReader.close();
			}
			catch(IOException e)
			{
				String str = "\n DeleteTheFirstLine类：待传文件清单关闭流异常！！！\n";
				new Print(str);
				System.out.println(str);
			}
		}
	}
	
}
