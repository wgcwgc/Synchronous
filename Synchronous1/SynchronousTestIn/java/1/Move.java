package wgc.lanchang;

import java.io.*;
import java.util.*;

public class Move
{
    public static void main(String argc[])throws Exception
    {
    	//�洢Ŀ¼����
    	System.out.println("\t�������ļ������洢������Ŀ¼,�ǵú���ӷ�б��'\\':");
    	@SuppressWarnings("resource")
		Scanner cin = new Scanner (System.in);
        String destDir = cin.next();
        //������Ŀ¼����
        System.out.println("\t������������ļ�������Ŀ¼:");
    	String srcDir = cin.next();
        File fileSrc = new File(srcDir);
        //�ж�Ŀ¼�Ϸ�
        if(!fileSrc.exists())
        {
            throw new Exception("������Ŀ¼�����ڻ��߸�ʽ���벻�Ϸ�!");
        }
        for(File item : fileSrc.listFiles())
        {
            if(item.isDirectory())
            {
            	//*********************************************���Ĵ��������************
                continue;
            }
            handleFile(item, destDir);
        }
        //��ʱ3�룬���ڹ۲�������
		try
		{
		    Thread.sleep(3000);
		}
		catch (InterruptedException e)
		{
		    e.printStackTrace();
		}
		//�Ѻý�����ʾ
        System.out.println("\t\t��ϲ!�����!!!");
    }
    //�ļ�����
    static void handleFile(File src, String dest) throws Exception
    {
    	//�����ļ��������������
        FileInputStream fin = new FileInputStream(src);
        FileOutputStream fou = new FileOutputStream(src.getName());
        byte [] data = new byte[2048];
        @SuppressWarnings("unused")
		int r;
        while((r = fin.read(data)) > 0)
        {
            fou.write(data);
        }
        //�ر��ļ���
        fin.close();
        fou.close();
        //��ʱ3�룬���ڹ۲�������
        try
		{
		    Thread.sleep(3000);
		}
		catch (InterruptedException e)
		{
		    e.printStackTrace();
		}
        System.out.println("\t\t���ݴ�����,��ȴ�... ...\n");
        
    }
    
}

