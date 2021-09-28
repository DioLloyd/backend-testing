package ru.diolloyd.lesson4atRestassuredAdvanced;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeAll;

import java.io.File;
import java.io.FileReader;
import java.util.Properties;

public abstract class BaseTest {
    protected static final File file = new File("src/test/resources/application.properties");
    protected static final Properties properties = new Properties();
    protected static String username;
    protected static RequestSpecification reqSpecAuth;
    protected static RequestSpecification reqSpecHdJpgImageSpec;
    protected final File JPG_HD_IMAGE = new File("src/test/resources/Cruella.jpg");
    protected final File BMP_1x1_IMAGE = new File("src/test/resources/pic1x1.bmp");
    protected final File PNG_IMAGE = new File("src/test/resources/Carl-meme.png");
    protected final File GIF_IMAGE = new File("src/test/resources/Cat.gif");
    protected final File EMPTY_FILE = new File("src/test/resources/empty");

    @SneakyThrows
    @BeforeAll
    public static void beforeAll() {
        properties.load(new FileReader(file));
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails(); //TODO посмотерть логи без этой строчки
        RestAssured.filters(new AllureRestAssured());
        RestAssured.baseURI = "https://api.imgur.com/3";
        reqSpecAuth = new RequestSpecBuilder()
                .addHeader("Authorization", properties.getProperty("token"))
                .build();
//        reqSpecHdJpgImageSpec = new RequestSpecBuilder()
//                .addMultiPart("image", new File("src/test/resources/Cruella.jpg"))
//                .build();
    }
}
