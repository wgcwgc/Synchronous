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
        // �趨�����ַ
        String serverUrl ="http://127.0.0.1:8080/test/upload";
        // �趨Ҫ�ϴ�����ͨForm Field�����Ӧ��value
        // ��FormFieldKeyValuePair�Ķ��������Ĵ���
        ArrayList<FormFieldKeyValuePair> ffkvp = new ArrayList<FormFieldKeyValuePair>();
        ffkvp.add(new FormFieldKeyValuePair("username", "Patrick"));
        ffkvp.add(new FormFieldKeyValuePair("password", "HELLOPATRICK"));
        ffkvp.add(new FormFieldKeyValuePair("hobby", "Computer programming"));
        // �趨Ҫ�ϴ����ļ���UploadFileItem������Ĵ���
        ArrayList<UploadFileItem> ufi = new ArrayList<UploadFileItem>();
        ufi.add(new UploadFileItem("upload1", "E:\\MyFiles\\java\\projects\\SynchronousFinal\\SynchronousTestIn\\specialTest.java"));
        ufi.add(new UploadFileItem("upload2", "E:\\MyFiles\\java\\projects\\SynchronousFinal\\SynchronousTestIn\\105304_#16_057188480536__T.wav"));
        ufi.add(new UploadFileItem("upload3", "E:\\MyFiles\\java\\projects\\SynchronousFinal\\SynchronousTestIn\\specialTes.java"));
        // ��HttpPostEmulator�Ķ��壬������Ĵ���
        HttpPostEmulator hpe = new HttpPostEmulator();
        String response =hpe.sendHttpPostRequest(serverUrl, ffkvp, ufi);
        System.out.println("Responsefrom server is: " + response);
    }
}

class HttpPostEmulator
{
    //ÿ��post����֮��ķָ��������趨��ֻҪ������������ַ����ظ����ɡ�
    private static final String BOUNDARY ="----------HV2ymHFg03ehbqgZCaKO6jyH";
    public String sendHttpPostRequest(String serverUrl,ArrayList<FormFieldKeyValuePair>generalFormFields,
    		ArrayList<UploadFileItem>filesToBeUploaded) throws Exception
    {
        // �����������post����
        URL url = new URL(serverUrl/*"http://127.0.0.1:8080/test/upload"*/);
        HttpURLConnection connection= (HttpURLConnection) url.openConnection();
        // ����POST�������������������
        connection.setDoOutput(true);
        connection.setDoInput(true);
        connection.setUseCaches(false);
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Connection","Keep-Alive");
        connection.setRequestProperty("Charset","UTF-8");
        connection.setRequestProperty("Content-Type","multipart/form-data; boundary=" + BOUNDARY);
        // ͷ
        String boundary = BOUNDARY;
        // ��������
        StringBuffer contentBody =new StringBuffer("--" + BOUNDARY);
        // β
        String endBoundary ="\r\n--" + boundary + "--\r\n";
        OutputStream out =connection.getOutputStream();
        // 1. ������ͨ����(������key = value��)��POST����
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
        // 2. �����ļ��ϴ�
        for(UploadFileItem ufi :filesToBeUploaded)
        {
            contentBody = new StringBuffer();
            contentBody.append("\r\n")
            .append("Content-Disposition:form-data; name=\"")
            .append(ufi.getFormFieldName() +"\"; ")   // form��field������
            .append("filename=\"")
            .append(ufi.getFileName() +"\"")   //�ϴ��ļ����ļ���������Ŀ¼
            .append("\r\n")
            .append("Content-Type:application/octet-stream")
            .append("\r\n\r\n");
            String boundaryMessage2 = contentBody.toString();
            out.write(boundaryMessage2.getBytes("utf-8"));
            // ��ʼ�����������д�ļ�
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
        // 3. д��β
        out.write(endBoundary.getBytes("utf-8"));
        out.flush();
        out.close();
        // 4. �ӷ�������ûش������
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
// һ��POJO�����ڱ����ϴ��ļ��������Ϣ
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


