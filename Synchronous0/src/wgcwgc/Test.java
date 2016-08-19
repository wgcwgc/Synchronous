package wgcwgc;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Test
{

	public static void main(String[] args)
	{
		System.out.println("*******************************0*******************************");
		
		
		
		System.out.println("*******************************1*******************************");
		
		String filepath1 = "E:\\MyFiles\\java\\projects\\SynchronousFinal\\SynchronousTestIn\\specialTest.java";
		File file = new File(filepath1);
		long current = System.currentTimeMillis();
		long last = file.lastModified(); 
		long time = System.currentTimeMillis() - file.lastModified();
		System.out.println("current:" + current + "\nlast:" + last + "\ntime:" + time);
		
		final long MAX = 10 * 24 * 3600 * 1000;
		System.out.println("MAX:" + MAX);
		if(time < MAX)
		{
			System.out.println("ok");
		}
		
		
		System.out.println("*******************************2*******************************");
		
//		String filepath2 = "E:\\MyFiles\\java\\projects\\SynchronousFinal\\SynchronousTestIn\\123";
//		System.out.println(delFile(filepath2));
		
		System.out.println("*******************************3*******************************");
		
		String filepath3 = "E:\\MyFiles\\java\\projects\\SynchronousFinal\\SynchronousTestIn\\specialTes.java";
//		deleteTheFirstLine(filepath3);
		File filepath33 = new File(filepath3);
		System.out.println(filepath33.length());
		
		System.out.println("*******************************4*******************************");
		
		String filepath4 = "E:\\MyFiles\\java\\projects\\SynchronousFinal\\SynchronousTestIn\\specialTest.java";
		File filepath44 = new File(filepath4);
		long beforeTime = filepath44.lastModified();
		try
		{
			Thread.sleep(10000);
		}
		catch(InterruptedException e)
		{
			e.printStackTrace();
		}
		long afterTime = filepath44.lastModified();
		if(afterTime - beforeTime == 0)
			System.out.println("可以上传！！！");
		
		System.out.println("*******************************5*******************************");
		
		
		
		System.out.println("*******************************6*******************************");
		
		
		
		System.out.println("*******************************7*******************************");
		
		
		
		System.out.println("*******************************8*******************************");
		
		
		
		System.out.println("*******************************9*******************************");
		
		
		
		System.out.println("*******************************10*******************************");
		
		
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
			e1.printStackTrace();
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
			e.printStackTrace();
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
				e.printStackTrace();
			}
		}
	}
	
	public static boolean delFile(String strPath)
	{
		boolean filebool = false;
		File file = new File(strPath);
		if(file.exists() && file.isDirectory())
		{
			if(file.listFiles().length == 0)
			{
				file.delete();
			}
			else
			{
				File[] ff = file.listFiles();
				for(int i = 0; i < ff.length; i ++)
				{
					if (ff[i].isDirectory())
						delFile(strPath);
					ff[i].delete();
				}
			}
		}
		file.delete();// 如果要把文件夹也删除。。就去掉注释。。
		filebool = true;
		return filebool;
	}
	
}


