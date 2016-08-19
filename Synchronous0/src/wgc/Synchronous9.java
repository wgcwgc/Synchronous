package wgc;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Synchronous9
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
            System.out.println("实现对本地文件同步到服务器的操作.\n\n选项：");
            System.out.println("\t-i\t" + "待同步文件目录。");
            System.out.println("\t-o\t" + "服务器地址。");
            System.out.println("\t-h\t" + "帮助。");
            System.out.println("\n参考输入格式：" + "-i C:\\testIn -o http://127.0.0.1:8080/doUpload.jsp");
            System.exit(0);
        }
        else
        {
            if(args.length != 4)
            {
            	String str = "\n输入参数不正确！！！";
            	new Print(str);
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
                	String str = "\n输入格式不正确！！！";
                	new Print(str);
                    System.out.println(str);
                    System.exit(0);
                }
            }
        }
        File filepath = new File(filePath);
        if((!filepath.isDirectory() && !filepath.isFile()) || !filepath.exists())
        {
        	String str = "\n输入目录不存在或者格式不正确！！！";
        	new Print(str);
            System.out.println(str);
            System.exit(0);
        }
        
        new Upload(filePath , urlPath).start();
        new UI(filePath , urlPath).start();

        System.out.println("**********\n");
    }
}


class Log
{
    String filePathName = "";
    String fileList = "";
    boolean flog = false;
    
    /*
     * filePath log's paths
     * fileList log's contents
     * flog		log cover(false) or not(true)
     */
    public Log(String filePathName , String fileList , boolean flog)
    {
        this.filePathName = filePathName;
        this.fileList = fileList;
        this.flog = flog;
//        Calendar calendar = Calendar.getInstance();
//		int year = calendar.get(Calendar.YEAR);
//		int month = calendar.get(Calendar.MONTH) + 1;
//		int day = calendar.get(Calendar.DATE);
        String logSavePath = "E:\\MyFiles\\java\\projects\\SynchronousFinal\\SynchronousTestOut\\LogFiles";
//        String yearMonthDay = logSavePath + "\\" + year + "\\" + month + "\\" + day + "\\" + filePathName;
        String yearMonthDay = logSavePath + "\\" + filePathName;
        if(filePathName.contains(":"))
        	yearMonthDay = filePathName;
//		String passingTimeShow = "\\" + year + "\\" + month + "\\" + day + "\\" + "passingFileList.log";
//		String passTimeShow = "\\" + year + "\\" + month + "\\" + day + "\\" + "passFileList.log";
//		String passedTimeShow = "\\" + year + "\\" + month + "\\" + day + "\\" + "passedFileList.log";
//		String PassingFileList = logSavePath + passingTimeShow;
//		String PassFileList = logSavePath + passTimeShow;
//		String PassedFileList = logSavePath + passedTimeShow;
        File filepath = new File(yearMonthDay);
	    if(!filepath.getParentFile().exists())
	    {
	        filepath.getParentFile().mkdirs();
	    }
	    try
	    {
	        filepath.createNewFile();
	    }
	    catch(Exception e1)
	    {
	    	String str = "\n状态日志文件创建异常！！！";
        	new Print(str);
	        System.out.println(str);
	        System.exit(0);
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
	    	String str = "\n状态日志文件写入异常！！！";
        	new Print(str);
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
	        	String str = "\n文件流关闭异常！！！";
	        	new Print(str);
	            System.out.println(str);
	            System.exit(0);
	        }
	    }
    }
	    
}

class Upload extends Thread
{
    String filepath = "";
    String urlpath = "";
    
    public Upload(String filepath , String urlpath)
    {
        this.filepath = filepath;
        this.urlpath = urlpath;
    }
    
	public void run()
    {
		new GetFilesList(filepath);
		String fileName = "E:\\MyFiles\\java\\projects\\SynchronousFinal\\SynchronousTestOut\\LogFiles\\passFileList.log";
		handleFiles(fileName);
    }
	
