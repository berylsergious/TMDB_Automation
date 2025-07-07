package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigManager {

    private static final Properties configProperties = new Properties();

    static {
    	File TestFile = new File(System.getProperty("user.dir")+"//src//test//resources//testdata//config.properties");
    	
        try {
            FileInputStream fis = new FileInputStream(TestFile);
            configProperties.load(fis);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load config.properties file!", e);
        }
    }

    public static String getConfigProperties(String key) {
        return configProperties.getProperty(key);
    }
}

