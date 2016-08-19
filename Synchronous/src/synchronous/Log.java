
package synchronous;

import java.io.*;

public class Log
{
	String filePathName = "";
	String fileList = "";
	boolean flog = false;
	
	public Log(String filePathName , String fileList , boolean flog)
	{
		this.filePathName = filePathName;
		this.fileList = fileList;
		this.flog = flog;
		String logSavePath = new ReadConfig("wgc").getPath("logSavePath");
		String yearMonthDay = logSavePath;
		yearMonthDay = logSavePath + "\\" + filePathName;
		File filepath = new File(yearMonthDay);
		if( ! filepath.getParentFile().exists() || ! filepath.exists())
		{
			filepath.getParentFile().mkdirs();
			try
			{
				filepath.createNewFile();
			}
			catch(Exception e1)
			{
				String str = "\n Log类：状态日志文件创建异常！！！\n";
				str += e1.getMessage();
				new Print(str);
				System.out.println(str);
				System.exit(0);
			}
		}
		
		BufferedWriter bufferedWriter = null;
		try
		{
			if(flog)
				bufferedWriter = new BufferedWriter(new FileWriter(filepath ,
						true));
			else
				bufferedWriter = new BufferedWriter(new FileWriter(filepath));
			bufferedWriter.write(fileList);
		}
		catch(IOException e)
		{
			String str = "\n Log类：状态日志文件写入异常！！！\n";
			str += e.getMessage();
			new Print(str);
			System.out.println(str);
			System.exit(0);
		}
		finally
		{
			try
			{
				bufferedWriter.flush();
				bufferedWriter.close();
			}
			catch(IOException e)
			{
				String str = "\n Log类：文件流关闭异常！！！\n";
				str += e.getMessage();
				new Print(str);
				System.out.println(str);
				System.exit(0);
			}
		}
	}
}

