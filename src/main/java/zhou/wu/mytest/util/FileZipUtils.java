package zhou.wu.mytest.util;

import java.io.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

/**
 * @author Lin.xc
 * @date 2019/10/15
 */
public class FileZipUtils {

    /** 要解压的文件目录 */
    public static final String UN_ZIP_DIR = "C:\\";
    /** 缓存大小 */
    public static final int BUFFER_SIZE = 1024;

    /**
     * zip压缩功能.
     * 压缩baseDir(文件夹目录)下所有文件，包括子目录
     * @throws Exception
     */
    public static void zipFile(String baseDir,String fileName) throws Exception{
        List fileList=getSubFiles(new File(baseDir));
        ZipOutputStream zos=new ZipOutputStream(new FileOutputStream(fileName));
        ZipEntry ze=null;
        byte[] buf=new byte[BUFFER_SIZE];
        int readLen=0;
        for(int i = 0; i <fileList.size(); i++) {
            File f=(File)fileList.get(i);
            ze=new ZipEntry(getAbsFileName(baseDir, f));
            ze.setSize(f.length());
            ze.setTime(f.lastModified());
            zos.putNextEntry(ze);
            InputStream is=new BufferedInputStream(new FileInputStream(f));
            while ((readLen=is.read(buf, 0, BUFFER_SIZE))!=-1) {
                zos.write(buf, 0, readLen);
            }
            is.close();
        }
        zos.close();
    }

    /**
     * 给定根目录，返回另一个文件名的相对路径，用于zip文件中的路径.
     * @param baseDir java.lang.String 根目录
     * @param realFileName java.io.File 实际的文件名
     * @return 相对文件名
     */
    private static String getAbsFileName(String baseDir, File realFileName){
        File real=realFileName;
        File base=new File(baseDir);
        String ret=real.getName();
        while (true) {
            real=real.getParentFile();
            if(real==null){
                break;
            }
            if(real.equals(base)){
                break;
            }else{
                ret=real.getName()+"/"+ret;
            }
        }
        return ret;
    }

    /**
     * 取得指定目录下的所有文件列表，包括子目录.
     * @param baseDir File 指定的目录
     * @return 包含java.io.File的List
     */
    private static List getSubFiles(File baseDir){
        List ret=new ArrayList();
        File[] tmp=baseDir.listFiles();
        for (int i = 0; i <tmp.length; i++) {
            if(tmp[i].isFile()){
                ret.add(tmp[i]);
            }
            if(tmp[i].isDirectory()){
                ret.addAll(getSubFiles(tmp[i]));
            }
        }
        return ret;
    }

    /**
     * 解压缩功能.
     * 将ZIP_FILENAME文件解压到ZIP_DIR目录下.
     * @throws Exception
     */
    public static void upZipFile(String zipPath, String toDirPath) throws Exception{
        ZipFile zfile=new ZipFile(zipPath);
        Enumeration zList=zfile.entries();
        ZipEntry ze=null;
        byte[] buf=new byte[BUFFER_SIZE];
        while(zList.hasMoreElements()){
            ze=(ZipEntry)zList.nextElement();
            if(ze.isDirectory()){
                File f=new File(toDirPath+ze.getName());
                f.mkdir();
                continue;
            }
            OutputStream os=new BufferedOutputStream(new FileOutputStream(getRealFileName(toDirPath, ze.getName())));
            InputStream is=new BufferedInputStream(zfile.getInputStream(ze));
            int readLen=0;
            while ((readLen=is.read(buf, 0, BUFFER_SIZE))!=-1) {
                os.write(buf, 0, readLen);
            }
            is.close();
            os.close();
        }
        zfile.close();
    }

    /**
     * 给定根目录，返回一个相对路径所对应的实际文件名.
     * @param baseDir 指定根目录
     * @param absFileName 相对路径名，来自于ZipEntry中的name
     * @return java.io.File 实际的文件
     */
    public static File getRealFileName(String baseDir, String absFileName){
        String[] dirs=absFileName.split("/");
        File ret=new File(baseDir);
        if(dirs.length>1){
            for (int i = 0; i < dirs.length-1;i++) {
                ret=new File(ret, dirs[i]);
            }
            if(!ret.exists()){
                ret.mkdirs();
            }
            ret=new File(ret, dirs[dirs.length-1]);
            return ret;
        }
        return ret;
    }

    public static void main(String[] args) {
        try {
            upZipFile("D:\\tmp\\54652_20191014_bill.csv.zip","D:\\tmp\\54652_20191014_bill.csv");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
