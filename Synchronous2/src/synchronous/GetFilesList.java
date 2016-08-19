/**
 * 
 */
package synchronous;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * @author           Administrator
 * @copyright        wgcwgc
 * @date             2016年4月28日
 * @time             下午2:25:00
 * @project_name     Synchronous
 * @package_name     synchronous
 * @file_name        GetFilesList.java
 * @type_name        GetFilesList
 * @enclosing_type   
 * @tags             
 * @todo             
 * @others           
 *
 */

public class GetFilesList extends Thread
{
	String path = "";
//	String name = "wgc";
//	ReadIniFile readIniFile = new ReadIniFile(name);
//	String history = readIniFile.getValue("Time" , "history");
//	String delay = readIniFile.getValue("Time" , "delay");
	
	final long MAX = new ReadConfig("wgc").getTime("history");
	final long MIN = new ReadConfig("wgc").getTime("delay");
	
	boolean isrunning = true;
	
	public boolean isIsrunning()
	{
		return isrunning;
	}
	
	public void setIsrunning(boolean isrunning)
	{
		this.isrunning = isrunning;
	}
	
	public GetFilesList()
	{
		
	}
	
	public GetFilesList(String path , String urlPath)
	{
		this.path = path;
		if(isrunning)
			getFilesName(path , urlPath);
		String filename = new ReadConfig("wgc").getPath("logSavePath");
		String fileName = filename + "\\" + "passFileList.log";
		File filepaths = new File(fileName);
		if(filepaths.length() != 0)
		{
			new Upload(path , urlPath);
		}
	}
	
	public void getFilesName(String filePath , String urlPath)
	{
		File dir = new File(filePath);
		File [] files = dir.listFiles();
		for(File file : files)
		{
			if(isrunning)
			{
				if(file.isDirectory())
				{
					getFilesName(file.toString() , urlPath);
				}
				else if(judge(file))
				{
					System.out.println("\n\t\t扫描中，，，");
					new Log("passFileList.log" , file.toString() + "\r\n" ,
							true);
				}
			}
		}
		
	}
	
	public boolean judge(File file)
	{
		if(file.isFile() && ( /*
							 * file.getName().toLowerCase().endsWith("java") ||
							 */
		file.getName().toLowerCase().endsWith("wav") )
				&& ( System.currentTimeMillis() - file.lastModified() ) < MAX)
		{
//			ReadIniFile readIniFile = new ReadIniFile(name);
//			String logSavePath = readIniFile.getValue("LogFiles" ,
//					"logSavePath");
			String logSavePath = new ReadConfig("wgc").getPath("logSavePath");
			String passedFileList = logSavePath + "\\passedFileList.log";
			String passFileList = logSavePath + "\\passFileList.log";
			if( ! isUpLoad(file , passedFileList)
					&& ! isUpLoad(file , passFileList))
			{
				long beforeTime = file.lastModified();
				try
				{
					Thread.sleep(MIN);
				}
				catch(InterruptedException e)
				{
					String str = "\n GetFilesList类：线程休眠异常！！！\n";
					str += e.getMessage();
					new Print(str);
					System.out.println(str);
					System.exit(0);
				}
				long afterTime = file.lastModified();
				if(afterTime - beforeTime == 0)
					return true;
			}
		}
		
		return false;
		
	}
	
	public boolean isUpLoad(File file , String path)
	{
		String filepath = file.toString();
		File paths = new File(path);
		if( ! paths.getParentFile().exists() || ! paths.exists())
		{
			paths.getParentFile().mkdirs();
			try
			{
				paths.createNewFile();
			}
			catch(Exception e1)
			{
				String str = "\n GetFilesList类：状态日志文件创建异常！！！\n";
				str += e1.getMessage();
				new Print(str);
				System.out.println(str);
				System.exit(0);
			}
		}
		
		BufferedReader bufferedReader = null;
		try
		{
			bufferedReader = new BufferedReader(new FileReader(path));
		}
		catch(FileNotFoundException e1)
		{
			String str = "\n GetFilesList类：检查已传文件内是否存在当前文件名。已传文件清单未发现异常！！！\n";
			str += e1.getMessage();
			new Print(str);
			System.out.println(str);
		}
		
		String temp = null;
		try
		{
			while( ( temp = bufferedReader.readLine() ) != null)
			{
				if(filepath.equals(temp))
				{
					return true;
				}
			}
		}
		catch(IOException e)
		{
			String str = "\n GetFilesList类：检查已传文件内是否存在当前文件名。IO异常！！！\n";
			str += e.getMessage();
			new Print(str);
			System.out.println(str);
		}
		finally
		{
			try
			{
				bufferedReader.close();
			}
			catch(IOException e)
			{
				String str = "\n GetFilesList类：检查已传文件内是否存在当前文件名。关闭流异常！！！\n";
				str += e.getMessage();
				new Print(str);
				System.out.println(str);
			}
		}
		
		return false;
	}
	
}