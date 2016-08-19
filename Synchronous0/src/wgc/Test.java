/**
 *
 * @author		Administrator
 * @author		wgc
 * @version		1.0
 * @user		Files Synchronous
 *
 */
package wgc;

import java.io.File;
import java.io.FilenameFilter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Test
{

    @SuppressWarnings("deprecation")
    public static void main(String[] args)
    {
        // 云盘同步
        System.out.println("**************1******************");
        Date date = new Date();
        System.out.println(date.toGMTString());
        System.out.println(date.toLocaleString());
        System.out.println(date.getYear() + 1900);
        System.out.println(date.getMonth() + 1);
        System.out.println(date.getDate());

        System.out.println("**************2******************");

        Calendar calendar = Calendar.getInstance();
        int currenYear = calendar.get(Calendar.YEAR);
        int currenMonth = calendar.get(Calendar.MONTH) + 1;
        int currenDay = calendar.get(Calendar.DATE);

        // calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.YEAR, currenYear);
        java.sql.Date newDate = new java.sql.Date(calendar.getTimeInMillis());
        System.out.println(newDate.toString());
        System.out.println("年：" + currenYear + "月：" + currenMonth + "日：" + currenDay);
        System.out.println(calendar.getWeekYear());
        String str = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS")).format(calendar.getTime());
        System.out.println(str);

        System.out.println("***************3*****************");

        String filepath1 = "E:\\MyFiles\\java\\projects\\SynchronousFinal\\SynchronousTestIn";
        System.out.println(System.currentTimeMillis() - new File(filepath1).lastModified());

        System.out.println(readFiles(filepath1));
        // System.out.println("该目录下对象个数："+tempList.length);
        // for(int i = 0; i < tempList.length; i ++)
        // {
        // if(tempList[i].isFile())
        // {
        // System.out.println("文     件：" + tempList[i]);
        // }
        // else if(tempList[i].isDirectory())
        // {
        // System.out.println("文件夹：" + tempList[i]);
        // }
        // }

        System.out.println("***************4*****************");

        String filepath2 = "E:\\MyFiles\\java\\projects\\SynchronousFinal\\SynchronousTestIn";
        startTest(filepath2);

        System.out.println("***************5*****************");
        
        // File filePath = new File(filepath);
        File[] files1 = File.listRoots();
        for (File file : files1)
        {
            System.out.println(file);
            if (file.length() > 0)
            {
                String[] filenames = file.list();
                for (String filename : filenames)
                {
                    System.out.println(filename);
                }
            }
        }

        System.out.println("***************6*****************");
        
        File[] files2 =File.listRoots();
        for(File file:files2)
        {
            System.out.println(file);
            if(file.length() > 0)
            {
                String[] filenames =file.list(new FilenameFilter()
                {
                    //file 过滤目录 name 文件名
                    public boolean accept(File file,String filename)
                    {
                        return filename.endsWith(".txt");
                    }
                });
                for(String filename:filenames)
                {
                    System.out.println(filename);
                }
            }
        }
        
        System.out.println("***************7*****************");
        
        String filepath3 = "E:\\MyFiles\\java\\projects\\SynchronousFinal\\SynchronousTestIn\\log.log";
//        showDir(new File(filepath3));
        File filePath3 = new File(filepath3);
        System.out.println(filePath3.toPath().toString());
        System.out.println(filepath3.contains("MyFles"));
        
        System.out.println("***************8*****************");
        
        
        
        System.out.println("***************9*****************");
        
        
        
        System.out.println("***************10*****************");
        
        
        
        System.out.println("***************11*****************");
        
        
        
        System.out.println("***************12*****************");
        
        
    }

    public static void showDir(File dir)
    {
//        System.out.println(dir);
        File[] files = dir.listFiles();
        for(File file:files)
        {
            if(file.isDirectory())
                showDir(file);
            else if(file.isFile() && file.getName().endsWith(".java"))
                System.out.println(file);
        }
    }
    
    public static String readFiles(String srcPath)
    {

        File srcpath = new File(srcPath);
        File[] tempList = srcpath.listFiles();

        for (int i = 0; i < tempList.length;)
            if (tempList[i].isDirectory())
            {
                return readFiles(tempList[i++].toString());
            }
            else
                return tempList[i++].toString();
        return srcPath;
    }

    private static void startTest(String testFileDir)
    {
        File[] testFile = new File(testFileDir).listFiles();
        for (int i = 0; i < testFile.length; i++)
        {
            if (testFile[i].isFile())
            {
                System.out.println(testFile[i].getPath());
            }
            else if (testFile[i].isDirectory())
            {
                startTest(testFile[i].getPath());
            }
            else
            {
                System.out.println("文件读入有误！");
            }
        }
    }
}
