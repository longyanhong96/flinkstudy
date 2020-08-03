package utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Description : 读properties文件
 *
 * @author lyh
 * @date 2020/5/6
 */
public class PropertyRead {

    public static Properties getProperties(String fileName){
        Properties properties = new Properties();
        InputStream in = null;
        try {
            in = PropertyRead.class.getClassLoader().getResourceAsStream(fileName);
            properties.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (in != null){
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return properties;
    }
}
