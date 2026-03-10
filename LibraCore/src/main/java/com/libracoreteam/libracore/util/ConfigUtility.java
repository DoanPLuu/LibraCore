
package com.libracoreteam.libracore.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigUtility {
    private static final Properties properties = new Properties();

    static {
        try (InputStream input = ConfigUtility.class
                .getClassLoader()
                .getResourceAsStream("config.properties")) {

            if (input == null) {
                throw new RuntimeException("Không tìm thấy config.properties");
            }
            properties.load(input);

        } catch (IOException e) {
            throw new RuntimeException("Lỗi load config.properties", e);
        }
    }

    public static String getConfig(String key) {
        return properties.getProperty(key);
    }
}