	void handleFiles(String fileName)
	{
        File [] fileList = new File(fileName).listFiles();
        for(int i= 0; i < fileList.length; i ++)
        {
           if(fileList[i].isFile())
           {
        	   try
        	   {
        		   URL url = new URL(urlpath);
        		   HttpURLConnection httpUrlConnection = (HttpURLConnection)url.openConnection();
        		   httpUrlConnection.setDoInput(true);
        		   httpUrlConnection.setDoOutput(true);
        		   httpUrlConnection.setUseCaches(false);
        		   httpUrlConnection.setConnectTimeout(30000);
        		   httpUrlConnection.setReadTimeout(30000);
        		   httpUrlConnection.setRequestMethod("POST");
        		   httpUrlConnection.setInstanceFollowRedirects(true);
//           httpUrlConnection.setRequestProperty("content-type", "audio/wav");
//        		   httpUrlConnection.setRequestProperty("content-type", "application/x-www-form-urlencoded");
        		   httpUrlConnection.setRequestProperty("content-type", "java/*");
        		   httpUrlConnection.connect();
        		   BufferedOutputStream  bufferedOutputStream = new BufferedOutputStream(httpUrlConnection.getOutputStream());
        		   File file = new File(fileList[i].toString());
        		   Calendar calendar = Calendar.getInstance();
//        		   int year = calendar.get(Calendar.YEAR);
//        		   int month = calendar.get(Calendar.MONTH) + 1;
//        		   int day = calendar.get(Calendar.DATE);
//        		   String logSavePath = "E:\\MyFiles\\java\\projects\\SynchronousFinal\\SynchronousTestOut\\LogFiles";
//        		   String passingTimeShow = "\\" + year + "\\" + month + "\\" + day + "\\" + "passingFileList.log";
//        		   String passTimeShow = "\\" + year + "\\" + month + "\\" + day + "\\" + "passFileList.log";
//        		   String passedTimeShow = "\\" + year + "\\" + month + "\\" + day + "\\" + "passedFileList.log";
//        		   String PassingFileList = logSavePath + passingTimeShow;
//        		   String PassFileList = logSavePath + passTimeShow;
//        		   String PassedFileList = logSavePath + passedTimeShow;
        		   String PassingFileList = "passingFileList.log";
//        		   String PassFileList = "passFileList.log";
        		   String PassedFileList = "passedFileList.log";
        		   String string = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS")).format(calendar.getTime())  + "\r\n" + fileList[i].getPath() + "\r\n";  
        		   new Log(PassingFileList, string , false);
//        		   new Log(PassFileList , string , true);
        		   new Log(PassedFileList , string , true);
        		   
        		   FileInputStream fileInputStream = new FileInputStream(file);
        		   byte [] bytes = new byte[1024];
        		   int numReadByte = 0;
        		   while((numReadByte = fileInputStream.read(bytes , 0 , 1024)) > 0)
        		   {
        			   bufferedOutputStream.write(bytes, 0, numReadByte);
        		   }
        		   String content = "filePath=" + URLEncoder.encode(filepath , "UTF-8");
        		   bufferedOutputStream.write(content.getBytes());
//            	   httpUrlConnection.disconnect();
        		   bufferedOutputStream.flush();
        		   bufferedOutputStream.close();
        		   fileInputStream.close();
//        		   System.out.println(httpUrlConnection.getResponseCode());
        		   if(httpUrlConnection.getResponseCode() == 200)
        		   {
        			   new DataInputStream(httpUrlConnection.getInputStream());
        		   }
        		   
        		   
        		   
        		   
        	   }
        	   catch(Exception e)
        	   {
        		   String str = "\n连接服务器错误！！！";
   	        	   new Print(str);
        		   System.out.println(str);
//        		   e.printStackTrace();
        		   System.exit(0);
        	   }
           }
           else
               if(fileList[i].isDirectory())
               {
                  handleFiles(fileList[i].getPath());
               }
               else
               {
            	  String str = "\n文件读入有误！！！";
   	        	  new Print(str);
                  System.out.println(str);
               }
        }
	}
}

class UI extends Thread
{
    String deal1 = "";
    String deal2 = "";
    public UI(String deal1 , String deal2)
    {
        this.deal1 = deal1;
        this.deal2 = deal2;
    }
    public void run()
    {
        try
        {
//			待处理，，，Ctrl + C 结束
        }
        catch(Exception e)
        {
        	String str = "\n用户输入有误！！！";
        	new Print(str);
            System.out.println(str);
        }
    }
}

class GetFilesList
{
	String path = "";
	public GetFilesList(String path)
	{
		this.path = path;
	}
	
	public static void getFilesName(File dir)
    {
		final long MAX = 3 * 24 * 3600 * 1000;
//        System.out.println(dir);
        File[] files = dir.listFiles();
        for(File file:files)
        {
            if(file.isDirectory())
            	getFilesName(file);
            else if(file.isFile() && file.getName().endsWith(".java") &&
            		System.currentTimeMillis() - file.lastModified() < MAX)
            {
            	new Log("passFileList.log" , file.toPath().toString() + "\r\n", true);
            }
        }
    }
}

class Print
{
	String str = "";
	public Print(String str)
	{
		this.str = str;
		String path = "c:\\debug.log";
		new Log(path , str , true);
		System.out.println(str);
	}
}

