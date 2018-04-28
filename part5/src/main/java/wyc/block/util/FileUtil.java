package wyc.block.util;

import java.io.*;

public class FileUtil {

    /**
     * 将object写入文件，object必须实现序列化接口
     * @param o
     * @param filePath
     */
    public static void writeObject(Object o,String filePath) {
        try {
            FileOutputStream outStream = new FileOutputStream(filePath);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outStream);
            objectOutputStream.writeObject(o);
            outStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 读取文件转化成对象
     * @param filePath
     * @return
     */
    public static Object readObject(String filePath){
        FileInputStream freader;
        Object o = null;
        try {
            freader = new FileInputStream(filePath);
            if(freader.available()==0){
                return null;
            }
            ObjectInputStream objectInputStream = new ObjectInputStream(freader);
            o =  objectInputStream.readObject();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return o;
    }

    /**
     * 创建文件目录，没有就创建，有就不管
     * @param dir
     */
    public static void createDir(String dir){
        File f = new File(dir);
        if (!f.exists()) {
            f.mkdirs();
        }
    }

    /**
     * 创建文件，没有就创建，有就不管
     * @param filePath
     */
    public static void createFile(String filePath){
        try {
            File f = new File(filePath);
            if (!f.exists()) {
                f.createNewFile();
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}
