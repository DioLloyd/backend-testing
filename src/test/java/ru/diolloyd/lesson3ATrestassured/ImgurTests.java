package ru.diolloyd.lesson3ATrestassured;

import io.restassured.response.Response;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class ImgurTests extends BaseTest {

    @Test
    @SneakyThrows
    public void getAccountTest() {
        Response response = given()
                .headers(authHeader)
                .when()
                .get("https://api.imgur.com/3/account/{username}", username)
                .prettyPeek();
        assertThat(response.jsonPath().get("data.url"), equalTo(username));
        assertThat(response.statusCode(), equalTo(200));
    }
}
