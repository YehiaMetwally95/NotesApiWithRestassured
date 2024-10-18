package utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesManager {
    public static String filePath;

    public static Properties loadProperties() throws IOException {
        Properties properties = new Properties();
        FileInputStream input = new FileInputStream(filePath);
        properties.load(input);
        properties.putAll(System.getProperties());
        System.getProperties().putAll(properties);
        return properties;
    }

    public static String getPropertiesValue(String key){
        return System.getProperty(key);
    }

    public static void AddPropertiesKey(String key, String value) throws IOException {
        Properties properties = loadProperties();
        FileOutputStream output = new FileOutputStream(filePath);
        properties.setProperty(key,value);
        properties.store(output,null);
    }

}
