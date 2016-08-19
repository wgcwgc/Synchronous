import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 
 * @author           Administrator
 * @copyright        wgcwgc
 * @date             2016年4月27日
 * @time             下午4:15:15
 * @project_name     HandleFinal
 * @package_name     
 * @file_name        Handle.java
 * @type_name        Handle
 * @enclosing_type   
 * @tags             
 * @todo     
 * 
1. 当配置文件不存在时或打不开时，输出日志又去查看配置文件，试图获得日志文件路径，然后又因为找不到配置文件出错，然后又要记录日志，
          然后。。。不断循环。这种情况直接打印到屏幕就好了。
2. 程序多处读取配置文件，应该写一个单独的类，用于读取配置文件，并保存在内存中。其他类只需要通过方法获取某项配置值即可，不再去读取配置文件。
3. 每一个类应该单独一个文件，而不是集中在一个文件中。可读性差。
4. 配置文件的目录应已当前目录为相对目录较好，比如./，而不是../
5. 判断服务器返回时，改成英文：OK，因为中文字符存在编码问题
6. 395行：handleFiles --> ufi.add(new UploadFileItem("upload" , tempString)); upload应该改为 file
7. upload线程不能起无数个，应该只有1个，或者有限的几个（线程池），否则传一个文件就起一个线程，很快系统就崩溃了。
          比如传一个文件如果失败后，再次传送时又起了一个线程，如此反复，肯定是不行的。
8. 把这几个问题改好后，你可以开始深入学习下android开发，用最新的SDK
9. 51节后我给你派个任务

 * 
 * @others           
 *
 */
public class Synchronous
{
	public static void main(String [] args)
	{
		String urlPath = "";
		String filePath = "";
//		String string = "";
//		if(args.length != 0)
//			string = args[0];
//		if(string.isEmpty() || string.equalsIgnoreCase("-h")
//				|| string.equalsIgnoreCase("-help")
//				|| string.equalsIgnoreCase("help"))
//		{
//			System.out.println("\n\n\t\t\tFiles Synchronous\n");
//			System.out.println("实现对本地文件同步到服务器的操作.\n\n选项：");
//			System.out.println("\t-i\t" + "待同步文件目录。");
//			System.out.println("\t-o\t" + "服务器地址。");
//			System.out.println("\t-h\t" + "帮助。");
//			System.out.println("\t q\t" + "退出。");
//			System.out
//					.println("\n参考输入格式："
//							+ "-i C:\\testIn -o http://127.0.0.1:8080/wgc/doUpload.jsp");
//			System.exit(0);
//		}
//		else
//		{
//			if(args.length != 4)
//			{
//				String str = "\n main方法：输入参数不正确！！！";
//				new Print(str);
//				System.out.println(str);
//				System.exit(0);
//			}
//			for(int i = 0 ; i < args.length ; i ++ )
//			{
//				if(args[i].equals("-i"))
//				{
//					filePath = args[i + 1];
//					i ++ ;
//				}
//				else if(args[i].equals("-o"))
//				{
//					urlPath = args[i + 1];
//					i ++ ;
//				}
//				else
//				{
//					String str = "\n main方法：输入格式不正确！！！";
//					new Print(str);
//					System.out.println(str);
//					System.exit(0);
//				}
//			}
//		}
		
		 filePath =
		 "E:\\MyFiles\\java\\projects\\SynchronousFinal\\SynchronousTestIn";
		 urlPath = "http://127.0.0.1:8080/wgc/doUpload.jsp";
		
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
			
			GetFilesList getFilesList = new GetFilesList(filePath);
			getFilesList.setIsrunning(true);
			getFilesList.start();
			
			try
			{
//				String packagePath = this.getClass().getResource("").getPath().toString();
//				packagePath = packagePath.substring(1 , packagePath.indexOf("bin"));
//				ReadIniFile readIniFile = new ReadIniFile(packagePath + "/config/wgc.ini");
				String name = "wgc";
				ReadIniFile readIniFile = new ReadIniFile(name);
				String Wait = readIniFile.getValue("Time" , "wait");
				final long wait = Long.parseLong(Wait);
				Thread.sleep(wait);
			}
			catch(InterruptedException e)
			{
				String str = "\n main方法：线程休眠异常！！！";
				new Print(str);
				System.out.println(str);
				System.exit(0);
			}
			
			Upload upload = new Upload(filePath , urlPath);
			upload.setIsrunning(true);
			upload.start();
		}
		
	}
	
}

