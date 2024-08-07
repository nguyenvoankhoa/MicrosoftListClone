package com.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigLoader {
    private ConfigLoader() {
    }

    public static String loadProperties(String path) throws IOException {
        Properties properties = new Properties();
        try (InputStream input = ConfigLoader.class.getClassLoader()
                .getResourceAsStream("application.properties")) {
            properties.load(input);
            return properties.getProperty(path);
        }
    }
}
