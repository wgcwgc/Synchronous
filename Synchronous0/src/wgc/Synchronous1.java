//package wgc;
//
//import java.io.*;
//import java.util.*;
//import java.util.regex.*;
//
//import javax.*;
//import javax.ejb.EJBException;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import java.io.EOFException;
//
//public class Synchronous1 extends HttpServlet
//{
//	
//    private static final String CONTENT_TYPE = "text/html; charset=GB2312";
//    public void doPost(HttpServletRequest request, HttpServletResponse response)throws Exception
//    {
//        response.setContentType(CONTENT_TYPE);
//        PrintWriter out=response.getWriter();
//        try
//        {
//            DiskFileUpload fu = new DiskFileUpload();
//// ���������û��ϴ��ļ���С,��λ:�ֽڣ�������Ϊ2m
//            fu.setSizeMax(2*1024*1024);
//// �������ֻ�������ڴ��д洢������,��λ:�ֽ�
//            fu.setSizeThreshold(4096);
//// ����һ���ļ���С����getSizeThreshold()��ֵʱ���ݴ����Ӳ�̵�Ŀ¼
//            fu.setRepositoryPath("c://windows//temp");
////��ʼ��ȡ�ϴ���Ϣ
//            List fileItems = fu.parseRequest(request);
//// ���δ���ÿ���ϴ����ļ�
//            @SuppressWarnings("rawtypes")
//			Iterator iter = fileItems.iterator();
//
////����ƥ�䣬����·��ȡ�ļ���
//            String regExp=".+////(.+)$";
//
////���˵����ļ�����
//            String[] errorType={".exe",".com",".cgi",".asp"};
//            Pattern p = Pattern.compile(regExp);
//            while (iter.hasNext())
//            {
//                FileItem item = (FileItem)iter.next();
//                //�������������ļ�������б���Ϣ
//                if (!item.isFormField())
//                {
//                    String name = item.getName();
//                    long size = item.getSize();
//                    if((name==null||name.equals("")) && size==0)
//                        continue;
//                    Matcher m = p.matcher(name);
//                    boolean result = m.find();
//                    if (result)
//                    {
//                        for (int temp=0; temp<ERRORTYPE.LENGTH; TEMP++)
//                        {
//                            if (m.group(1).endsWith(errorType[temp]))
//                            {
//                                throw new IOException(name+": wrong type");
//                            }
//                        }
//                        try
//                        {
//
//                            //�����ϴ����ļ���ָ����Ŀ¼
//
//                            //���������ϴ��ļ������ݿ�ʱ�����������д
//                            item.write(new File("d://" + m.group(1)));
//
//                            out.print(name+"  "+size+"");
//                        }
//                        catch(Exception e)
//                        {
//                            out.println(e);
//                        }
//
//                    }
//                    else
//                    {
//                        throw new Exception("fail to upload");
//                    }
//                }
//            }
//        }
//        catch (Exception e)
//        {
//            out.println(e);
//        }
//
//    }
//    public static void main(String[] args)throws EJBException
//	{
//		System.out.println();
//	}
//}
////
////���ڽ����ϴ��ļ���������������ֻд����ش��룺
////
////��sql2000Ϊ������ṹ���£�
////
////�ֶ�����name    filecode
////
////���ͣ� varchar     image
////
////���ݿ�������Ϊ��PreparedStatement pstmt=conn.prepareStatement("insert into test values(?,?)");
////
////�������£�
////
////������������
////
////try
////{
//////        ��δ��������ȥ������һͬд�뵽��������
////    item.write(new File("d://" + m.group(1)));
////
////    int byteread=0;
////    //��ȡ��������Ҳ�����ϴ����ļ�����
////    InputStream inStream=item.getInputStream();
////    pstmt.setString(1,m.group(1));
////    pstmt.setBinaryStream(2,inStream,(int)size);
////    pstmt.executeUpdate();
////    inStream.close();
////
////    out.println(name+"  "+size+" ");
////}
////
////������������
////
////������ʵ�����ϴ��ļ������ݿ