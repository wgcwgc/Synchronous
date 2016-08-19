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
//// 设置允许用户上传文件大小,单位:字节，这里设为2m
//            fu.setSizeMax(2*1024*1024);
//// 设置最多只允许在内存中存储的数据,单位:字节
//            fu.setSizeThreshold(4096);
//// 设置一旦文件大小超过getSizeThreshold()的值时数据存放在硬盘的目录
//            fu.setRepositoryPath("c://windows//temp");
////开始读取上传信息
//            List fileItems = fu.parseRequest(request);
//// 依次处理每个上传的文件
//            @SuppressWarnings("rawtypes")
//			Iterator iter = fileItems.iterator();
//
////正则匹配，过滤路径取文件名
//            String regExp=".+////(.+)$";
//
////过滤掉的文件类型
//            String[] errorType={".exe",".com",".cgi",".asp"};
//            Pattern p = Pattern.compile(regExp);
//            while (iter.hasNext())
//            {
//                FileItem item = (FileItem)iter.next();
//                //忽略其他不是文件域的所有表单信息
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
//                            //保存上传的文件到指定的目录
//
//                            //在下文中上传文件至数据库时，将对这里改写
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
////现在介绍上传文件到服务器，下面只写出相关代码：
////
////以sql2000为例，表结构如下：
////
////字段名：name    filecode
////
////类型： varchar     image
////
////数据库插入代码为：PreparedStatement pstmt=conn.prepareStatement("insert into test values(?,?)");
////
////代码如下：
////
////。。。。。。
////
////try
////{
//////        这段代码如果不去掉，将一同写入到服务器中
////    item.write(new File("d://" + m.group(1)));
////
////    int byteread=0;
////    //读取输入流，也就是上传的文件内容
////    InputStream inStream=item.getInputStream();
////    pstmt.setString(1,m.group(1));
////    pstmt.setBinaryStream(2,inStream,(int)size);
////    pstmt.executeUpdate();
////    inStream.close();
////
////    out.println(name+"  "+size+" ");
////}
////
////。。。。。。
////
////这样就实现了上传文件至数据库