class ReadIniFile
{
	@SuppressWarnings("rawtypes")
	HashMap sections = new HashMap();
	String currentSecion;
	Properties current;
	
	@SuppressWarnings("unchecked")
	public ReadIniFile(String filename)
	{
		BufferedReader reader = null;
		try
		{
//			reader = new BufferedReader(new FileReader(filename));
			String packagePath = this.getClass().getResource("").getPath();
			packagePath = packagePath.substring(1 , packagePath.indexOf("bin"));
			if(filename.equals("wgc"))
			{
				filename = packagePath + "config/wgc.ini";
//				System.out.println(filename + "*******************");
				if(!new File(filename.toString()).exists())
				{
					String str = "\n ReadIniFile类：配置文件不存在或者格式不正确！！！\n";
					new Print(str);
					System.out.println(str);
					System.exit(0);
				}
				reader = new BufferedReader(new FileReader(filename));
			}
			else
			{
				String str = "\n ReadIniFile类：配置文件不存在或者格式不正确！！！\n";
				new Print(str);
				System.out.println(str);
				System.exit(0);
			}
		}
		catch(Exception e)
		{
			String str = "\n ReadIniFile类：配置文件不存在或者格式不正确！！！\n";
			str += e.getMessage();
			new Print(str);
			System.out.println(str);
			System.exit(0);
		}
		
		try
		{
			String line;
			try
			{
				while( ( line = reader.readLine() ) != null)
				{
					line = line.trim();
					if(line.matches("\\[.*\\]"))
					{
						currentSecion = line.replaceFirst("\\[(.*)\\]" , "$1");
						current = new Properties();
						sections.put(currentSecion , current);
					}
					else if(line.matches(".*=.*"))
					{
						if(current != null)
						{
							int i = line.indexOf('=');
							String name = line.substring(0 , i);
							String value = line.substring(i + 1);
							current.setProperty(name , value);
						}
					}
				}
			}
			catch(Exception e)
			{
				String str = "\n ReadIniFile类：配置文件读取异常！！！\n";
				str += e.getMessage();
				new Print(str);
				System.out.println(str);
				System.exit(0);
			}
		}
		catch(Exception e)
		{
			String str = "\n ReadIniFile类：配置文件内容不合法！！！\n";
			str += e.getMessage();
			new Print(str);
			System.out.println(str);
			System.exit(0);
		}
		finally
		{
			try
			{
				reader.close();
			}
			catch(Exception e)
			{
				String str = "\n ReadIniFile类：配置文件关闭异常！！！\n";
				str += e.getMessage();
				new Print(str);
				System.out.println(str);
				System.exit(0);
			}
		}
	}
	
	public String getValue(String section , String name)
	{
		Properties p = (Properties) sections.get(section);
		if(p == null)
		{
			return null;
		}
		String value = p.getProperty(name);
		return value;
	}
	
}

class Log
{
	String filePathName = "";
	String fileList = "";
	boolean flog = false;
	
