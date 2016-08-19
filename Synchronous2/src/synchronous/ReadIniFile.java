/**
 * 
 */
package synchronous;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Properties;

/**
 * @author           Administrator
 * @copyright        wgcwgc
 * @date             2016年4月28日
 * @time             下午2:25:40
 * @project_name     Synchronous
 * @package_name     synchronous
 * @file_name        ReadIniFile.java
 * @type_name        ReadIniFile
 * @enclosing_type   
 * @tags             
 * @todo             
 * @others           
 *
 */

public class ReadIniFile
{
	@SuppressWarnings("rawtypes")
	HashMap sections = new HashMap();
	String currentSecion;
	Properties current;
	
	@SuppressWarnings("unchecked")
	public ReadIniFile(String filename)
	{
		BufferedReader reader = null;
		try
		{
//			String packagePath = this.getClass().getResource("").getPath().toString();
//			System.out.println(packagePath + "\n*******************");
//			packagePath = packagePath.substring(1 , packagePath.indexOf("src"));
//			System.out.println(packagePath + "\n###################");
			if(filename.equals("wgc"))
			{
//				filename = packagePath + "config/wgc.ini";
				filename = "./wgc.ini";
				try
				{
					new File(filename);
				}
				catch (Exception e)
				{
					String str = "\n ReadIniFile类：配置文件不存在或者格式不正确0！！！\n";
					System.out.println(str);
					System.exit(0);
				}
				reader = new BufferedReader(new FileReader(filename));
			}
			else
			{
				String str = "\n ReadIniFile类：配置文件不存在或者格式不正确1！！！\n";
				new Print(str);
				System.out.println(str);
				System.exit(0);
			}
		}
		catch(Exception e)
		{
			String str = "\n ReadIniFile类：配置文件不存在或者格式不正确2！！！\n";
			str += e.getMessage();
			System.out.println(str);
			System.exit(0);
		}
		
		try
		{
			String line;
			try
			{
				while( ( line = reader.readLine() ) != null)
				{
					line = line.trim();
					if(line.matches("\\[.*\\]"))
					{
						currentSecion = line.replaceFirst("\\[(.*)\\]" , "$1");
						current = new Properties();
						sections.put(currentSecion , current);
					}
					else if(line.matches(".*=.*"))
					{
						if(current != null)
						{
							int i = line.indexOf('=');
							String name = line.substring(0 , i);
							String value = line.substring(i + 1);
							current.setProperty(name , value);
						}
					}
				}
			}
			catch(Exception e)
			{
				String str = "\n ReadIniFile类：配置文件读取异常！！！\n";
				str += e.getMessage();
				new Print(str);
				System.out.println(str);
				System.exit(0);
			}
		}
		catch(Exception e)
		{
			String str = "\n ReadIniFile类：配置文件内容不合法！！！\n";
			str += e.getMessage();
			new Print(str);
			System.out.println(str);
			System.exit(0);
		}
		finally
		{
			try
			{
				reader.close();
			}
			catch(Exception e)
			{
				String str = "\n ReadIniFile类：配置文件关闭异常！！！\n";
				str += e.getMessage();
				new Print(str);
				System.out.println(str);
				System.exit(0);
			}
		}
	}
	
	public String getValue(String section , String name)
	{
		Properties p = (Properties) sections.get(section);
		if(p == null)
		{
			return null;
		}
		String value = p.getProperty(name);
		return value;
	}
}
