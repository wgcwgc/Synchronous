/**
 * 
 */
package synchronous;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * @author           Administrator
 * @copyright        wgcwgc
 * @date             2016��4��28��
 * @time             ����2:26:11
 * @project_name     Synchronous
 * @package_name     synchronous
 * @file_name        Upload.java
 * @type_name        Upload
 * @enclosing_type   
 * @tags             
 * @todo             
 * @others           
 *
 */

public class Upload
{
	String filepath = "";
	String urlpath = "";
	
	public Upload()
	{
		
	}
	
	public Upload(String filepath , String urlpath)
	{
		this.filepath = filepath;
		this.urlpath = urlpath;
		run();
	}
	
//	boolean isrunning = true;
	
//	public boolean isIsrunning()
//	{
//		return isrunning;
//	}
//	
//	public void setIsrunning(boolean isrunning)
//	{
//		this.isrunning = isrunning;
//	}
	
	public void run()
	{
//		new GetFilesList(filepath , urlpath);
//		String name = "wgc";
//		ReadIniFile readIniFile = new ReadIniFile(name);
//		String filename = readIniFile.getValue("LogFiles" , "logSavePath");
		String filename = new ReadConfig("wgc").getPath("logSavePath");
		String fileName = filename + "\\" + "passFileList.log";
		File filepaths = new File(fileName);
		if( ! filepaths.getParentFile().exists() || ! filepaths.exists())
		{
			filepaths.getParentFile().mkdirs();
			try
			{
				filepaths.createNewFile();
			}
			catch(Exception e1)
			{
				String str = "\n Upload�ࣺ״̬��־�ļ������쳣������\n";
				str += e1.getMessage();
				new Print(str);
				System.out.println(str);
				System.exit(0);
			}
		}
		
//		if(isrunning)
		handleFiles(filepath , fileName , filename);
	}
	/**
	 * 
	 * @param filepaths �ļ�ɨ��·��
	 * @param filePath E:\\...\\logFiles\\passFileList.log �����ļ�·��
	 * @param filename E:\\...\\logFiles    �ļ�����·��
	 */
	void handleFiles(String filepaths , String filePath , String filename)
	{
		File fileList = new File(filePath);
		BufferedReader bufferedReader = null;
		try
		{
			bufferedReader = new BufferedReader(new FileReader(fileList));
		}
		catch(FileNotFoundException e1)
		{
			String str = "\n Upload�ࣺ�ļ�δ�����쳣������\n";
			str += e1.getMessage();
			new Print(str);
			System.out.println(str);
			System.exit(0);
		}
		
		try
		{
			String tempString = "";
			
			while(( tempString = bufferedReader.readLine() ) != null)
			{
				try
				{
					File files = new File(tempString);
					Calendar calendar = Calendar.getInstance();
					String string = ( new SimpleDateFormat(
							"yyyy-MM-dd HH:mm:ss:SSS") ).format(calendar
							.getTime())
							+ "\r\n" + files.getPath() + "\r\n";
					String PassingFileList = "passingFileList.log";
					new Log(PassingFileList , string , false);
					
					ArrayList <FormFieldKeyValuePair> ffkvp = new ArrayList <FormFieldKeyValuePair>();
					ffkvp.add(new FormFieldKeyValuePair(filepaths , tempString));
					
					ArrayList <UploadFileItem> ufi = new ArrayList <UploadFileItem>();
					ufi.add(new UploadFileItem("file" , tempString));
					HttpPostEmulator hpe = new HttpPostEmulator();
					String response = hpe.sendHttpPostRequest(urlpath , ffkvp ,
							ufi);
					System.out.println("Response from server is: " + response);
					
					if(response.contains("OK"))
					{
						String PassedFileList = "passedFileList.log";
						new Log(PassedFileList , string , true);
						new DeleteTheFirstLine(filePath);
						try
						{
							File passingFileName = new File(filename + "\\"
									+ PassingFileList);
							passingFileName.delete();
							if( ! passingFileName.getParentFile().exists()
									|| ! passingFileName.exists())
							{
								passingFileName.getParentFile().mkdirs();
								passingFileName.createNewFile();
							}
						}
						catch(Exception e)
						{
							String str = "\n Upload�ࣺ״̬�ļ�ɾ���ʹ����쳣������\n";
							str += e.getMessage();
							new Print(str);
							System.out.println(str);
							System.exit(0);
						}
					}
					
				}
				catch(Exception e)
				{
					String str = "\n Upload�ࣺ���ӷ��������󣡣���\n";
					str += e.getMessage();
					new Print(str);
					System.out.println(str);
					System.exit(0);
				}
			}
		}
		catch(IOException e)
		{
			String str = "\n Upload�ࣺ�ļ��������쳣������\n";
			str += e.getMessage();
			new Print(str);
			System.out.println(str);
			System.exit(0);
		}
	}
	
}