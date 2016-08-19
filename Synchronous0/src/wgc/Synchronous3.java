package wgc;

import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.HttpURLConnection;
import java.net.URL;
public class Synchronous3
{
    public static void main(String[] args)
    {
    	
        String urlPath = "http://127.0.0.1:8080/index.jsp";
        String filePath = "E:\\MyFiles\\java\\projects\\SynchronousFinal\\SynchronousTestIn\\specialTest.java";
        
        new Upload1(urlPath , filePath).start();
        new UI1(urlPath , filePath).start();
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
//			if("监听到ctrl + C 停止exit()")
		}
		catch(Exception e)
		{
			System.out.println("\n异常！！！");
		}
	}
}


class Upload0 extends Thread
{
	String urlpath = "";
	String filepath = "";
    public Upload0(String urlpath , String filepath)
    {
        this.urlpath = urlpath;
        this.filepath = filepath;
    }
    public void run()
    {
       System.out.println(urlpath + "\n" + filepath);
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