	public Log(String filePathName , String fileList , boolean flog)
	{
		this.filePathName = filePathName;
		this.fileList = fileList;
		this.flog = flog;
		String name = "wgc";
		ReadIniFile readIniFile = new ReadIniFile(name);
		String logSavePath = readIniFile.getValue("LogFiles" , "logSavePath");
		String yearMonthDay = logSavePath;
		yearMonthDay = logSavePath + "\\" + filePathName;
		File filepath = new File(yearMonthDay);
		if( ! filepath.getParentFile().exists() || ! filepath.exists())
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
				new Print(str);
				System.out.println(str);
				System.exit(0);
			}
		}
		
		BufferedWriter bufferedWriter = null;
		try
		{
			if(flog)
				bufferedWriter = new BufferedWriter(new FileWriter(filepath ,
						true));
			else
				bufferedWriter = new BufferedWriter(new FileWriter(filepath));
			bufferedWriter.write(fileList);
		}
		catch(IOException e)
		{
			String str = "\n Log类：状态日志文件写入异常！！！\n";
			str += e.getMessage();
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
				String str = "\n Log类：文件流关闭异常！！！\n";
				str += e.getMessage();
				new Print(str);
				System.out.println(str);
				System.exit(0);
			}
		}
	}
	
}

class UI extends Thread
{
	public void run()
	{
		Scanner cin = new Scanner(System.in);
		if(cin.nextLine().toLowerCase().equals("q"))
		{
			System.out.println("正在结束，请稍候，，，\n");
			Upload upload = new Upload();
			upload.setIsrunning(false);
			System.out.println("正在结束，请稍候，，，\n");
			GetFilesList getFilesList = new GetFilesList();
			getFilesList.setIsrunning(false);
			System.out.println("已结束，谢谢您的使用和支持！！！");
			System.exit(0);
			cin.close();
		}
	}
}

class Upload extends Thread
{
	String filepath = "";
	String urlpath = "";
	
	public Upload()
	{
		
	}
	
	public Upload(String filepath , String urlpath)
	{
		this.filepath = filepath;
		this.urlpath = urlpath;
	}
	
	boolean isrunning = true;
	
	public boolean isIsrunning()
	{
		return isrunning;
	}
	
	public void setIsrunning(boolean isrunning)
	{
		this.isrunning = isrunning;
	}
	
	public void run()
	{
		new GetFilesList(filepath).getFilesName(filepath);
		String name = "wgc";
		ReadIniFile readIniFile = new ReadIniFile(name);
		String filename = readIniFile.getValue("LogFiles" , "logSavePath");
		String fileName = filename + "\\" + "passFileList.log";
		File filepaths = new File(fileName);
		if( ! filepaths.getParentFile().exists() || ! filepaths.exists())
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
				new Print(str);
				System.out.println(str);
				System.exit(0);
			}
		}
		
		if(filepaths.length() == 0)
		{
			new GetFilesList(filepath).getFilesName(filepath);
		}
		
		if(isrunning)
			handleFiles(filepath , fileName , filename);
	}
	
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
			new Print(str);
			System.out.println(str);
			System.exit(0);
		}
		
		try
		{
			String tempString = "";
			
			while(isrunning
					&& ( tempString = bufferedReader.readLine() ) != null)
			{
				try
				{
					File files = new File(tempString);
					Calendar calendar = Calendar.getInstance();
					String string = ( new SimpleDateFormat(
							"yyyy-MM-dd HH:mm:ss:SSS") ).format(calendar
							.getTime())
							+ "\r\n" + files.getPath() + "\r\n";
					String PassingFileList = "passingFileList.log";
					new Log(PassingFileList , string , false);
					
					ArrayList <FormFieldKeyValuePair> ffkvp = new ArrayList <FormFieldKeyValuePair>();
					ffkvp.add(new FormFieldKeyValuePair(filepaths , tempString));
					
					ArrayList <UploadFileItem> ufi = new ArrayList <UploadFileItem>();
					ufi.add(new UploadFileItem("file" , tempString));
					HttpPostEmulator hpe = new HttpPostEmulator();
					String response = hpe.sendHttpPostRequest(urlpath , ffkvp ,
							ufi);
					System.out.println("Response from server is: " + response);
					
					if(response.contains("OK"))
					{
						String PassedFileList = "passedFileList.log";
						new Log(PassedFileList , string , true);
						new DeleteTheFirstLine(filePath);
						try
						{
							File passingFileName = new File(filename + "\\"
									+ PassingFileList);
							passingFileName.delete();
							if( ! passingFileName.getParentFile().exists()
									|| ! passingFileName.exists())
							{
								passingFileName.getParentFile().mkdirs();
								passingFileName.createNewFile();
							}
						}
						catch(Exception e)
						{
							String str = "\n Upload类：状态文件删除和创建异常！！！\n";
							str += e.getMessage();
							new Print(str);
							System.out.println(str);
							System.exit(0);
						}
					}
					
				}
				catch(Exception e)
				{
					String str = "\n Upload类：连接服务器错误！！！\n";
					str += e.getMessage();
					new Print(str);
					System.out.println(str);
					System.exit(0);
				}
			}
		}
		catch(IOException e)
		{
			String str = "\n Upload类：文件流创建异常！！！\n";
			str += e.getMessage();
			new Print(str);
			System.out.println(str);
			System.exit(0);
		}
	}
	
}

