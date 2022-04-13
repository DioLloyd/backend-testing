package ru.diolloyd.lesson4atRestassuredAdvanced;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeAll;

import java.io.File;
import java.io.FileReader;
import java.util.Properties;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsEqual.equalTo;

public abstract class BaseTest {
    protected static final File file = new File("src/test/resources/application.properties");
    protected static final Properties properties = new Properties();
    protected static String username;
    protected static RequestSpecification requestSpecAuth;
    protected static ResponseSpecification responsePositiveSpec;
    protected static ResponseSpecification responseDataTrueSpec;
    protected static ResponseSpecification response404Spec;
    protected final File JPG_HD_IMAGE = new File("src/test/resources/Cruella.jpg");
    protected final File BMP_1x1_IMAGE = new File("src/test/resources/pic1x1.bmp");
    protected final File PNG_IMAGE = new File("src/test/resources/Carl-meme.png");
    protected final File GIF_IMAGE = new File("src/test/resources/Cat.gif");
    protected final File EMPTY_FILE = new File("src/test/resources/empty");

    @SneakyThrows
    @BeforeAll
    public static void beforeAll() {
        properties.load(new FileReader(file));
        username = properties.getProperty("username");
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails(); //TODO посмотерть логи без этой строчки
        RestAssured.filters(new AllureRestAssured());
        RestAssured.baseURI = "https://api.imgur.com/3";
        requestSpecAuth = new RequestSpecBuilder()
                .addHeader("Authorization", properties.getProperty("token"))
                .log(LogDetail.ALL)
                .build();
        responsePositiveSpec = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectBody("status", equalTo(200))
                .expectBody("success", is(true))
                .expectContentType(ContentType.JSON)
                .build();
        responseDataTrueSpec = new ResponseSpecBuilder()
                .expectBody("data", equalTo(true))
                .build();
        response404Spec = new ResponseSpecBuilder()
                .expectStatusCode(404)
                .expectContentType(ContentType.HTML)
                .build();
    }
}
