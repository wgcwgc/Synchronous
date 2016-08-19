
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
			System.out.println("ʵ�ֶԱ����ļ�ͬ�����������Ĳ���.\n\nѡ�");
			System.out.println("\t-i\t" + "��ͬ���ļ�Ŀ¼��");
			System.out.println("\t-o\t" + "��������ַ��");
			System.out.println("\t-h\t" + "������");
			System.out.println("\t q\t" + "�˳���");
			System.out
					.println("\n�ο������ʽ��"
							+ "-i C:\\testIn -o http://127.0.0.1:8080/wgc/doUpload.jsp");
			System.exit(0);
		}
		else
		{
			if(args.length != 4)
			{
				String str = "\n main�����������������ȷ������";
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
					String str = "\n main�����������ʽ����ȷ������";
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
			String str = "\n main����������Ŀ¼�����ڻ��߸�ʽ����ȷ������";
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
				String str = "\n main�������߳������쳣������";
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