class HttpPostEmulator
{
	private static final String BOUNDARY = "----------HV2ymHFg03ehbqgZCaKO6jyH";
	
	public String sendHttpPostRequest(String serverUrl ,
			ArrayList <FormFieldKeyValuePair> generalFormFields ,
			ArrayList <UploadFileItem> filesToBeUploaded)
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
			new Print(str);
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
			new Print(str);
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
			new Print(str);
			System.out.println(str);
			System.exit(0);
		}
		
		connection.setRequestProperty("Connection" , "Keep-Alive");
		connection.setRequestProperty("Charset" , "gb2312");
		connection.setRequestProperty("Content-Type" ,
				"multipart/form-data; boundary=" + BOUNDARY);
		String boundary = BOUNDARY;
		StringBuffer contentBody = new StringBuffer("--" + BOUNDARY);
		String endBoundary = "\r\n--" + boundary + "--\r\n";
		OutputStream out = null;
		
		try
		{
			out = connection.getOutputStream();
		}
		catch(IOException e)
		{
			String str = "\n HttpPostEmulator类：服务器输出流连接异常！！！\n";
			str += e.getMessage();
			new Print(str);
			System.out.println(str);
			System.exit(0);
		}
		
		for(FormFieldKeyValuePair ffkvp : generalFormFields)
		{
			contentBody.append("\r\n")
					.append("Content-Disposition: form-data; name=\"")
					.append(ffkvp.getKey() + "\"").append("\r\n")
					.append("\r\n").append(ffkvp.getValue()).append("\r\n")
					.append("--").append(boundary);
		}
		
		String boundaryMessage = contentBody.toString();
		try
		{
			out.write(boundaryMessage.getBytes());
		}
		catch(UnsupportedEncodingException e)
		{
			String str = "\n HttpPostEmulator类：服务器不支持的编码方式异常1！！！\n";
			str += e.getMessage();
			new Print(str);
			System.out.println(str);
			System.exit(0);
		}
		catch(IOException e)
		{
			String str = "\n HttpPostEmulator类：文件流输出异常1！！！\n";
			str += e.getMessage();
			new Print(str);
			System.out.println(str);
			System.exit(0);
		}
		
		for(UploadFileItem ufi : filesToBeUploaded)
		{
			contentBody = new StringBuffer();
			contentBody.append("\r\n")
					.append("Content-Disposition:form-data; name=\"")
					.append(ufi.getFormFieldName() + "\"; ")
					.append("filename=\"").append(ufi.getFileName() + "\"")
					.append("\r\n")
					.append("Content-Type:application/octet-stream")
					.append("\r\n\r\n");
			String boundaryMessage2 = contentBody.toString();
			try
			{
				out.write(boundaryMessage2.getBytes());
			}
			catch(UnsupportedEncodingException e)
			{
				String str = "\n HttpPostEmulator类：服务器不支持的编码方式异常2！！！\n";
				str += e.getMessage();
				new Print(str);
				System.out.println(str);
				System.exit(0);
			}
			catch(IOException e)
			{
				String str = "\n HttpPostEmulator类：文件流输出异常2！！！\n";
				str += e.getMessage();
				new Print(str);
				System.out.println(str);
				System.exit(0);
			}
			
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
				new Print(str);
				System.out.println(str);
				System.exit(0);
			}
			
			int bytes = 0;
			byte [] bufferOut = new byte [(int) file.length()];
			try
			{
				bytes = dis.read(bufferOut);
			}
			catch(IOException e)
			{
				String str = "\n HttpPostEmulator类：文件流输出异常3！！！\n";
				str += e.getMessage();
				new Print(str);
				System.out.println(str);
				System.exit(0);
			}
			
			try
			{
				out.write(bufferOut , 0 , bytes);
			}
			catch(IOException e)
			{
				String str = "\n HttpPostEmulator类：文件流输出异常4！！！\n";
				str += e.getMessage();
				new Print(str);
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
				new Print(str);
				System.out.println(str);
				System.exit(0);
			}
			
			contentBody = new StringBuffer();
			String boundaryMessage1 = contentBody.toString();
			try
			{
				out.write(boundaryMessage1.getBytes());
			}
			catch(UnsupportedEncodingException e)
			{
				String str = "\n HttpPostEmulator类：服务器不支持的编码方式异常3！！！\n";
				str += e.getMessage();
				new Print(str);
				System.out.println(str);
				System.exit(0);
			}
			catch(IOException e)
			{
				String str = "\n HttpPostEmulator类：文件流输出异常5！！！\n";
				str += e.getMessage();
				new Print(str);
				System.out.println(str);
				System.exit(0);
			}
		}
		
		try
		{
			out.write(endBoundary.getBytes());
		}
		catch(UnsupportedEncodingException e)
		{
			String str = "\n HttpPostEmulator类：服务器不支持的编码方式异常4！！！\n";
			str += e.getMessage();
			new Print(str);
			System.out.println(str);
			System.exit(0);
		}
		catch(IOException e)
		{
			String str = "\n HttpPostEmulator类：文件流输出异常6！！！\n";
			str += e.getMessage();
			new Print(str);
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
				new Print(str);
				System.out.println(str);
				System.exit(0);
			}
		}
		
		String strLine = "";
		String strResponse = "";
		InputStream in = null;
		try
		{
			in = connection.getInputStream();
		}
		catch(IOException e)
		{
			String str = "\n HttpPostEmulator类：文件流输入异常1！！！\n";
			str += e.getMessage();
			new Print(str);
			System.out.println(str);
			System.exit(0);
		}
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		try
		{
			while( ( strLine = reader.readLine() ) != null)
			{
				strResponse += strLine + "\n";
			}
		}
		catch(IOException e)
		{
			String str = "\n HttpPostEmulator类：文件流输入异常2！！！\n";
			str += e.getMessage();
			new Print(str);
			System.out.println(str);
			System.exit(0);
		}
		return strResponse;
		
	}
	
}

