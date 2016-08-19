/**
 * 
 */
package synchronous;

import java.io.Serializable;

/**
 * @author           Administrator
 * @copyright        wgcwgc
 * @date             2016年4月28日
 * @time             下午2:26:41
 * @project_name     Synchronous
 * @package_name     synchronous
 * @file_name        UploadFileItem.java
 * @type_name        UploadFileItem
 * @enclosing_type   
 * @tags             
 * @todo             
 * @others           
 *
 */

public class UploadFileItem implements Serializable
{
	static final long serialVersionUID = 1L;
	String formFieldName;
	String fileName;
	
	public UploadFileItem(String formFieldName , String fileName)
	{
		this.formFieldName = formFieldName;
		this.fileName = fileName;
	}
	
	public String getFormFieldName()
	{
		return formFieldName;
	}
	
	public void setFormFieldName(String formFieldName)
	{
		this.formFieldName = formFieldName;
	}
	
	public String getFileName()
	{
		return fileName;
	}
	
	public void setFileName(String fileName)
	{
		this.fileName = fileName;
	}
	
}