package wgc.lanchang;
import java.awt.List;
import java.io.File;
public class test1
{
	public static void main(String[] args)
	{
			String str = "1234567	 ";
			System.out.println(str.trim());
	}
	public static List getFileList(String strPath)
	{
		File dir = new File(strPath);
		File[] files = dir.listFiles(); // ���ļ�Ŀ¼���ļ�ȫ����������
		List filelist = null;
		if (files != null)
		{
			for (int i = 0; i < files.length; i++)
			{
				String fileName = files[i].getName();
				if (files[i].isDirectory())
				{ // �ж����ļ������ļ���
					getFileList(files[i].getAbsolutePath()); // ��ȡ�ļ�����·��
				}
				else if (fileName.endsWith("avi"))
				{ // �ж��ļ����Ƿ���.avi��β
					String strFileName = files[i].getAbsolutePath();
					System.out.println("---" + strFileName);
//					filelist.add(files[i]);
				}
				else
				{
					continue;
				}
			}
		}
		return filelist;
	}
}
