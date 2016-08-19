package pat;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class PostRequestEmulator
{
    public static void main(String[] args)throws Exception
    {
        // 设定服务地址
        String serverUrl ="http://127.0.0.1:8080/test/upload";
        // 设定要上传的普通Form Field及其对应的value
        // 类FormFieldKeyValuePair的定义见后面的代码
        ArrayList<FormFieldKeyValuePair> ffkvp = new ArrayList<FormFieldKeyValuePair>();
        ffkvp.add(new FormFieldKeyValuePair("username", "Patrick"));
        ffkvp.add(new FormFieldKeyValuePair("password", "HELLOPATRICK"));
        ffkvp.add(new FormFieldKeyValuePair("hobby", "Computer programming"));
        // 设定要上传的文件。UploadFileItem见后面的代码
        ArrayList<UploadFileItem> ufi = new ArrayList<UploadFileItem>();
        ufi.add(new UploadFileItem("upload1", "E:\\MyFiles\\java\\projects\\SynchronousFinal\\SynchronousTestIn\\specialTest.java"));
        ufi.add(new UploadFileItem("upload2", "E:\\MyFiles\\java\\projects\\SynchronousFinal\\SynchronousTestIn\\105304_#16_057188480536__T.wav"));
        ufi.add(new UploadFileItem("upload3", "E:\\MyFiles\\java\\projects\\SynchronousFinal\\SynchronousTestIn\\specialTes.java"));
        // 类HttpPostEmulator的定义，见后面的代码
        HttpPostEmulator hpe = new HttpPostEmulator();
        String response =hpe.sendHttpPostRequest(serverUrl, ffkvp, ufi);
        System.out.println("Responsefrom server is: " + response);
    }
}

class HttpPostEmulator
{
    //每个post参数之间的分隔。随意设定，只要不会和其他的字符串重复即可。
    private static final String BOUNDARY ="----------HV2ymHFg03ehbqgZCaKO6jyH";
    public String sendHttpPostRequest(String serverUrl,ArrayList<FormFieldKeyValuePair>generalFormFields,
    		ArrayList<UploadFileItem>filesToBeUploaded) throws Exception
    {
        // 向服务器发送post请求
        URL url = new URL(serverUrl/*"http://127.0.0.1:8080/test/upload"*/);
        HttpURLConnection connection= (HttpURLConnection) url.openConnection();
        // 发送POST请求必须设置如下两行
        connection.setDoOutput(true);
        connection.setDoInput(true);
        connection.setUseCaches(false);
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Connection","Keep-Alive");
        connection.setRequestProperty("Charset","UTF-8");
        connection.setRequestProperty("Content-Type","multipart/form-data; boundary=" + BOUNDARY);
        // 头
        String boundary = BOUNDARY;
        // 传输内容
        StringBuffer contentBody =new StringBuffer("--" + BOUNDARY);
        // 尾
        String endBoundary ="\r\n--" + boundary + "--\r\n";
        OutputStream out =connection.getOutputStream();
        // 1. 处理普通表单域(即形如key = value对)的POST请求
        for(FormFieldKeyValuePair ffkvp : generalFormFields)
        {
            contentBody.append("\r\n")
            .append("Content-Disposition: form-data; name=\"")
            .append(ffkvp.getKey() + "\"")
            .append("\r\n")
            .append("\r\n")
            .append(ffkvp.getValue())
            .append("\r\n")
            .append("--")
            .append(boundary);
        }
        String boundaryMessage1 =contentBody.toString();
        out.write(boundaryMessage1.getBytes("utf-8"));
        // 2. 处理文件上传
        for(UploadFileItem ufi :filesToBeUploaded)
        {
            contentBody = new StringBuffer();
            contentBody.append("\r\n")
            .append("Content-Disposition:form-data; name=\"")
            .append(ufi.getFormFieldName() +"\"; ")   // form中field的名称
            .append("filename=\"")
            .append(ufi.getFileName() +"\"")   //上传文件的文件名，包括目录
            .append("\r\n")
            .append("Content-Type:application/octet-stream")
            .append("\r\n\r\n");
            String boundaryMessage2 = contentBody.toString();
            out.write(boundaryMessage2.getBytes("utf-8"));
            // 开始真正向服务器写文件
            File file = new File(ufi.getFileName());
            DataInputStream dis= new DataInputStream(new FileInputStream(file));
            int bytes = 0;
            byte[] bufferOut =new byte[(int) file.length()];
            bytes =dis.read(bufferOut);
            out.write(bufferOut,0, bytes);
            dis.close();
            contentBody.append("------------HV2ymHFg03ehbqgZCaKO6jyH");
            String boundaryMessage = contentBody.toString();
            out.write(boundaryMessage.getBytes("utf-8"));
            //System.out.println(boundaryMessage);
        }
        out.write("------------HV2ymHFg03ehbqgZCaKO6jyH--\r\n".getBytes("UTF-8"));
        // 3. 写结尾
        out.write(endBoundary.getBytes("utf-8"));
        out.flush();
        out.close();
        // 4. 从服务器获得回答的内容
        String strLine="";
        String strResponse ="";
        InputStream in =connection.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        while((strLine =reader.readLine()) != null)
        {
            strResponse +=strLine +"\n";
        }
        //System.out.print(strResponse);
        return strResponse;
    }
}
class FormFieldKeyValuePair implements Serializable
{
    private static final long serialVersionUID = 1L;
    // The form field used for receivinguser's input,
    // such as "username" in "<inputtype="text" name="username"/>"
    private String key;
    // The value entered by user in thecorresponding form field,
    // such as "Patrick" the abovementioned formfield "username"
    private String value;
    public FormFieldKeyValuePair(String key, String value)
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
// 一个POJO。用于保存上传文件的相关信息
class UploadFileItem implements Serializable
{
    private static final long serialVersionUID = 1L;
    // The form field name in a form used foruploading a file,
    // such as "upload1" in "<inputtype="file" name="upload1"/>"
    private String formFieldName;
    // File name to be uploaded, thefileName contains path,
    // such as "E:\\some_file.jpg"
    private String fileName;
    public UploadFileItem(String formFieldName, String fileName)
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
        this.formFieldName =formFieldName;
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


