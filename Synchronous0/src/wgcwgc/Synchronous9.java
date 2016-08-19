package wgcwgc;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;



public class Synchronous9
{
	public static void main(String[] args)
    {
//        String urlPath = "http://127.0.0.1:8080/wgc/doUpload.jsp";
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
            	String str = "\n main方法：输入参数不正确！！！";
            	new Print9(str);
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
                	String str = "\n main方法：输入格式不正确！！！";
                	new Print9(str);
                    System.out.println(str);
                    System.exit(0);
                }
            }
        }
        
        File filepath = new File(filePath);
        if((!filepath.isDirectory() && !filepath.isFile()) || !filepath.exists())
        {
        	String str = "\n main方法：输入目录不存在或者格式不正确！！！";
        	new Print9(str);
            System.out.println(str);
            System.exit(0);
        }
        
        GetFilesList9 getFilesList = new GetFilesList9(filePath);
        getFilesList.start();
        
//        try
//        {
//			Thread.sleep(10000);
//		}
//        catch(InterruptedException e)
//        {
//			String str = "\n main方法：线程休眠异常！！！";
//        	new Print(str);
//	        System.out.println(str);
//	        System.exit(0);
//		}
        Upload9 upload = new Upload9(filePath , urlPath);
        upload.start();
        System.out.println("\n*\t\n*\t\n*\t\n*\t\n*\t\n*\t\n*\t\n*\t\n*\t\n*\n");
        
    }

}


class Log9
{
    String filePathName = "";
    String fileList = "";
    boolean flog = false;
    
    public Log9(String filePathName , String fileList , boolean flog)
    {
        this.filePathName = filePathName;
        this.fileList = fileList;
        this.flog = flog;
        
        String logSavePath = "E:\\MyFiles\\java\\projects\\SynchronousFinal\\SynchronousTestOut\\logFiles";
        String yearMonthDay = logSavePath;
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
		    	String str = "\n Log类：状态日志文件创建异常！！！\n";
		    	str += e1.getMessage();
	        	new Print9(str);
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
	    	String str = "\n Log类：状态日志文件写入异常！！！\n";
	    	str += e.getMessage();
        	new Print9(str);
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
	        	String str = "\n Log类：文件流关闭异常！！！\n";
		    	str += e.getMessage();
	        	new Print9(str);
	            System.out.println(str);
	            System.exit(0);
	        }
	    }
    }
}

class Upload9 extends Thread
{
    String filepath = "";
    String urlpath = "";
    
    public Upload9()
    {
    	
    }
    
    public Upload9(String filepath , String urlpath)
    {
        this.filepath = filepath;
        this.urlpath = urlpath;
    }
    
	public void run()
    {
		new GetFilesList9().getFilesName(filepath);
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
		    	String str = "\n Upload类：状态日志文件创建异常！！！\n";
		    	str += e1.getMessage();
	        	new Print9(str);
		        System.out.println(str);
		        System.exit(0);
		    }
	    }
	    
	    if(filepaths.length() == 0)
	    {
	    	new GetFilesList9().getFilesName(filepath);
	    }
	    handleFiles(filepath , fileName , filename);
    }
	/*
	 * filepaths 扫描目录
	 * filePath  待传文件的文件
	 * filename  log保存目录
	 */
	void handleFiles(String filepaths , String filePath , String filename)
	{
        File fileList = new File(filePath);
	    BufferedReader bufferedReader = null;
	    try
	    {
			bufferedReader = new BufferedReader(new FileReader(fileList));
		}
	    catch(FileNotFoundException e1)
	    {
	    	String str = "\n Upload类：文件未发现异常！！！\n";
	    	str += e1.getMessage();
			new Print9(str);
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
				   new Log9(PassingFileList, string , false);
				   
				   ArrayList<FormFieldKeyValuePair9> ffkvp = new ArrayList<FormFieldKeyValuePair9>();
			       ffkvp.add(new FormFieldKeyValuePair9("filepaths", filepaths));
			       ffkvp.add(new FormFieldKeyValuePair9("filepath", tempString));
			        
			       ArrayList<UploadFileItem9> ufi = new ArrayList<UploadFileItem9>();
			       ufi.add(new UploadFileItem9("upload", tempString));
			       HttpPostEmulator9 hpe = new HttpPostEmulator9();
			       String response = hpe.sendHttpPostRequest(urlpath , ffkvp , ufi);
			       System.out.println("Responsefrom server is: " + response);
				  
				   String PassedFileList = "passedFileList.log";
				   new Log9(PassedFileList , string , true);
			 	   new DeleteTheFirstLine9(filePath);
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
					   String str = "\n Upload类：状态文件删除和创建异常！！！\n";
				    	str += e.getMessage();
					   new Print9(str);
					   System.out.println(str);
					   System.exit(0);
				   }
				   
			   }
			   catch(Exception e)
			   {
				   String str = "\n Upload类：连接服务器错误！！！\n";
			    	str += e.getMessage();
				   new Print9(str);
				   System.out.println(str);
				   System.exit(0);
			   }
			}
		}
	    catch(IOException e)
		{
			String str = "\n Upload类：文件流创建异常！！！\n";
	    	str += e.getMessage();
			new Print9(str);
			System.out.println(str);
			System.exit(0);
		}
     }
}

