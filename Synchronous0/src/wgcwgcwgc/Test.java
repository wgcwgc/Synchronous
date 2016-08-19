package wgcwgcwgc;

/*
* IniReader.java
* 用搜索Java读取INI文件(带section的)
* 示例：
* IniReader reader = new IniReader("E:\\james\\win.ini");
* out.println(reader.getValue("TestSect3", "kkk 6"));
*/

//import java.io.BufferedReader;
//import java.io.FileNotFoundException;
//import java.io.FileReader;
//import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;
/**
 * 
 * @author           Administrator
 * @copyright        wgcwgc
 * @date             2016年4月19日
 * @time             下午3:49:58
 * @project_name     Synchronous
 * @package_name     wgcwgcwgc
 * @file_name        Test.java
 * @type_name        Test
 * @enclosing_type   
 * @tags             
 * @todo             TODO
 * @others           
 *
 */
public class Test
{
	@SuppressWarnings("rawtypes")
	HashMap sections = new HashMap();
    String currentSecion;
    Properties current;
//	public Test(String filename)
//    {
//        BufferedReader reader = null;
//		try
//		{
//			reader = new BufferedReader(new FileReader(filename));
//		}
//		catch(FileNotFoundException e)
//		{
//			String str = "\n ReadIniFile类：配置文件不存在或者格式不正确！！！\n";
//	    	str += e.getMessage();
//	        System.out.println(str);
//	        System.exit(0);
//		}
//        try
//        {
//        	String line;
//            try
//            {
//    			while((line = reader.readLine()) != null)
//    			{
//    				line = line.trim();
//    		        if(line.matches("\\[.*\\]"))
//    		        {
//    		            currentSecion = line.replaceFirst("\\[(.*)\\]", "$1");
//    		            current = new Properties();
//    		            sections.put(currentSecion, current);
//    		        }
//    		        else if(line.matches(".*=.*"))
//    		        {
//    		            if(current != null)
//    		            {
//    		                int i = line.indexOf('=');
//    		                String name = line.substring(0 , i);
//    		                String value = line.substring(i + 1);
//    		                current.setProperty(name , value);
//    		            }
//    		        }
//    			}
//    		}
//            catch(IOException e)
//            {
//    			String str = "\n ReadIniFile类：配置文件读取异常！！！\n";
//    	    	str += e.getMessage();
//    	        System.out.println(str);
//    	        System.exit(0);
//    		}
//		}
//        catch(Exception e)
//		{
//			String str = "\n ReadIniFile类：配置文件内容不合法！！！\n";
//	    	str += e.getMessage();
//	        System.out.println(str);
//	        System.exit(0);
//		}
//        finally
//        {
//        	try
//        	{
//				reader.close();
//			}
//        	catch(IOException e)
//        	{
//				String str = "\n ReadIniFile类：配置文件关闭异常！！！\n";
//		    	str += e.getMessage();
//		        System.out.println(str);
//		        System.exit(0);
//			}
//        }
//    }

//    void read(BufferedReader reader)
//    {
//        String line;
//        try
//        {
//			while((line = reader.readLine()) != null)
//			{
//			    parseLine(line);
//			}
//		}
//        catch(IOException e)
//        {
//			String str = "\n ReadIniFile类：配置文件读取异常！！！\n";
//	    	str += e.getMessage();
//	        System.out.println(str);
//	        System.exit(0);
//		}
//    }

//    @SuppressWarnings("unchecked")
//	void parseLine(String line)
//    {
//        line = line.trim();
//        if(line.matches("\\[.*\\]"))
//        {
//            currentSecion = line.replaceFirst("\\[(.*)\\]", "$1");
//            current = new Properties();
//            sections.put(currentSecion, current);
//        }
//        else if(line.matches(".*=.*"))
//        {
//            if(current != null)
//            {
//                int i = line.indexOf('=');
//                String name = line.substring(0, i);
//                String value = line.substring(i + 1);
//                current.setProperty(name, value);
//            }
//        }
//    }

    public String getValue(String section , String name)
    {
        Properties p = (Properties) sections.get(section);
        if (p == null)
        {
            return null;
        }
        String value = p.getProperty(name);
        return value;
    }
}