class FormFieldKeyValuePair implements Serializable
{
	static final long serialVersionUID = 1L;
	String key;
	String value;
	
	public FormFieldKeyValuePair(String key , String value)
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

class UploadFileItem implements Serializable
{
	static final long serialVersionUID = 1L;
	String formFieldName;
	String fileName;
	
	public UploadFileItem(String formFieldName , String fileName)
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

class GetFilesList extends Thread
{
	String path = "";
	String name = "wgc";
	ReadIniFile readIniFile = new ReadIniFile(name);
	String history = readIniFile.getValue("Time" , "history");
	String delay = readIniFile.getValue("Time" , "delay");
	final long MAX = Long.parseLong(history);
	final long MIN = Long.parseLong(delay);
	
	boolean isrunning = true;
	
	public boolean isIsrunning()
	{
		return isrunning;
	}
	
	public void setIsrunning(boolean isrunning)
	{
		this.isrunning = isrunning;
	}
	
	public GetFilesList()
	{
		
	}
	
	public GetFilesList(String path)
	{
		this.path = path;
		if(isrunning)
			getFilesName(path);
	}
	
	public void getFilesName(String filePath)
	{
		File dir = new File(filePath);
		File [] files = dir.listFiles();
		for(File file : files)
		{
			if(isrunning)
			{
				if(file.isDirectory())
				{
					getFilesName(file.toString());
				}
				else if(judge(file))
				{
					System.out.println("\n\t\t扫描中，，，");
					new Log("passFileList.log" , file.toString() + "\r\n" ,
							true);
				}
			}
		}
	}
	
