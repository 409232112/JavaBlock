package wyc.block.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author fc
 * @Date 2016/12/29
 * @Version 1.0.0
 */
public class PropertiesUtil {
    private  Properties p;

    public PropertiesUtil(String fileName) {
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(fileName);
        if (inputStream != null) {
            p = new Properties();
            try {
                p.load(inputStream);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }
    public  String getPropertyValue(String key){
        if (p==null){
            return null;
        }
        return p.getProperty(key);
    }
}
