package pat;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class HandlingHttpRequestServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException
    {
        super.doGet(req, resp);
    }
    @SuppressWarnings("deprecation")
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException
    {
        DiskFileItemFactory factory = new DiskFileItemFactory();
        String path = req.getRealPath("/upload");
        String repositoryPath =req.getRealPath("/upload/temp");
        factory.setRepository(new File(repositoryPath));
        factory.setSizeThreshold(1024* 1024);
        ServletFileUpload uploader =new ServletFileUpload(factory);
        ArrayList<FileItem> list = null;
		try
		{
			list = (ArrayList <FileItem> )uploader.parseRequest(req);
		}
		catch(FileUploadException e)
		{
			System.out.println(e.getMessage());
		}
        System.out.println(list.size());
        for(FileItem fileItem : list)
        {
            if(fileItem.isFormField())
            {
                String name = fileItem.getFieldName();
                String value = fileItem.getString();
                System.out.println(name+ " = " + value);
            }
            else
            {
                String value = fileItem.getName();
                int start = value.lastIndexOf("\\");
                String fileName = value.substring(start + 1);
                try
                {
					fileItem.write(new File(path, fileName));
				}
                catch(Exception e)
                {
                	System.out.println(e.getMessage());
				}
            }
        }
       
        PrintWriter out = resp.getWriter();
        try
        {
        	out.print("OK");
        	
        }
        catch(Exception e)
        {
        	System.out.println(e.getMessage());
        }
        finally
        {
        	try
        	{
	        	out.flush();
	        	out.close();
	        	super.doPost(req, resp);
        	}
        	catch(Exception e)
        	{
        		System.out.println(e.getMessage());
        	}
        }
    }
}
