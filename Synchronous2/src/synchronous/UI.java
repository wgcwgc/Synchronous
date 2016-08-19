/**
 * 
 */
package synchronous;

import java.util.Scanner;

/**
 * @author           Administrator
 * @copyright        wgcwgc
 * @date             2016年4月28日
 * @time             下午2:24:34
 * @project_name     Synchronous
 * @package_name     synchronous
 * @file_name        UI.java
 * @type_name        UI
 * @enclosing_type   
 * @tags             
 * @todo             
 * @others           
 *
 */

public class UI extends Thread
{
	public void run()
	{
		Scanner cin = new Scanner(System.in);
		try
		{
			if(cin.nextLine().toLowerCase().equals("q"))
			{
//			System.out.println("正在结束，请稍候，，，\n");
//			Upload upload = new Upload();
//			upload.setIsrunning(false);
				System.out.println("正在结束，请稍候，，，\n");
				GetFilesList getFilesList = new GetFilesList();
				getFilesList.setIsrunning(false);
				System.out.println("已结束，感谢您的使用和支持！！！");
				System.exit(0);
				cin.close();
			}
			else
			{
				String str = "\n请按“Q”键结束本工具！！！\n感谢您的使用和支持！！！";
				new Print(str);
				System.out.println(str);
			}
		}
		catch(Exception e)
		{
			String str = "\n UI类：正常结束！！！\n感谢您的使用和支持！！！";
			new Print(str);
			System.out.println(str);
			System.exit(0);
		}
	}
}
