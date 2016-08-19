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
//import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Synchronous6
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
            	new Print6(str);
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
                	new Print6(str);
                    System.out.println(str);
                    System.exit(0);
                }
            }
        }
        
        File filepath = new File(filePath);
        if((!filepath.isDirectory() && !filepath.isFile()) || !filepath.exists())
        {
        	String str = "\n输入目录不存在或者格式不正确！！！";
        	new Print6(str);
            System.out.println(str);
            System.exit(0);
        }
//        try
//        {
//			Thread.sleep(10000);
//		}
//        catch(InterruptedException e)
//        {
//			e.printStackTrace();
//		}
        Upload6 upload = new Upload6(filePath , urlPath);
        upload.start();
        GetFilesList6 getFilesList = new GetFilesList6();
        getFilesList.start();
        System.out.println("\n*\t\n*\t\n*\t\n*\t\n*\t\n*\t\n*\t\n*\t\n*\t\n*\n");
        
    }

}


class Log6
{
    String filePathName = "";
    String fileList = "";
    boolean flog = false;
    
    /*
     * filePath log's paths
     * fileList log's contents
     * flog		log cover(false) or not(true)
     */
    public Log6(String filePathName , String fileList , boolean flog)
    {
        this.filePathName = filePathName;
        this.fileList = fileList;
        this.flog = flog;
        
//        Calendar calendar = Calendar.getInstance();
//		int year = calendar.get(Calendar.YEAR);
//		int month = calendar.get(Calendar.MONTH) + 1;
//		int day = calendar.get(Calendar.DATE);
        String logSavePath = "E:\\MyFiles\\java\\projects\\SynchronousFinal\\SynchronousTestOut\\logFiles";
        String yearMonthDay = logSavePath;
        
//        if(filePathName.contains("passedFileList"))
//            yearMonthDay = logSavePath + "\\" + year + "\\" + month + "\\" + day + "\\" + filePathName;
//        else
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
		    	String str = "\n状态日志文件创建异常！！！";
	        	new Print6(str);
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
        	new Print6(str);
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
	        	new Print6(str);
	            System.out.println(str);
	            System.exit(0);
	        }
	    }
    }
}

class Upload6 extends Thread
{
    String filepath = "";
    String urlpath = "";
    
    public Upload6()
    {
    	
    }
    
    public Upload6(String filepath , String urlpath)
    {
        this.filepath = filepath;
        this.urlpath = urlpath;
    }
    
	public void run()
    {
		new GetFilesList6().getFilesName(filepath);
		String filename = "E:\\MyFiles\\java\\projects\\SynchronousFinal\\SynchronousTestOut\\LogFiles";
		String fileName = filename + "\\" + "passFileList.log";
		File filepaths = new File(fileName);
	    if(!filepaths.getParentFile().exists() || !filepaths.exists())
	    {
	        filepaths.getParentFile().mkdirs();
		    try
		    {
		        filepaths.createNewFile();
		    }
		    catch(Exception e1)
		    {
		    	String str = "\n状态日志文件创建异常！！！";
	        	new Print6(str);
		        System.out.println(str);
		        System.exit(0);
		    }
	    }
	    
	    if(filepaths.length() == 0)
	    {
	    	new GetFilesList6().getFilesName(filepath);
	    }
	    handleFiles(fileName , filename);
    }
	
	void handleFiles(String filePath , String filename)
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
			new Print6(str);
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
				   Calendar calendar = Calendar.getInstance();
				   String string = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS")).format(calendar.getTime())  + "\r\n" + files.getPath() + "\r\n";  
				   String PassingFileList = "passingFileList.log";
				   new Log6(PassingFileList, string , false);
				   
				   URL url = new URL(urlpath);
				   HttpURLConnection httpUrlConnection = (HttpURLConnection)url.openConnection();
				   httpUrlConnection.setDoInput(true);
				   httpUrlConnection.setDoOutput(true);
				   httpUrlConnection.setUseCaches(false);
				   httpUrlConnection.setConnectTimeout(30000);
				   httpUrlConnection.setReadTimeout(30000);
				   httpUrlConnection.setRequestMethod("POST");
				   httpUrlConnection.setInstanceFollowRedirects(true);
//				   httpUrlConnection.setRequestProperty("content-type", "application/x-www-form-urlencoded");
				   httpUrlConnection.setRequestProperty("content-type", "java/*");
//				   httpUrlConnection.setRequestProperty("content-type", "audio/wav");
				   httpUrlConnection.connect();
				   BufferedOutputStream  bufferedOutputStream = new BufferedOutputStream(httpUrlConnection.getOutputStream());
				   
				   FileInputStream fileInputStream = new FileInputStream(files);
				   byte [] bytes = new byte[1024];
				   int numReadByte = 0;
				   while((numReadByte = fileInputStream.read(bytes , 0 , 1024)) > 0)
				   {
					   bufferedOutputStream.write(bytes, 0, numReadByte);
				   }
//				   String content = "filePath=" + URLEncoder.encode(filepath , "UTF-8");
//				   bufferedOutputStream.write(content.getBytes());
				   
//         	   httpUrlConnection.disconnect();
				   bufferedOutputStream.flush();
				   bufferedOutputStream.close();
				   fileInputStream.close();
//     		   System.out.println(httpUrlConnection.getResponseCode());
				   if(httpUrlConnection.getResponseCode() == 200)
				   {
					   new DataInputStream(httpUrlConnection.getInputStream());
				   }
				   
