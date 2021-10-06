package ru.diolloyd.lesson5atRetrofit.utils;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

import java.io.File;
import java.io.FileReader;
import java.util.Properties;

@UtilityClass
public class ConfigUtils {
    private final Properties properties = new Properties();
    private final File configFile = new File("src/test/resources/application.properties");

    @SneakyThrows
    public String getBaseUrl() {
        properties.load(new FileReader(configFile));
        return properties.getProperty("url");
    }
}
