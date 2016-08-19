/**
 * 
 */
package synchronous;


/**
 * @author           Administrator
 * @copyright        wgcwgc
 * @date             2016年4月28日
 * @time             下午4:10:03
 * @project_name     Synchronous
 * @package_name     synchronous
 * @file_name        ReadConfig.java
 * @type_name        ReadConfig
 * @enclosing_type   
 * @tags             
 * @todo             
 * @others           
 *
 */

public class ReadConfig
{
	String path = "";
	String time = "";
	
	String path1 = "";
	
	String time1 = "";
	String time2 = "";
	String time3 = "";
	
	
	public ReadConfig(String name)
	{
		getvalue(name);
	}
	
	public void getvalue(String name)
	{
		if(name.equals("wgc"))
		{
			ReadIniFile readIniFile = new ReadIniFile(name);
			path1 = readIniFile.getValue("LogFiles" , "logSavePath");
			time1 = readIniFile.getValue("Time" , "history");
			time2 = readIniFile.getValue("Time" , "delay");
			time3 = readIniFile.getValue("Time" , "wait");
		}
		else
		{
			String str = "\n ReadConfig类：该配置不存在，请检查配置文件！！！\n";
			new Print(str);
			System.out.println(str);
			System.exit(0);
		}
	}
	
	public String getPath(String path)
	{
		this.path = path;
		if(path.equals("logSavePath"))
		{
			path = path1;
			return path;
		}
		else
		{
			String str = "\n ReadConfig类：该配置不存在，请检查配置文件！！！\n";
			new Print(str);
			System.out.println(str);
			System.exit(0);
		}
		return null;
	}
	
	public void setPath(String path)
	{
		this.path = path;
	}
	
	
	public long getTime(String time)
	{
		this.time = time;
		
		if(time.equals("history"))
		{
			time = time1;
		}
		else if(time.equals("delay"))
		{
			time = time2;
		}
		else if(time.equals("wait"))
		{
			time = time3;
		}
		else
		{
			String str = "\n ReadConfig类：该配置不存在，请检查配置文件！！！\n";
			new Print(str);
			System.out.println(str);
			System.exit(0);
		}
		
		return Long.parseLong(time);
	}
	
	public void setTime(String time)
	{
		this.time = time;
	}
	
}




