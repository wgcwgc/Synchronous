
package synchronous;

import java.io.File;

public class Synchronous
{
	public static void main(String [] args)
	{
		String urlPath = "";
		String filePath = "";
		String string = "";
		if(args.length != 0)
			string = args[0];
		if(string.isEmpty() || string.equalsIgnoreCase("-h")
				|| string.equalsIgnoreCase("-help")
				|| string.equalsIgnoreCase("help"))
		{
			System.out.println("\n\n\t\t\tFiles Synchronous\n");
			System.out.println("实现对本地文件同步到服务器的操作.\n\n选项：");
			System.out.println("\t-i\t" + "待同步文件目录。");
			System.out.println("\t-o\t" + "服务器地址。");
			System.out.println("\t-h\t" + "帮助。");
			System.out.println("\t q\t" + "退出。");
			System.out
					.println("\n参考输入格式："
							+ "-i C:\\testIn -o http://127.0.0.1:8080/wgc/doUpload.jsp");
			System.exit(0);
		}
		else
		{
			if(args.length != 4)
			{
				String str = "\n main方法：输入参数不正确！！！";
				new Print(str);
				System.out.println(str);
				System.exit(0);
			}
			for(int i = 0 ; i < args.length ; i ++ )
			{
				if(args[i].equals("-i"))
				{
					filePath = args[i + 1];
					i ++ ;
				}
				else if(args[i].equals("-o"))
				{
					urlPath = args[i + 1];
					i ++ ;
				}
				else
				{
					String str = "\n main方法：输入格式不正确！！！";
					new Print(str);
					System.out.println(str);
					System.exit(0);
				}
			}
		}
		
//		 filePath = "E:\\MyFiles\\java\\projects\\SynchronousFinal\\SynchronousTestIn";
//		 urlPath = "http://127.0.0.1:8080/wgc/doUpload.jsp";
		
		File filepath = new File(filePath);
		if( ( ! filepath.isDirectory() && ! filepath.isFile() )
				|| ! filepath.exists())
		{
			String str = "\n main方法：输入目录不存在或者格式不正确！！！";
			new Print(str);
			System.out.println(str);
			System.exit(0);
		}
		
		while(true)
		{
			UI ui = new UI();
			ui.start();
			
			try
			{
				long wait = new ReadConfig("wgc").getTime("wait");
				Thread.sleep(wait);
			}
			catch(InterruptedException e)
			{
				String str = "\n main方法：线程休眠异常！！！";
				new Print(str);
				System.out.println(str);
				System.exit(0);
			}
			
			GetFilesList getFilesList = new GetFilesList(filePath , urlPath);
			getFilesList.setIsrunning(true);
			getFilesList.start();
			
		}
	}
	
}
