package wgc.lanchang;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class ReadFileByLines
{
	public static void main(String[] args)throws Exception
	{
		@SuppressWarnings("resource")
		Scanner cin = new Scanner (System.in);
		
		//������Ŀ¼����
        System.out.println("\t������������ļ�������Ŀ¼:");
    	String srcDir = cin.next();
        
        //�洢Ŀ¼����
//    	System.out.println("\t�������ļ������洢������Ŀ¼:");
//		String destDir = cin.next();

		for(File item : new File(srcDir).listFiles())
	    {
			try
			{
				if(item.isFile())
					readFileByLines(item.toString() );//, destDir);
			}
			catch (Exception e)
			{
				throw new Exception("�ļ���д�쳣��");
			}
	    }
	}
	
	@SuppressWarnings("resource")
	//public static void readFileByLines(String fileName1 , String fileName2)throws Exception
	public static void readFileByLines(String fileName1)throws Exception

	{
        File file1 = new File(fileName1);
//        @SuppressWarnings("unused")
//		File file2 = new File(fileName2);
        //�ж�Ŀ¼�Ϸ�
        if(!file1.exists())
        {
            throw new Exception("������Ŀ¼�����ڻ��߸�ʽ���벻�Ϸ�!");
        }
        
        BufferedReader reader = null;
       // BufferedWriter writer = null;
        try
        {
            System.out.println("����Ϊ��λ��ȡ�ļ����ݣ�һ�ζ�һ���У�");
            reader = new BufferedReader(new FileReader(file1));
            FileWriter fileWriter = new FileWriter(file1.getName() , true);
            BufferedWriter bufferWriter = new BufferedWriter(fileWriter);
            
            String tempString = null;
            int line = 1;
            while ((tempString = reader.readLine()) != null)
            {
               System.out.println(line + ": " + tempString);
               bufferWriter.write(tempString);
//               bufferWriter.write(tempString, 0, 1);
               line ++;
            }
        }
        catch (IOException e)
        {
        }
        finally
        {
            
            try
            {
				reader.close();
			}
            catch (IOException e)
            {
            	throw new Exception("�ļ��ر��쳣��");
			}
            
        }
        
    }
	
}

