/**
 * 
 */
package synchronous;

import java.io.Serializable;

/**
 * @author           Administrator
 * @copyright        wgcwgc
 * @date             2016年4月28日
 * @time             下午2:29:25
 * @project_name     Synchronous
 * @package_name     synchronous
 * @file_name        FormFieldKeyValuePair.java
 * @type_name        FormFieldKeyValuePair
 * @enclosing_type   
 * @tags             
 * @todo             
 * @others           
 *
 */

public class FormFieldKeyValuePair implements Serializable
{
	static final long serialVersionUID = 1L;
	String key;
	String value;
	
	public FormFieldKeyValuePair(String key , String value)
	{
		this.key = key;
		this.value = value;
	}
	
	public String getKey()
	{
		return key;
	}
	
	public void setKey(String key)
	{
		this.key = key;
	}
	
	public String getValue()
	{
		return value;
	}
	
	public void setValue(String value)
	{
		this.value = value;
	}
	
}