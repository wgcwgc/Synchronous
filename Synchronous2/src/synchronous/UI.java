/**
 * 
 */
package synchronous;

import java.util.Scanner;

/**
 * @author           Administrator
 * @copyright        wgcwgc
 * @date             2016��4��28��
 * @time             ����2:24:34
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
//			System.out.println("���ڽ��������Ժ򣬣���\n");
//			Upload upload = new Upload();
//			upload.setIsrunning(false);
				System.out.println("���ڽ��������Ժ򣬣���\n");
				GetFilesList getFilesList = new GetFilesList();
				getFilesList.setIsrunning(false);
				System.out.println("�ѽ�������л����ʹ�ú�֧�֣�����");
				System.exit(0);
				cin.close();
			}
			else
			{
				String str = "\n�밴��Q�������������ߣ�����\n��л����ʹ�ú�֧�֣�����";
				new Print(str);
				System.out.println(str);
			}
		}
		catch(Exception e)
		{
			String str = "\n UI�ࣺ��������������\n��л����ʹ�ú�֧�֣�����";
			new Print(str);
			System.out.println(str);
			System.exit(0);
		}
	}
}
