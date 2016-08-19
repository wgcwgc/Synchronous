//package wgc;
//
//import javax.swing.text.html.HTMLDocument.HTMLReader.BlockAction;
//
//
//public class Synchronous2 extends BlockAction {
//    private static final String UPLOAD_PATH = "D:\\upload\\";
//    private static final int BUF_SIZE = 8192;
//
//    public ActionForward upload(ActionMapping mapping, ActionForm form,
//            HttpServletRequest request, HttpServletResponse response)
//            throws Exception {
//        final UploadForm bodyForm = (UploadForm) form;
//        final FormFile formFile = bodyForm.getFile();
//
//        if (formFile.getFileSize() > 0) {
//            boolean isUpload = uploadFile(formFile);
//            System.out.println("upload complate is " + isUpload);
//        }
//
//        return mapping.findForward(FORWARD_SHOW);
//    }
//
//    private boolean uploadFile(FormFile formFile) throws FileNotFoundException,
//            IOException {
//
//        // 如果目录不存在，创建目录
//        new File(UPLOAD_PATH).mkdirs();
//
//        final File uploadFile = new File(UPLOAD_PATH, formFile.getFileName());
//
//        // 如果文件已存在，将旧文件删除
//        uploadFile.delete();
//
//        final OutputStream outputStream = new FileOutputStream(uploadFile);
//        BufferedInputStream bufferedInputStream = null;
//        BufferedOutputStream bufferedOutputStream = null;
//        try {
//            bufferedInputStream = new BufferedInputStream(formFile.getInputStream());
//            bufferedOutputStream = new BufferedOutputStream(outputStream);
//
//            final byte temp[] = new byte[BUF_SIZE];
//            int readBytes = 0;
//            while ((readBytes = bufferedInputStream.read(temp)) != -1) {
//                bufferedOutputStream.write(temp, 0, readBytes);
//            }
//            bufferedOutputStream.flush();
//
//        } finally {
//            if (bufferedOutputStream != null) {
//                bufferedOutputStream.close();
//            }
//            if (bufferedInputStream != null) {
//                bufferedInputStream.close();
//            }
//            formFile.destroy();
//        }
//
//        return true;
//
//    }
//}
