package wgc.lanchang;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;


/**
 * �ļ�����
 * ������
 */
public class Test {
	
	//��������ļ�����·��
	static String fromFile = "C:\\1.txt";
	//��ȡ�������ļ��Ķ�ȡ����
	static String fromFileCharset = "UTF-8";
	//������ļ��ı����ʽ
	static String toFileCharset = "UTF-8";
	//�������ļ���·�����������ļ����ƣ�
	static String fromFilePathWithOutFile = fromFile.substring(0,fromFile.lastIndexOf("\\")+1);
	//�������ļ����ļ����ƣ�������·����
	static String fromFileWithOutFilePath = fromFile.substring(fromFile.lastIndexOf("\\")+1,fromFile.length());
	
	
	public static void main(String args[]){
		Test test = new Test();
		test.excute(fromFile);
		System.out.println(fromFileWithOutFilePath);
	}

	public void excute(String filePath){
		File file = new File(filePath);
		if(file.exists()){
			System.out.println("---�ļ��������ڴ���---");
			String tempString = null; 
			String writeString = null;
			InputStreamReader isReader = null;
			FileInputStream fiStream = null;
			BufferedReader bReader = null;
			try {
				
				fiStream = new FileInputStream(filePath);
				isReader = new InputStreamReader(fiStream,fromFileCharset);
				bReader = new BufferedReader(isReader);
				
				String toFilePathWithFileName = fromFilePathWithOutFile
					+ "New_"
					+ fromFileWithOutFilePath;
					
				File wFile = new File(toFilePathWithFileName);
				wFile.createNewFile();
				OutputStreamWriter os = null;
				FileOutputStream fos = null;
				fos = new FileOutputStream(toFilePathWithFileName);
				os = new OutputStreamWriter(fos,toFileCharset);
				while((tempString = bReader.readLine()) != null){
					writeString = tempString;
					//����ʽ
					writeString = this.delKongHang(writeString);
					writeString = this.delHangHouZhuShi(writeString);
					writeString = this.replaceShuangyinhaoToDanyinhao(writeString);
					writeString = this.replaceDanyinhaoToShuangyinhao(writeString);
					
					if(!writeString.equals("")){
						os.write(writeString);
						os.write("\n");
					}
					
				}
				if (os != null) {
					try {
						os.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					os = null;
				}
				if (fos != null) {
					try {
						fos.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					fos = null;
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally{
				
			}
			
		}else{
			System.out.println("---�ļ������ڣ�");
		}
	}
	
	/**
	 * ȥ������
	 */
	public String delKongHang(String str){
		if(str.trim()!= null && !str.trim().equals("")){
			return str;
		}else{
			return "";
		}
	}
	
	/**
	 * ȥ���к�ע��
	 * //��/*��<!--
	 */
	public String delHangHouZhuShi(String str){
		if(str.contains("//")){
			return str.substring(0,str.indexOf("//"));
		}else if(str.contains("/*"))
		{
			return str.substring(0,str.indexOf("/*"));
		}else if(str.contains("<!--"))
		{
			return str.substring(0,str.indexOf("<!--"));
		}
		else{
			return str;
		}
	}
	
	/**
	 * ˫���Ż�������
	 */
	public String replaceShuangyinhaoToDanyinhao(String str){
		return str.replaceAll("\"", "\'");
	}
	
	/**
	 * �����Ż�˫����
	 */
	public String replaceDanyinhaoToShuangyinhao(String str){
		return str.replaceAll("\'", "\"");
	}
}