	public boolean judge(File file)
	{
		if(file.isFile() && ( /*
							 * file.getName().toLowerCase().endsWith("java") ||
							 */
		file.getName().toLowerCase().endsWith("wav") )
				&& ( System.currentTimeMillis() - file.lastModified() ) < MAX)
		{
			String name = "wgc";
			ReadIniFile readIniFile = new ReadIniFile(name);
			String logSavePath = readIniFile.getValue("LogFiles" ,
					"logSavePath");
			String passedFileList = logSavePath + "\\passedFileList.log";
			String passFileList = logSavePath + "\\passFileList.log";
			if( ! isUpLoad(file , passedFileList)
					&& ! isUpLoad(file , passFileList))
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
					new Print(str);
					System.out.println(str);
					System.exit(0);
				}
				long afterTime = file.lastModified();
				if(afterTime - beforeTime == 0)
					return true;
			}
		}
		
		return false;
		
	}
	
	public boolean isUpLoad(File file , String path)
	{
		String filepath = file.toString();
		File paths = new File(path);
		if( ! paths.getParentFile().exists() || ! paths.exists())
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
				new Print(str);
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
			new Print(str);
			System.out.println(str);
		}
		
		String temp = null;
		try
		{
			while( ( temp = bufferedReader.readLine() ) != null)
			{
				if(filepath.equals(temp))
				{
					return true;
				}
			}
		}
		catch(IOException e)
		{
			String str = "\n GetFilesList类：检查已传文件内是否存在当前文件名。IO异常！！！\n";
			str += e.getMessage();
			new Print(str);
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
				new Print(str);
				System.out.println(str);
			}
		}
		
		return false;
	}
	
}

class DeleteTheFirstLine
{
	String filepath = "";
	
	public DeleteTheFirstLine(String filepath)
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
			new Print(str);
			System.out.println(str);
		}
		
		StringBuffer stringBuffer = new StringBuffer(4096);
		String temp = null;
		int line = 0;
		BufferedWriter bufferedWriter = null;
		try
		{
			while( ( temp = bufferedReader.readLine() ) != null)
			{
				line ++ ;
				if(line == lineDel)
					continue;
				stringBuffer.append(temp).append("\r\n");
			}
			bufferedWriter = new BufferedWriter(new FileWriter(path));
			bufferedWriter.write(stringBuffer.toString());
		}
		catch(IOException e)
		{
			String str = "\n DeleteTheFirstLine类：待传文件清单IO异常！！！\n";
			str += e.getMessage();
			new Print(str);
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
				new Print(str);
				System.out.println(str);
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
		Calendar calendar = Calendar.getInstance();
		str = ( new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS") )
				.format(calendar.getTime()) + "\r\n" + str + "\r\n";
		String path = "debug.log";
		new Log(path , str , true);
		System.out.println(str);
	}
	
}
