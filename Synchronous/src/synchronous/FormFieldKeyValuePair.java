
package synchronous;

import java.io.Serializable;

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

