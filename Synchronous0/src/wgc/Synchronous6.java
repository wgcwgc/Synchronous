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
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Synchronous6
{
    public static void main(String[] args)
    {
//        String urlPath = "http://127.0.0.1:8080/index.jsp";
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
            System.out.println("\n参考输入格式：" + "-i C:\\testIn -o http://127.0.0.1:8080/index.jsp");
            System.exit(0);
        }
        else
        {
            if(args.length != 4)
            {
                System.out.println("\n输入参数不正确！！！");
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
                    System.out.println("\n输入格式不正确！！！");
                    System.exit(0);
                }
            }
        }
        File filepath = new File(filePath);
        if((!filepath.isDirectory() && !filepath.isFile()) || !filepath.exists())
        {
            System.out.println("\n输入目录不存在或者格式不正确！！！");
            System.exit(0);
        }

        new Upload3(filePath , urlPath).start();
        new UI3(filePath , urlPath).start();

        System.out.println("\n");
    }
}


class Log1
{
    String filePath = "";
    String fileList = "";
    boolean flog = false;
    public Log1(String filePath , String fileList , boolean flog)
    {
        this.filePath = filePath;
        this.fileList = fileList;
        this.flog = flog;
        
        File filepath = new File(filePath);
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
	        System.out.println("\n状态日志文件创建异常！！！");
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
	        System.out.println("\n状态日志文件写入异常！！！");
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
	            System.out.println("\n文件流关闭异常！！！");
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
//       System.out.println(filepath + "\n" + urlpath);
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
//           httpUrlConnection.setRequestProperty("content-type", "audio/wav");
            httpUrlConnection.setRequestProperty("content-type", "java/*");
//            Date date = new Date();
//            int year = date.getYear() + 1900;
//            int month = date.getMonth() + 1;
//            int day = date.getDate();
            Calendar calendar = Calendar.getInstance();
    		int year = calendar.get(Calendar.YEAR);
    		int month = calendar.get(Calendar.MONTH) + 1;
    		int day = calendar.get(Calendar.DATE);
    		String string = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS")).format(calendar.getTime());  
            String PassingFileList = "E:\\MyFiles\\java\\projects\\SynchronousFinal\\SynchronousTestOut\\LogFiles";
            String PassFileList = "E:\\MyFiles\\java\\projects\\SynchronousFinal\\SynchronousTestOut\\LogFiles";
            String PassedFileList = "E:\\MyFiles\\java\\projects\\SynchronousFinal\\SynchronousTestOut\\LogFiles";
            new Log3(PassingFileList + "\\" + year + "\\" + month + "\\" + day + "\\" + "passingFileList.log", string + "\r\n" + filepath + "\r\n" , false);
            new Log3(PassFileList + "\\" + year + "\\" + month + "\\" + day + "\\" + "passFileList.log", string + "\r\n" + filepath + "\r\n" , true);
            new Log3(PassedFileList + "\\" + year + "\\" + month + "\\" + day + "\\" + "passedFileList.log", string + "\r\n" + filepath + "\r\n" , true);
            httpUrlConnection.connect();
            BufferedOutputStream  bufferedOutputStream = new BufferedOutputStream(httpUrlConnection.getOutputStream());
            
            File file = new File(filepath);
            FileInputStream fileInputStream = new FileInputStream(file);
            byte [] bytes = new byte[1024];
            int numReadByte = 0;
            while((numReadByte = fileInputStream.read(bytes , 0 , 1024)) > 0)
            {
                bufferedOutputStream.write(bytes, 0, numReadByte);
            }
            
            httpUrlConnection.disconnect();
            bufferedOutputStream.flush();
            bufferedOutputStream.close();
            fileInputStream.close();
            System.out.println(httpUrlConnection.getResponseCode());
            if(httpUrlConnection.getResponseCode() == 200)
            {
                new DataInputStream(httpUrlConnection.getInputStream());
            }
        }
        catch(Exception e)
        {
            System.out.println("\n连接服务器错误！！！");
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
//			if("监听到ctrl + C 停止exit()")
        }
        catch(Exception e)
        {
            System.out.println("\n异常！！！");
        }
    }
}


