
package synchronous;

import java.text.SimpleDateFormat;
import java.util.Calendar;

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
