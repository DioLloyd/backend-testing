package ru.diolloyd.lesson3ATrestassured;

import io.restassured.RestAssured;
import io.restassured.builder.MultiPartSpecBuilder;
import io.restassured.config.HttpClientConfig;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.charset.StandardCharsets;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;

public class ImageTests extends BaseTest {

    private String imageHash;
    private String deleteHash;

    /**
     * Позитивные тесты
     */


    @Test
    public void uploadHdJpgImageTest() {
        uploadImage(new File("src/test/resources/Cruella.jpg"), 200);
        deleteImage();
    }

    @Test
    public void getHdJpgImageTest() {
        uploadImage(new File("src/test/resources/Cruella.jpg"), 200);
        getImage(null,
                null,
                "image/jpeg",
                1920,
                1080,
                200);
        deleteImage();
    }

    @Test
    public void updateHdJpgImageInfoTest() {
        uploadImage(new File("src/test/resources/Cruella.jpg"), 200);
        updateImageInfo("Это HD картинка", "Это описание HD картинки", 200);
        getImage("Это HD картинка",
                "Это описание HD картинки",
                "image/jpeg",
                1920,
                1080,
                200);
        deleteImage();
    }

    @Test
    public void upload1x1bmpImageTest() {
        uploadImage(new File("src/test/resources/pic1x1.bmp"), 200);
        deleteImage();
    }

    @Test
    public void get1x1bmpImageTest() {
        uploadImage(new File("src/test/resources/pic1x1.bmp"), 200);
        getImage(null,
                null,
                "image/png",
                1,
                1,
                200);
        deleteImage();
    }

    @Test
    public void update1x1bmpImageInfoTest() {
        uploadImage(new File("src/test/resources/pic1x1.bmp"), 200);
        updateImageInfo("Это картинка 1x1px", "Это описание картинки 1x1px", 200);
        getImage("Это картинка 1x1px",
                "Это описание картинки 1x1px",
                "image/png",
                1,
                1,
                200);
        deleteImage();
    }

    @Test
    public void uploadPngImageTest() {
        uploadImage(new File("src/test/resources/Carl-meme.png"), 200);
        deleteImage();
    }

    @Test
    public void getPngImageTest() {
        uploadImage(new File("src/test/resources/Carl-meme.png"), 200);
        getImage(null,
                null,
                "image/png",
                1280,
                720,
                200);
        deleteImage();
    }

    @Test
    public void updatePngImageInfoTest() {
        uploadImage(new File("src/test/resources/Carl-meme.png"), 200);
        updateImageInfo("Это PNG картинка", "Это описание PNG картинки", 200);
        getImage("Это PNG картинка",
                "Это описание PNG картинки",
                "image/png",
                1280,
                720,
                200);
        deleteImage();
    }

    @Test
    public void uploadGifImageTest() {
        uploadImage(new File("src/test/resources/Cat.gif"), 200);
        deleteImage();
    }

    @Test
    public void getGifImageTest() {
        uploadImage(new File("src/test/resources/Cat.gif"), 200);
        getImage(null,
                null,
                "image/gif",
                800,
                528,
                200);
        deleteImage();
    }

    @Test
    public void updateGifImageInfoTest() {
        uploadImage(new File("src/test/resources/Cat.gif"), 200);
        updateImageInfo("Это GIF изображение", "Это описание GIF изображения", 200);
        getImage("Это GIF изображение",
                "Это описание GIF изображения",
                "image/gif",
                800,
                528,
                200);
        deleteImage();
    }

    /**
     * Негативные тесты
     */

    @Test
    public void uploadEmptyFileTest() {
        uploadImage(new File("src/test/resources/empty"), 400);
    }

    @Test
    public void updateNonExistentImageTest() {
        imageHash = RandomStringUtils.randomAlphanumeric(7);
        updateImageInfo("", "", 404);
    }