				   String PassedFileList = "passedFileList.log";
				   new Log6(PassedFileList , string , true);
				   new DeleteTheFirstLine6(filePath);
				   try
				   {
					   File passingFileName = new File(filename + "\\" + PassingFileList);
					   passingFileName.delete();
					   if(!passingFileName.getParentFile().exists() || !passingFileName.exists())
					    {
						   passingFileName.getParentFile().mkdirs();
						   passingFileName.createNewFile();
					    }
				   }
				   catch(Exception e)
				   {
					   String str = "\n状态文件删除和创建异常！！！";
					   new Print6(str);
					   System.out.println(str);
					   System.exit(0);
				   }
				   
			   }
			   catch(Exception e)
			   {
				   String str = "\n连接服务器错误！！！";
				   new Print6(str);
				   System.out.println(str);
				   System.exit(0);
			   }
			}
		}
	    catch(IOException e)
		{
			String str = "\n文件流创建异常！！！";
			new Print6(str);
			System.out.println(str);
			System.exit(0);
		}
     }
}

class GetFilesList6 extends Thread
{
	String path = "";
	final long MAX = 10 * 24 * 3600 * 1000;
	final long MIN = 1 * 1 * 10 * 1000;
	public GetFilesList6()
	{
		
	}
	public GetFilesList6(String path)
	{
		this.path = path;
	}
//	public void run()
//	{
//		getFilesName(path);
//	}
	public void getFilesName(String filePath)
    {
		File dir = new File(filePath);
        File [] files = dir.listFiles();
        for(File file:files)
        {
            if(file.isDirectory())
            	getFilesName(file.toString());
            else if(judge(file))
            {
            	new Log6("passFileList.log" , file.toString() + "\r\n" , true);
            }
        }
    }
	
	public boolean judge(File file)
	{
		if(file.isFile() && ( file.getName().endsWith(".java") || file.getName().endsWith(".wav") )&& 
        		( System.currentTimeMillis() - file.lastModified() ) < MAX)
		{
			long beforeTime = file.lastModified();
			try
			{
				Thread.sleep(MIN);
			}
			catch(InterruptedException e)
			{
				e.printStackTrace();
			}
			long afterTime = file.lastModified();
			if(afterTime - beforeTime == 0 && !isUpLoad(file))
				return true;
		}
		return false;
	}
	
	public boolean isUpLoad(File file)
	{
		String filepath = file.toString();
		String path = "E:\\MyFiles\\java\\projects\\SynchronousFinal\\SynchronousTestOut\\logFiles\\passedFileList.log";
		File paths = new File(path);
		if(!paths.getParentFile().exists() || !paths.exists())
	    {
			paths.getParentFile().mkdirs();
		    try
		    {
		    	paths.createNewFile();
		    }
		    catch(Exception e1)
		    {
		    	String str = "\n状态日志文件创建异常！！！";
	        	new Print6(str);
		        System.out.println(str);
		        System.exit(0);
		    }
	    }
		BufferedReader bufferedReader = null;
		try
		{
			bufferedReader = new BufferedReader(new FileReader(path));
		}
		catch(FileNotFoundException e1)
		{
			String str = "\n检查已传文件内是否存在当前文件名。已传文件清单未发现异常！！！";
	       	new Print6(str);
	        System.out.println(str);
		}
		String temp = null;
		try
		{
			while((temp = bufferedReader.readLine()) != null)
			{
			    if(filepath.equals(temp))
			    {
			    	return true;
			    }
			}
		}
		catch (IOException e)
		{
			String str = "\n检查已传文件内是否存在当前文件名。IO异常！！！";
	       	new Print6(str);
	        System.out.println(str);
		}
		finally
		{
			try
			{
				bufferedReader.close();
			}
			catch(IOException e)
			{
				String str = "\n检查已传文件内是否存在当前文件名。关闭流异常！！！";
	        	new Print6(str);
	            System.out.println(str);
			}
		}
	
		return false;
	}
	
}

class DeleteTheFirstLine6
{
	String filepath = "";
	public DeleteTheFirstLine6(String filepath)
	{
		this.filepath = filepath;
		deleteTheFirstLine(filepath);
	}
	
	public static void deleteTheFirstLine(String path)
	{
		int lineDel = 1;
		BufferedReader bufferedReader = null;
		try
		{
			bufferedReader = new BufferedReader(new FileReader(path));
		}
		catch(FileNotFoundException e1)
		{
			String str = "\n待传文件清单未发现异常！！！";
        	new Print6(str);
            System.out.println(str);
		}
		
		StringBuffer stringBuffer = new StringBuffer(4096);
		String temp = null;
		int line = 0;
		BufferedWriter bufferedWriter = null;
		try
		{
			while((temp = bufferedReader.readLine()) != null)
			{
			        line ++;
			        if(line == lineDel)
			        	continue;
			        stringBuffer.append(temp).append( "\r\n");
			}
			bufferedWriter = new BufferedWriter(new FileWriter(path));
			bufferedWriter.write(stringBuffer.toString());
		}
		catch (IOException e)
		{
			String str = "\n待传文件清单IO异常！！！";
        	new Print6(str);
            System.out.println(str);
		}
		finally
		{
			try
			{
				bufferedWriter.close();
				bufferedReader.close();
			}
			catch(IOException e)
			{
				String str = "\n待传文件清单关闭流异常！！！";
	        	new Print6(str);
	            System.out.println(str);
			}
		}
	}
}

class Print6
{
	String str = "";
	public Print6(String str)
	{
		this.str = str;
		Calendar calendar = Calendar.getInstance();
		str = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS")).format(calendar.getTime())  + "\r\n" + str + "\r\n";
		String path = "debug.log";
		new Log6(path , str , true);
		System.out.println(str);
	}
}


