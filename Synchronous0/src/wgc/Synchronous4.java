//package wgc;
//
//public class Synchronous4 extends Thread
//{
//    private static Logger logger = Logger.getLogger(Synchronous4.class);
//
//    public void run()
//    {
//        logger.info("文件监听器已启动");
//        PropertiesUtil config=FileUtil.PropertiesUtil.getInstance();
//        while (true)
//        {
//            int mask = JNotify.FILE_CREATED | JNotify.FILE_DELETED
//                       | JNotify.FILE_MODIFIED | JNotify.FILE_RENAMED;
//            //参数FILE_CREATED 创建 FILE_DELETED 删除 FILE_RENAMED 重命名 FILE_MODIFIED文件变动
//            try
//            {
//                int watchID=JNotify.addWatch(config.getListenerPath(), mask, true,
//                                             new ActivityFileListener());//执行监听
//                long sleep=config.getSyncTime();
//                sleep(sleep);//默认每分钟监听一次
//                JNotify.removeWatch(watchID);
//            }
//            catch (JNotifyException e)
//            {
//                logger.error(e);
//            }
//            catch (InterruptedException e)
//            {
//                logger.error(e);
//            }
//        }
//    }
//
//    public static void main(String[] args)
//    {
//        new Synchronous4().start();
//    }
//}
///**
//   *
//   * @Description : 新建文件
//   * @param rootPath
//   * @param name
//   *            void Created by [Aug 30, 2013] [2:14:38 PM]
//   */
//public static void createFile(String rootPath, String name)
//{
//    renamedFile(rootPath,name,name);
//}
//
///**
// *
// * @Description : 删除文件
// * @param rootPath
// * @param name
// *            void Created by [Aug 30, 2013] [2:14:54 PM]
// */
//public static void deleteFile(String rootPath, String name)
//{
//    File tempFile = getFile(rootPath, name);
//    boolean isprocess = isBeingUsed(tempFile);
//    if (isprocess)  // 文件被占用
//    {
//        deleteFile(rootPath, name);
//    }
//    else
//    {
//        try
//        {
//            Runtime.getRuntime().exec("rd/s/q "+tempFile.getPath());//这样删除文件避免无法删除非空目录
//        }
//        catch (IOException e)
//        {
//            logger.error(e);
//        }
//    }
//}
//
///**
// *
// * @Description : 文件重命名
// * @param rootPath
// * @param oldName
// * @param newName
// *            void Created by [Aug 30, 2013] [2:14:57 PM]
// */
//public static void renamedFile(String rootPath, String oldName,
//                               String newName)
//{
//    try
//    {
//        String tempname = PropertiesUtil.getInstance().getListenerPath()
//                          + File.separator + newName;
//        File tempFile = getFile(rootPath, oldName);
//        boolean isprocess = isBeingUsed(tempFile);
//        if (isprocess)
//        {
//            renamedFile(rootPath, oldName, newName);
//        }
//        else
//        {
//            tempFile.renameTo(new File(tempname));
//        }
//    }
//    catch (Exception e)
//    {
//        logger.error(e);
//    }
//}
//
///**
// *
// * @Description : 文件改动
// * @param rootPath
// * @param name
// *            void Created by [Aug 30, 2013] [2:15:02 PM]
// */
//public static void modifiedFile(String rootPath, String name)
//{
//    deleteFile(rootPath, name);
//    createFile(rootPath, name);
//}
//
///**
// *
// * @Description : 文件是否被占用
// * @param file
// * @return boolean Created by [Aug 30, 2013] [2:23:17 PM]
// */
//public static boolean isBeingUsed(File file)
//{
//    return (file.exists() && !file.renameTo(file));
//}
//
///**
// *
// * @Description : 获取同步需要同步文件的路径
// * @param rootPath
// * @param name
// * @return String Created by [Aug 30, 2013] [2:37:27 PM]
// */
//private static File getFile(String rootPath, String name)
//{
//    String path = "";
//    String oldPath = rootPath + File.separator + name;
//    try
//    {
//        String syncPath = PropertiesUtil.getInstance().getSyncPath();
//        oldPath = oldPath.replace(syncPath, "");
//        String listenerPath = PropertiesUtil.getInstance()
//                              .getListenerPath();
//        path = listenerPath + oldPath;
//    }
//    catch (Exception e)
//    {
//        logger.error(e);
//    }
//    return new File(path);
//}
//
//public static class PropertiesUtil
//{
//    private static PropertiesUtil propertiesUtil = null;
//    private static final String SYNC_TIME = "syncTime";
//    private static final String SYNC_PATH = "syncPath";
//    private static final String LISTENER_PATH = "oldPath";
//    private static final long DEFAULT_TIME = 60000;
//    private PropertiesUtil() {}
//    public static  PropertiesUtil getInstance()
//    {
//        if (propertiesUtil == null)
//            propertiesUtil = new PropertiesUtil();
//        return propertiesUtil;
//    }
//
//    /**
//     *
//     * @Description : 加载配置文件
//     * @return Properties Created by [Aug 30, 2013] [1:52:41 PM]
//     * @throws InterruptedException
//     */
//    private Properties getSyncConfig() throws InterruptedException
//    {
//        Properties properties = new Properties();
//        try {
//            properties.load(FileUtil.class
//            .getResourceAsStream("/synchronous.properties"));
//        }
//        catch (IOException e)
//        {
//            logger.error("synchronous.properties文件不存在！");
//            throw new InterruptedException();//停止监听
//        }
//        return properties;
//    }
//
//    /**
//     *
//     * @Description : 同步相隔时间
//     * @return long Created by [Aug 30, 2013] [1:52:56 PM]
//     * @throws InterruptedException
//     * @throws NumberFormatException
//     */
//    public long getSyncTime() throws NumberFormatException, InterruptedException
//    {
//        return Long.valueOf(getSyncConfig().getProperty(SYNC_TIME,
//        "" + DEFAULT_TIME));
//    }
//
//    /**
//     *
//     * @Description : 文件同步路径
//     * @return
//     * @throws Exception
//     *             String Created by [Aug 30, 2013] [2:07:51 PM]
//     */
//    public String getSyncPath() throws InterruptedException
//    {
//        String path = getSyncConfig().getProperty(SYNC_PATH, "");
//        if (path.equals(""))
//            throw new InterruptedException("请设置同步路径！");//停止监听
//        return path;
//    }
//
//    /**
//     *
//     * @Description : 文件监听路径
//     * @return
//     * @throws Exception
//     *             String Created by [Aug 30, 2013] [2:07:38 PM]
//     */
//    public String getListenerPath() throws InterruptedException
//    {
//        String path = getSyncConfig().getProperty(LISTENER_PATH);
//        if (path.equals(""))
//            throw new InterruptedException("请设置监听路径！");//停止监听
//        return path;
//    }
//}
////
////再次告诉大家一个秘密 java无法删除一个目录或者说非空目录 以前看很多人都去写一些复杂的代码
////迭代文件夹然后一个一个去删除文件 这一样很麻烦 现在告诉大家一个高效的办法
////可以直接使用runtime执行cmd命令中的 rd/s/q 文件目录 或文件夹 就可以删除此文件或文件夹
////标签： <无>
