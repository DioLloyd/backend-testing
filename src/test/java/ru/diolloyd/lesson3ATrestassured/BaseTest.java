package ru.diolloyd.lesson3ATrestassured;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeAll;

import java.io.File;
import java.io.FileReader;
import java.util.Map;
import java.util.Properties;

public abstract class BaseTest {
    protected static final File file = new File("src/test/resources/application.properties");
    protected static final Properties properties = new Properties();
    //    protected static String token;
    protected static String username;
    protected static Map<String, String> authHeader;

    @SneakyThrows
    @BeforeAll
    public static void beforeAll() {
        properties.load(new FileReader(file));
//        token = properties.getProperty("token");
        username = properties.getProperty("username");
        authHeader = Map.of("Authorization", properties.getProperty("token"));
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.filters(new AllureRestAssured());
    }
}