class HttpPostEmulator9
{
    private static final String BOUNDARY ="----------HV2ymHFg03ehbqgZCaKO6jyH";
    public String sendHttpPostRequest(String serverUrl , ArrayList<FormFieldKeyValuePair9>generalFormFields ,
    		ArrayList<UploadFileItem9>filesToBeUploaded)
    {
        URL url = null;
		try
		{
			url = new URL(serverUrl);
		}
		catch(MalformedURLException e)
		{
			String str = "\n HttpPostEmulator类：url对象创建异常！！！\n";
	    	str += e.getMessage();
			new Print9(str);
			System.out.println(str);
			System.exit(0);
		}
        HttpURLConnection connection = null;
		try
		{
			connection = (HttpURLConnection) url.openConnection();
		}
		catch(IOException e)
		{
			String str = "\n HttpPostEmulator类：服务器连接异常！！！\n";
	    	str += e.getMessage();
			new Print9(str);
			System.out.println(str);
			System.exit(0);
		}
        connection.setDoOutput(true);
        connection.setDoInput(true);
        connection.setUseCaches(false);
        try
        {
			connection.setRequestMethod("POST");
		}
        catch(ProtocolException e)
        {
        	String str = "\n HttpPostEmulator类：服务器post异常！！！\n";
	    	str += e.getMessage();
			new Print9(str);
			System.out.println(str);
			System.exit(0);
		}
        connection.setRequestProperty("Connection","Keep-Alive");
        connection.setRequestProperty("Charset","UTF-9");
        connection.setRequestProperty("Content-Type","multipart/form-data; boundary=" + BOUNDARY);
        // 头
        String boundary = BOUNDARY;
        // 传输内容
        StringBuffer contentBody =new StringBuffer("--" + BOUNDARY);
        // 尾
        String endBoundary ="\r\n--" + boundary + "--\r\n";
        OutputStream out = null;
		try
		{
			out = connection.getOutputStream();
		}
		catch(IOException e)
		{
			String str = "\n HttpPostEmulator类：服务器输出流连接异常！！！\n";
	    	str += e.getMessage();
			new Print9(str);
			System.out.println(str);
			System.exit(0);
		}
        for(FormFieldKeyValuePair9 ffkvp : generalFormFields)
        {
            contentBody.append("\r\n")
            .append("Content-Disposition: form-data; name=\"")
            .append(ffkvp.getKey() + "\"")
            .append("\r\n")
            .append("\r\n")
            .append(ffkvp.getValue())
            .append("\r\n")
            .append("--")
            .append(boundary);
        }
        String boundaryMessage1 =contentBody.toString();
        try
        {
			out.write(boundaryMessage1.getBytes("utf-8"));
		}
        catch(UnsupportedEncodingException e)
        {
        	String str = "\n HttpPostEmulator类：服务器不支持的编码方式异常1！！！\n";
	    	str += e.getMessage();
			new Print9(str);
			System.out.println(str);
			System.exit(0);
		}
        catch(IOException e)
		{
        	String str = "\n HttpPostEmulator类：文件流输出异常1！！！\n";
	    	str += e.getMessage();
			new Print9(str);
			System.out.println(str);
			System.exit(0);
		}
        // 2. 处理文件上传
        for(UploadFileItem9 ufi :filesToBeUploaded)
        {
            contentBody = new StringBuffer();
            contentBody.append("\r\n")
            .append("Content-Disposition:form-data; name=\"")
            .append(ufi.getFormFieldName() +"\"; ")   // form中field的名称
            .append("filename=\"")
            .append(ufi.getFileName() +"\"")   //上传文件的文件名，包括目录
            .append("\r\n")
            .append("Content-Type:application/octet-stream")
            .append("\r\n\r\n");
            String boundaryMessage2 = contentBody.toString();
            try
            {
				out.write(boundaryMessage2.getBytes("utf-8"));
			}
            catch(UnsupportedEncodingException e)
            {
            	String str = "\n HttpPostEmulator类：服务器不支持的编码方式异常2！！！\n";
    	    	str += e.getMessage();
    			new Print9(str);
    			System.out.println(str);
    			System.exit(0);
			}
            catch(IOException e)
            {
            	String str = "\n HttpPostEmulator类：文件流输出异常2！！！\n";
    	    	str += e.getMessage();
    			new Print9(str);
    			System.out.println(str);
    			System.exit(0);
			}
            // 开始真正向服务器写文件
            File file = new File(ufi.getFileName());
            DataInputStream dis = null;
			try
			{
				dis = new DataInputStream(new FileInputStream(file));
			}
			catch(FileNotFoundException e1)
			{
				String str = "\n HttpPostEmulator类：输出文件未发现异常！！！\n";
		    	str += e1.getMessage();
				new Print9(str);
				System.out.println(str);
				System.exit(0);
			}
            int bytes = 0;
            byte[] bufferOut =new byte[(int) file.length()];
            try
            {
				bytes =dis.read(bufferOut);
			}
            catch (IOException e)
            {
            	String str = "\n HttpPostEmulator类：文件流输出异常3！！！\n";
    	    	str += e.getMessage();
    			new Print9(str);
    			System.out.println(str);
    			System.exit(0);
			}
            try
            {
				out.write(bufferOut,0, bytes);
			}
            catch(IOException e)
            {
            	String str = "\n HttpPostEmulator类：文件流输出异常4！！！\n";
    	    	str += e.getMessage();
    			new Print9(str);
    			System.out.println(str);
    			System.exit(0);
			}
            try
            {
				dis.close();
			}
            catch(IOException e)
            {
            	String str = "\n HttpPostEmulator类：文件流关闭异常1！！！\n";
    	    	str += e.getMessage();
    			new Print9(str);
    			System.out.println(str);
    			System.exit(0);
			}
            contentBody.append("------------HV2ymHFg03ehbqgZCaKO6jyH");
            String boundaryMessage = contentBody.toString();
            try
            {
				out.write(boundaryMessage.getBytes("utf-8"));
			}
            catch(UnsupportedEncodingException e)
            {
            	String str = "\n HttpPostEmulator类：服务器不支持的编码方式异常3！！！\n";
    	    	str += e.getMessage();
    			new Print9(str);
    			System.out.println(str);
    			System.exit(0);
			}
            catch (IOException e)
            {
            	String str = "\n HttpPostEmulator类：文件流输出异常5！！！\n";
    	    	str += e.getMessage();
    			new Print9(str);
    			System.out.println(str);
    			System.exit(0);
			}
            //System.out.println(boundaryMessage);
        }
        try
        {
			out.write("------------HV2ymHFg03ehbqgZCaKO6jyH--\r\n".getBytes("UTF-8"));
			out.write(endBoundary.getBytes("utf-8"));
		}
        catch(UnsupportedEncodingException e)
        {
        	String str = "\n HttpPostEmulator类：服务器不支持的编码方式异常4！！！\n";
	    	str += e.getMessage();
			new Print9(str);
			System.out.println(str);
			System.exit(0);
		}
        catch(IOException e)
        {
        	String str = "\n HttpPostEmulator类：文件流输出异常6！！！\n";
	    	str += e.getMessage();
			new Print9(str);
			System.out.println(str);
			System.exit(0);
		}
        finally
        {
        	try
        	{
        		out.close();
				out.flush();
			}
        	catch(IOException e)
        	{
        		String str = "\n HttpPostEmulator类：文件流关闭异常2！！！\n";
    	    	str += e.getMessage();
    			new Print9(str);
    			System.out.println(str);
    			System.exit(0);
			}
        }
        //从服务器获得回答的内容
        String strLine="";
        String strResponse ="";
        InputStream in = null;
		try
		{
			in = connection.getInputStream();
		}
		catch(IOException e)
		{
			String str = "\n HttpPostEmulator类：文件流输入异常1！！！\n";
	    	str += e.getMessage();
			new Print9(str);
			System.out.println(str);
			System.exit(0);
		}
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        try
        {
			while((strLine =reader.readLine()) != null)
			{
			    strResponse +=strLine +"\n";
			}
		}
        catch(IOException e)
        {
        	String str = "\n HttpPostEmulator类：文件流输入异常2！！！\n";
	    	str += e.getMessage();
			new Print9(str);
			System.out.println(str);
			System.exit(0);
		}
        //System.out.print(strResponse);
        return strResponse;
    }
}

