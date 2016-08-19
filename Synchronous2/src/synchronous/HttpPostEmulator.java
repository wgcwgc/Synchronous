/**
 * 
 */
package synchronous;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;

/**
 * @author           Administrator
 * @copyright        wgcwgc
 * @date             2016年4月28日
 * @time             下午2:29:00
 * @project_name     Synchronous
 * @package_name     synchronous
 * @file_name        HttpPostEmulator.java
 * @type_name        HttpPostEmulator
 * @enclosing_type   
 * @tags             
 * @todo             
 * @others           
 *
 */

public class HttpPostEmulator
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
//			System.out.println(connection);
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
