package wgcwgc;


import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Synchronous1
{
    public static void main(String[] args)
    {
//        String urlPath = "http://127.0.0.1:8080/doUpload.jsp";
//        String filePath = "E:\\MyFiles\\java\\projects\\SynchronousFinal\\SynchronousTestIn";
        String urlPath = "";
        String filePath = "";
        String string = "";
        if(args.length != 0)
            string = args[0];
        if(string.isEmpty() || string.equalsIgnoreCase("-h") || string.equalsIgnoreCase("-help") || string.equalsIgnoreCase("help"))
        {
            System.out.println("\n\n\t\t\tFiles Synchronous\n");
            System.out.println("ʵ�ֶԱ����ļ�ͬ�����������Ĳ���.\n\nѡ�");
            System.out.println("\t-i\t" + "��ͬ���ļ�Ŀ¼��");
            System.out.println("\t-o\t" + "��������ַ��");
            System.out.println("\t-h\t" + "������");
            System.out.println("\n�ο������ʽ��" + "-i C:\\testIn -o http://127.0.0.1:8080/doUpload.jsp");
            System.exit(0);
        }
        else
        {
            if(args.length != 4)
            {
            	String str = "\n�����������ȷ������";
            	new Print1(str);
                System.out.println(str);
                System.exit(0);
            }
            for(int i = 0; i < args.length; i ++)
            {
                if(args[i].equals("-i"))
                {
                    filePath = args[i + 1];
                    i ++;
                }
                else if(args[i].equals("-o"))
                {
                    urlPath = args[i + 1];
                    i ++;
                }
                else
                {
                	String str = "\n�����ʽ����ȷ������";
                	new Print1(str);
                    System.out.println(str);
                    System.exit(0);
                }
            }
        }
        File filepath = new File(filePath);
        if((!filepath.isDirectory() && !filepath.isFile()) || !filepath.exists())
        {
        	String str = "\n����Ŀ¼�����ڻ��߸�ʽ����ȷ������";
        	new Print1(str);
            System.out.println(str);
            System.exit(0);
        }
        
        new Upload1(filePath , urlPath).start();
        new UI1(filePath , urlPath).start();

        System.out.println("**********\n");
    }
}


class Log1
{
    String filePathName = "";
    String fileList = "";
    boolean flog = false;
    
    /*
     * filePath log's paths
     * fileList log's contents
     * flog		log cover(false) or not(true)
     */
    public Log1(String filePathName , String fileList , boolean flog)
    {
        this.filePathName = filePathName;
        this.fileList = fileList;
        this.flog = flog;
        Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		int day = calendar.get(Calendar.DATE);
        String logSavePath = "E:\\MyFiles\\java\\projects\\SynchronousFinal\\SynchronousTestOut\\logFiles";
        String yearMonthDay = logSavePath;
        if(filePathName.contains("passedFileList"))
            yearMonthDay = logSavePath + "\\" + year + "\\" + month + "\\" + day + "\\" + filePathName;
        else
        	yearMonthDay = logSavePath + "\\" + filePathName;
        File filepath = new File(yearMonthDay);
	    if(!filepath.getParentFile().exists() || !filepath.exists())
	    {
	        filepath.getParentFile().mkdirs();
		    try
		    {
		        filepath.createNewFile();
		    }
		    catch(Exception e1)
		    {
		    	String str = "\n״̬��־�ļ������쳣������";
	        	new Print1(str);
		        System.out.println(str);
		        System.exit(0);
		    }
	    }
	    BufferedWriter bufferedWriter = null;
	    try
	    {
	        if(flog)
	            bufferedWriter = new BufferedWriter(new FileWriter(filepath , true));
	        else
	            bufferedWriter = new BufferedWriter(new FileWriter(filepath));
	        bufferedWriter.write(fileList);
	    }
	    catch(IOException e)
	    {
	    	String str = "\n״̬��־�ļ�д���쳣������";
        	new Print1(str);
	        System.out.println(str);
	        System.exit(0);
	    }
	    finally
	    {
	        try
	        {
	            bufferedWriter.flush();
	            bufferedWriter.close();
	        }
	        catch(IOException e)
	        {
	        	String str = "\n�ļ����ر��쳣������";
	        	new Print1(str);
	            System.out.println(str);
	            System.exit(0);
	        }
	    }
    }
	    
}

class Upload1 extends Thread
{
    String filepath = "";
    String urlpath = "";
    
    public Upload1(String filepath , String urlpath)
    {
        this.filepath = filepath;
        this.urlpath = urlpath;
    }
    
	public void run()
    {
		new GetFilesList1(filepath).getFilesName(filepath);
		String fileName = "E:\\MyFiles\\java\\projects\\SynchronousFinal\\SynchronousTestOut\\LogFiles\\passFileList.log";
		File filepath = new File(fileName);
	    if(!filepath.getParentFile().exists() || !filepath.exists())
	    {
	        filepath.getParentFile().mkdirs();
		    try
		    {
		        filepath.createNewFile();
		    }
		    catch(Exception e1)
		    {
		    	String str = "\n״̬��־�ļ������쳣������";
	        	new Print1(str);
		        System.out.println(str);
		        System.exit(0);
		    }
	    }
		handleFiles(fileName);
    }
	