    @Test
    public void getNonExistentImageTest() {
        imageHash = RandomStringUtils.randomAlphanumeric(7);
        getImage(null, null, null, null, null, 404);
    }

    @Test
    public void deleteNonExistentImageTest() {
        deleteHash = RandomStringUtils.randomAlphanumeric(15);
        deleteImage();
    }


    /**
     * Методы отправки запросов
     */

    private void updateImageInfo(String title, String description, Integer statusCode) {
        Response response = given()
                .config(RestAssured.config().httpClient(
                        HttpClientConfig.httpClientConfig()
                                .httpMultipartMode(HttpMultipartMode.BROWSER_COMPATIBLE)))
                .headers(authHeader)
                .log().all()
                .multiPart(
                        new MultiPartSpecBuilder(title)
                                .charset(StandardCharsets.UTF_8)
                                .controlName("title")
                                .build()
                )
                .multiPart(
                        new MultiPartSpecBuilder(description)
                                .charset(StandardCharsets.UTF_8)
                                .controlName("description")
                                .build()
                )
                .when()
                .post("https://api.imgur.com/3/image/{imageHash}", imageHash)
                .prettyPeek();
        assertThat(response.statusCode(), equalTo(statusCode));
        if (statusCode == 200) {
            assertThat(response.jsonPath().get("success"), equalTo(true));
            assertThat(response.jsonPath().get("data"), equalTo(true));
        } else if (statusCode == 404) {
            assertThat(response.header("content-type").contains("text/html"), equalTo(true));
        }
    }

    private void uploadImage(File file, Integer statusCode) {
        Response response = given()
                .headers(authHeader)
                .multiPart("image", file)
                .when()
                .post("https://api.imgur.com/3/image")
                .prettyPeek();
        assertThat(response.statusCode(), equalTo(statusCode));
        if (statusCode == 200) {
            assertThat(response.jsonPath().get("success"), equalTo(true));
            assertThat(response.jsonPath().get("data.id"), is(notNullValue()));
            assertThat(response.jsonPath().get("data.deletehash"), is(notNullValue()));
            imageHash = response.jsonPath().getString("data.id");
            deleteHash = response.jsonPath().getString("data.deletehash");
        } else if (statusCode == 400) {
            assertThat(response.jsonPath().get("success"), equalTo(false));
            assertThat(response.jsonPath().get("data.request"), equalTo("/3/image"));
            assertThat(response.jsonPath().get("data.method"), equalTo("POST"));
        }
    }

    private void deleteImage() {
        Response response = given()
                .headers(authHeader)
                .when()
                .delete("https://api.imgur.com/3/image/{imageDeleteHash}", deleteHash)
                .prettyPeek();
        assertThat(response.jsonPath().get("status"), equalTo(200));
        assertThat(response.jsonPath().get("data"), equalTo(true));
        assertThat(response.jsonPath().get("success"), equalTo(true));
    }

    private void getImage(String title,
                          String description,
                          String imageType,
                          Integer width,
                          Integer height,
                          Integer statusCode) {
        Response response = given()
                .headers(authHeader)
                .when()
                .get("https://api.imgur.com/3/image/{imageHash}", imageHash)
                .prettyPeek();
        assertThat(response.statusCode(), equalTo(statusCode));
        if (statusCode == 200) {
            assertThat(response.jsonPath().get("success"), equalTo(true));
            assertThat(response.jsonPath().get("data.deletehash"), equalTo(deleteHash));
            assertThat(response.jsonPath().get("data.id"), equalTo(imageHash));
            assertThat(response.jsonPath().get("data.type"), equalTo(imageType));
            assertThat(response.jsonPath().get("data.title"), equalTo(title));
            assertThat(response.jsonPath().get("data.description"), equalTo(description));
            assertThat(response.jsonPath().get("data.width"), equalTo(width));
            assertThat(response.jsonPath().get("data.height"), equalTo(height));
        } else if (statusCode == 404) {
            assertThat(response.header("content-type").contains("text/html"), equalTo(true));
        }
    }
}
