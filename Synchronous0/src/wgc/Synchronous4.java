//package wgc;
//
//public class Synchronous4 extends Thread
//{
//    private static Logger logger = Logger.getLogger(Synchronous4.class);
//
//    public void run()
//    {
//        logger.info("�ļ�������������");
//        PropertiesUtil config=FileUtil.PropertiesUtil.getInstance();
//        while (true)
//        {
//            int mask = JNotify.FILE_CREATED | JNotify.FILE_DELETED
//                       | JNotify.FILE_MODIFIED | JNotify.FILE_RENAMED;
//            //����FILE_CREATED ���� FILE_DELETED ɾ�� FILE_RENAMED ������ FILE_MODIFIED�ļ��䶯
//            try
//            {
//                int watchID=JNotify.addWatch(config.getListenerPath(), mask, true,
//                                             new ActivityFileListener());//ִ�м���
//                long sleep=config.getSyncTime();
//                sleep(sleep);//Ĭ��ÿ���Ӽ���һ��
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
//   * @Description : �½��ļ�
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
// * @Description : ɾ���ļ�
// * @param rootPath
// * @param name
// *            void Created by [Aug 30, 2013] [2:14:54 PM]
// */
//public static void deleteFile(String rootPath, String name)
//{
//    File tempFile = getFile(rootPath, name);
//    boolean isprocess = isBeingUsed(tempFile);
//    if (isprocess)  // �ļ���ռ��
//    {
//        deleteFile(rootPath, name);
//    }
//    else
//    {
//        try
//        {
//            Runtime.getRuntime().exec("rd/s/q "+tempFile.getPath());//����ɾ���ļ������޷�ɾ���ǿ�Ŀ¼
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
// * @Description : �ļ�������
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
// * @Description : �ļ��Ķ�
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
// * @Description : �ļ��Ƿ�ռ��
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
// * @Description : ��ȡͬ����Ҫͬ���ļ���·��
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
//     * @Description : ���������ļ�
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
//            logger.error("synchronous.properties�ļ������ڣ�");
//            throw new InterruptedException();//ֹͣ����
//        }
//        return properties;
//    }
//
//    /**
//     *
//     * @Description : ͬ�����ʱ��
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
//     * @Description : �ļ�ͬ��·��
//     * @return
//     * @throws Exception
//     *             String Created by [Aug 30, 2013] [2:07:51 PM]
//     */
//    public String getSyncPath() throws InterruptedException
//    {
//        String path = getSyncConfig().getProperty(SYNC_PATH, "");
//        if (path.equals(""))
//            throw new InterruptedException("������ͬ��·����");//ֹͣ����
//        return path;
//    }
//
//    /**
//     *
//     * @Description : �ļ�����·��
//     * @return
//     * @throws Exception
//     *             String Created by [Aug 30, 2013] [2:07:38 PM]
//     */
//    public String getListenerPath() throws InterruptedException
//    {
//        String path = getSyncConfig().getProperty(LISTENER_PATH);
//        if (path.equals(""))
//            throw new InterruptedException("�����ü���·����");//ֹͣ����
//        return path;
//    }
//}
////
////�ٴθ��ߴ��һ������ java�޷�ɾ��һ��Ŀ¼����˵�ǿ�Ŀ¼ ��ǰ���ܶ��˶�ȥдһЩ���ӵĴ���
////�����ļ���Ȼ��һ��һ��ȥɾ���ļ� ��һ�����鷳 ���ڸ��ߴ��һ����Ч�İ취
////����ֱ��ʹ��runtimeִ��cmd�����е� rd/s/q �ļ�Ŀ¼ ���ļ��� �Ϳ���ɾ�����ļ����ļ���
////��ǩ�� <��>
