/**
 * 
 */
package synchronous;


import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * @author           Administrator
 * @copyright        wgcwgc
 * @date             2016年4月28日
 * @time             下午2:23:42
 * @project_name     Synchronous
 * @package_name     synchronous
 * @file_name        Print.java
 * @type_name        Print
 * @enclosing_type   
 * @tags             
 * @todo             
 * @others           
 *
 */

public class Print
{
	String str = "";
	public Print(String str)
	{
		this.str = str;
		Calendar calendar = Calendar.getInstance();
		str = ( new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS") )
				.format(calendar.getTime()) + "\r\n" + str + "\r\n";
		String path = "debug.log";
		new Log(path , str , true);
		System.out.println(str);
	}
}
