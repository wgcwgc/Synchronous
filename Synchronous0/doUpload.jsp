
<%@ page contentType="text/html;charset=gb2312"%>
<%@ page import="org.apache.commons.fileupload.servlet.ServletFileUpload"%>
<%@ page import="org.apache.commons.fileupload.disk.DiskFileItemFactory"%>
<%@ page import="org.apache.commons.fileupload.FileItem"%>
<%@ page import="javax.servlet.*"%>
<%@ page import="java.io.File"%>
<%@ page import="java.io.IOException"%>
<%@ page import="java.io.PrintWriter"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="javax.servlet.ServletException"%>
<%@ page import="javax.servlet.http.HttpServlet"%>
<%@ page import="javax.servlet.http.HttpServletRequest"%>
<%@ page import="javax.servlet.http.HttpServletResponse"%>
<html>
	<head>
		<title>
			doUpload.jsp
		</title>
	</head>
	
	<body>
	<%
        DiskFileItemFactory factory = new DiskFileItemFactory();
        String path = request.getRealPath("/upload");
        String repositoryPath = request.getRealPath("/upload/temp");
        factory.setRepository(new File(repositoryPath));
        factory.setSizeThreshold(1024 * 1024);
        ServletFileUpload uploader = new ServletFileUpload(factory);
        ArrayList <FileItem> list = null;
		try
		{
			list = (ArrayList <FileItem> ) uploader.parseRequest(request);
		}
		catch(Exception e)
		{
			out.println(e.getMessage());
		}
        String paths = "";
        for(FileItem fileItem : list)
        {
        	if(fileItem.getName() == "")
        	{
        		out.println("请重新选择文件！！！");
        		return ;
        	}
            if(fileItem.isFormField())
            {
                String name = fileItem.getFieldName();
                String value = fileItem.getString();
                paths = value.substring(name.length() , value.lastIndexOf("\\"));
                //out.println("name :" + name + "\n" + "value :" + value + "\npaths :" + paths);
            }
            else if(fileItem.isInMemory())
            {
                String value = fileItem.getName();
                int start = value.lastIndexOf("\\");
                String fileName = value.substring(start + 1);
                try
                {
                	path = path.concat(paths);
                	File finalpath = new File(path.concat("/".concat(fileName)));
                	//out.println(finalPath);
                	if(!finalpath.getParentFile().exists())
                	{
                		finalpath.getParentFile().mkdirs();
                	}
					fileItem.write(new File(path , fileName));
			        out.println("\n\t\tOK!!!\n" + fileItem.getName() + "\t\t传输完成!!!");
				}
                catch(Exception e)
                {
                	out.println("e.getMessage():" + e.getMessage());
				}
            }
            else
            {
            	out.println("请重新选择文件！！！");
            	return ;
            }
        }
	%>
	</body>
</html>


