package ru.diolloyd.lesson6atOrm.utils;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

import java.io.InputStream;
import java.util.Properties;

@UtilityClass
public class ConfigUtils {
    private final Properties properties = new Properties();
    private final InputStream configFile = ConfigUtils.class.getResourceAsStream("application.properties");

    @SneakyThrows
    public String getBaseUrl() {
        properties.load(configFile);
        return properties.getProperty("url");
    }
}