class FormFieldKeyValuePair9 implements Serializable
{
    private static final long serialVersionUID = 1L;
    private String key;
    private String value;
    public FormFieldKeyValuePair9(String key, String value)
    {
        this.key = key;
        this.value = value;
    }
    public String getKey()
    {
        return key;
    }
    public void setKey(String key)
    {
        this.key = key;
    }
    public String getValue()
    {
        return value;
    }
    public void setValue(String value)
    {
        this.value = value;
    }
}

class UploadFileItem9 implements Serializable
{
    private static final long serialVersionUID = 1L;
    private String formFieldName;
    private String fileName;
    public UploadFileItem9(String formFieldName, String fileName)
    {
        this.formFieldName = formFieldName;
        this.fileName = fileName;
    }
    public String getFormFieldName()
    {
        return formFieldName;
    }
    public void setFormFieldName(String formFieldName)
    {
        this.formFieldName = formFieldName;
    }
    public String getFileName()
    {
        return fileName;
    }
    public void setFileName(String fileName)
    {
        this.fileName = fileName;
    }
}

class GetFilesList9 extends Thread
{
	String path = "";
	final long MAX = 10 * 24 * 3600 * 1000;
	final long MIN = 1 * 1 * 10 * 1000;
	public GetFilesList9()
	{
		
	}
	public GetFilesList9(String path)
	{
		this.path = path;
		getFilesName(path);
	}
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
            	new Log9("passFileList.log" , file.toString() + "\r\n" , true);
            }
        }
    }
	
	public boolean judge(File file)
	{
		if(file.isFile() && ( file.getName().endsWith("java") || file.getName().endsWith("wav") )&& 
        		( System.currentTimeMillis() - file.lastModified() ) < MAX)
		{
			long beforeTime = file.lastModified();
			try
			{
				Thread.sleep(MIN);
			}
			catch(InterruptedException e)
			{
				String str = "\n GetFilesList类：线程休眠异常！！！\n";
		    	str += e.getMessage();
	        	new Print9(str);
		        System.out.println(str);
		        System.exit(0);
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
		    	String str = "\n GetFilesList类：状态日志文件创建异常！！！\n";
		    	str += e1.getMessage();
	        	new Print9(str);
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
			String str = "\n GetFilesList类：检查已传文件内是否存在当前文件名。已传文件清单未发现异常！！！\n";
	    	str += e1.getMessage();
	       	new Print9(str);
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
			String str = "\n GetFilesList类：检查已传文件内是否存在当前文件名。IO异常！！！\n";
	    	str += e.getMessage();
	       	new Print9(str);
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
				String str = "\n GetFilesList类：检查已传文件内是否存在当前文件名。关闭流异常！！！\n";
		    	str += e.getMessage();
	        	new Print9(str);
	            System.out.println(str);
			}
		}
	
		return false;
	}
	
}

class DeleteTheFirstLine9
{
	String filepath = "";
	public DeleteTheFirstLine9(String filepath)
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
			String str = "\n DeleteTheFirstLine类：待传文件清单未发现异常！！！\n";
	    	str += e1.getMessage();
        	new Print9(str);
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
			String str = "\n DeleteTheFirstLine类：待传文件清单IO异常！！！\n";
	    	str += e.getMessage();
        	new Print9(str);
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
				String str = "\n DeleteTheFirstLine类：待传文件清单关闭流异常！！！\n";
	        	new Print9(str);
	            System.out.println(str);
			}
		}
	}
}

class Print9
{
	String str = "";
	public Print9(String str)
	{
		this.str = str;
		Calendar calendar = Calendar.getInstance();
		str = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS")).format(calendar.getTime())  + "\r\n" + str + "\r\n";
		String path = "debug.log";
		new Log9(path , str , true);
		System.out.println(str);
	}
}