	void handleFiles(String filePath)
	{
        File fileList = new File(filePath);
	    BufferedReader bufferedReader = null;
	    try
	    {
			bufferedReader = new BufferedReader(new FileReader(fileList));
		}
	    catch(FileNotFoundException e1)
	    {
	    	String str = "\n�ļ�δ�����쳣������";
			new Print1(str);
			System.out.println(str);
			System.exit(0);
		}
	    try
	    {
	    	String tempString = "";
			while((tempString = bufferedReader.readLine()) != null)
			{
			   try
			   {
				   File files = new File(tempString);
				   URL url = new URL(urlpath);
				   HttpURLConnection httpUrlConnection = (HttpURLConnection)url.openConnection();
				   httpUrlConnection.setDoInput(true);
				   httpUrlConnection.setDoOutput(true);
				   httpUrlConnection.setUseCaches(false);
				   httpUrlConnection.setConnectTimeout(30000);
				   httpUrlConnection.setReadTimeout(30000);
				   httpUrlConnection.setRequestMethod("POST");
				   httpUrlConnection.setInstanceFollowRedirects(true);
//        httpUrlConnection.setRequestProperty("content-type", "audio/wav");
//     		   httpUrlConnection.setRequestProperty("content-type", "application/x-www-form-urlencoded");
				   httpUrlConnection.setRequestProperty("content-type", "java/*");
				   httpUrlConnection.connect();
				   BufferedOutputStream  bufferedOutputStream = new BufferedOutputStream(httpUrlConnection.getOutputStream());
				   File file = new File(files.toString());
				   Calendar calendar = Calendar.getInstance();
				   String PassingFileList = "passingFileList.log";
				   String PassedFileList = "passedFileList.log";
				   String string = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS")).format(calendar.getTime())  + "\r\n" + files.getPath() + "\r\n";  
				   new Log1(PassingFileList, string , false);
				   new Log1(PassedFileList , string , true);
				   
				   FileInputStream fileInputStream = new FileInputStream(file);
				   byte [] bytes = new byte[1024];
				   int numReadByte = 0;
				   while((numReadByte = fileInputStream.read(bytes , 0 , 1024)) > 0)
				   {
					   bufferedOutputStream.write(bytes, 0, numReadByte);
				   }
				   String content = "filePath=" + URLEncoder.encode(filepath , "UTF-8");
				   bufferedOutputStream.write(content.getBytes());
//         	   httpUrlConnection.disconnect();
				   bufferedOutputStream.flush();
				   bufferedOutputStream.close();
				   fileInputStream.close();
//     		   System.out.println(httpUrlConnection.getResponseCode());
				   if(httpUrlConnection.getResponseCode() == 200)
				   {
					   new DataInputStream(httpUrlConnection.getInputStream());
				   }
				   
				   
				   
				   
			   }
			   catch(Exception e)
			   {
				   String str = "\n���ӷ��������󣡣���";
				   new Print1(str);
				   System.out.println(str);
				   System.exit(0);
			   }
			}
		}
	    catch(IOException e)
		{
			String str = "\n�ļ��������쳣������";
			new Print1(str);
			System.out.println(str);
			System.exit(0);
		}
     }
}

class UI1 extends Thread
{
    String deal1 = "";
    String deal2 = "";
    public UI1(String deal1 , String deal2)
    {
        this.deal1 = deal1;
        this.deal2 = deal2;
    }
    public void run()
    {
        try
        {
//			������������Ctrl + C ����
        }
        catch(Exception e)
        {
        	String str = "\n�û��������󣡣���";
        	new Print1(str);
            System.out.println(str);
        }
    }
}

class GetFilesList1
{
	String path = "";
	public GetFilesList1(String path)
	{
		this.path = path;
	}
	
	public void getFilesName(String filePath)
    {
		File dir = new File(filePath);
		final long MAX = 10 * 24 * 3600 * 1000;
        File [] files = dir.listFiles();
        for(File file:files)
        {
            if(file.isDirectory())
            	getFilesName(file.toString());
            else if(file.isFile() && file.getName().endsWith(".java") && 
            		( System.currentTimeMillis() - file.lastModified() ) < MAX)
            {
            	new Log1("passFileList.log" , file.toString() + "\r\n" , true);
            }
        }
    }
}

class Print1
{
	String str = "";
	public Print1(String str)
	{
		this.str = str;
		Calendar calendar = Calendar.getInstance();
		str = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS")).format(calendar.getTime())  + "\r\n" + str + "\r\n";
		String path = "debug.log";
		new Log1(path , str , true);
		System.out.println(str);
	}
}

