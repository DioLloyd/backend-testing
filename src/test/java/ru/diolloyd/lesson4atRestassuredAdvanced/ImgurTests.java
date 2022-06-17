package ru.diolloyd.lesson4atRestassuredAdvanced;

import io.restassured.response.Response;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class ImgurTests extends BaseTest {

    private final String endpoint = "https://api.imgur.com/3/account/";

    @Test
    @SneakyThrows
    public void getAccountTest() {
        Response response = given(requestSpecAuth)
                .when()
                .get(endpoint + username)
                .prettyPeek();
        assertThat(response.jsonPath().get("data.url"), equalTo(username));
        assertThat(response.statusCode(), equalTo(200));
    }
}
