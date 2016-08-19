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

public class Synchronous
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
            	new Print0(str);
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
                	new Print0(str);
                    System.out.println(str);
                    System.exit(0);
                }
            }
        }
        File filepath = new File(filePath);
        if((!filepath.isDirectory() && !filepath.isFile()) || !filepath.exists())
        {
        	String str = "\n输入目录不存在或者格式不正确！！！";
        	new Print0(str);
            System.out.println(str);
            System.exit(0);
        }
        
        new Upload0(filePath , urlPath).start();
        new UI0(filePath , urlPath).start();

        System.out.println("**********\n");
    }
}


class Log0
{
    String filePathName = "";
    String fileList = "";
    boolean flog = false;
    
    /*
     * filePath log's paths
     * fileList log's contents
     * flog		log cover(false) or not(true)
     */
    public Log0(String filePathName , String fileList , boolean flog)
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
//        System.out.println(yearMonthDay);
        File filepath = new File(yearMonthDay);
	    if(!filepath.getParentFile().exists() || !filepath.exists())
	    {
//	    	System.out.println("创建目录成功");
	        filepath.getParentFile().mkdirs();
		    try
		    {
	//	    	System.out.println("创建文件成功");
	//	    	System.out.println(filepath);
		        filepath.createNewFile();
		    }
		    catch(Exception e1)
		    {
		    	String str = "\n状态日志文件创建异常！！！";
	        	new Print0(str);
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
	    	String str = "\n状态日志文件写入异常！！！";
        	new Print0(str);
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
	        	new Print0(str);
	            System.out.println(str);
	            System.exit(0);
	        }
	    }
    }
	    
}

class Upload0 extends Thread
{
    String filepath = "";
    String urlpath = "";
    
    public Upload0(String filepath , String urlpath)
    {
        this.filepath = filepath;
        this.urlpath = urlpath;
    }
    
	public void run()
    {
		new GetFilesList0(filepath).getFilesName(filepath);
		String fileName = "E:\\MyFiles\\java\\projects\\SynchronousFinal\\SynchronousTestOut\\LogFiles\\passFileList.log";
//		System.out.println(fileName);
		
		File filepath = new File(fileName);
	    if(!filepath.getParentFile().exists() || !filepath.exists())
	    {
//	    	System.out.println("创建目录成功");
	        filepath.getParentFile().mkdirs();
		    try
		    {
	//	    	System.out.println("创建文件成功");
	//	    	System.out.println(filepath);
		        filepath.createNewFile();
		    }
		    catch(Exception e1)
		    {
		    	String str = "\n状态日志文件创建异常！！！";
	        	new Print0(str);
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
	    	String str = "\n文件未发现异常！！！";
			new Print0(str);
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
				   new Log0(PassingFileList, string , false);
				   new Log0(PassedFileList , string , true);
				   
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
				   String str = "\n连接服务器错误！！！";
				   new Print0(str);
				   System.out.println(str);
				   System.exit(0);
			   }
			}
		}
	    catch(IOException e)
		{
			String str = "\n文件流创建异常！！！";
			new Print0(str);
			System.out.println(str);
			System.exit(0);
		}
     }
}

class UI0 extends Thread
{
    String deal1 = "";
    String deal2 = "";
    public UI0(String deal1 , String deal2)
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
        	new Print0(str);
            System.out.println(str);
        }
    }
}

class GetFilesList0
{
	String path = "";
	public GetFilesList0(String path)
	{
		this.path = path;
	}
	
	public void getFilesName(String filePath)
    {
		File dir = new File(filePath);
		final long MAX = 10 * 24 * 3600 * 1000;
//        System.out.println(path);
        File [] files = dir.listFiles();
        for(File file:files)
        {
            if(file.isDirectory())
            	getFilesName(file.toString());
            else if(file.isFile() && file.getName().endsWith(".java") && 
            		( System.currentTimeMillis() - file.lastModified() ) < MAX)
            {
//            	System.err.println(file.toString());
            	new Log0("passFileList.log" , file.toString() + "\r\n" , true);
            }
        }
    }
}

class Print0
{
	String str = "";
	public Print0(String str)
	{
		this.str = str;
		Calendar calendar = Calendar.getInstance();
		str = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS")).format(calendar.getTime())  + "\r\n" + str + "\r\n";
		String path = "debug.log";
		new Log0(path , str , true);
		System.out.println(str);
	}
}

