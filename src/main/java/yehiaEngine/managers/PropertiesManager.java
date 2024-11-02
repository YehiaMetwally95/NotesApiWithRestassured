package yehiaEngine.managers;

import org.apache.commons.io.FileUtils;
import yehiaEngine.loggers.LogHelper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.Properties;

public class PropertiesManager {
    private static final String propertiesPath = "src/main/resources/propertiesFiles/";

    public static Properties loadProperties() {
        try {
            Properties properties = new Properties();
            Collection<File> propertiesFilesList;
            propertiesFilesList = FileUtils.listFiles(new File(propertiesPath), new String[]{"properties"}, true);
            propertiesFilesList.forEach(propertyFile -> {
                try {
                    properties.load(new FileInputStream(propertyFile));
                } catch (IOException ioe) {
                    System.out.println(ioe.getMessage());
                }
                properties.putAll(System.getProperties());
                System.getProperties().putAll(properties);
            });
            LogHelper.logInfoStep("Loading Properties File Data");
            return properties;
        }catch (Exception e)
        {
            LogHelper.logErrorStep("Failed to Load Properties File Data",e);
            return null;
        }
    }

    public static String getPropertiesValue(String key){
        return System.getProperty(key);
    }

    public static void AddPropertiesKey(String key, String value) throws IOException {
        Properties properties = loadProperties();
        FileOutputStream output = new FileOutputStream(propertiesPath);
        properties.setProperty(key,value);
        properties.store(output,null);
    }

}
