package ru.diolloyd.lesson4atRestassuredAdvanced;

import io.restassured.builder.MultiPartSpecBuilder;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import ru.diolloyd.lesson4atRestassuredAdvanced.dto.FavoriteResponseDto;
import ru.diolloyd.lesson4atRestassuredAdvanced.dto.ImageDataDto;
import ru.diolloyd.lesson4atRestassuredAdvanced.dto.ImageResponseDto;

import java.io.File;
import java.nio.charset.StandardCharsets;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
import static ru.diolloyd.lesson4atRestassuredAdvanced.ApiUtilsCommon.*;
import static ru.diolloyd.lesson4atRestassuredAdvanced.dto.FavoriteDataDto.FAVORITED;
import static ru.diolloyd.lesson4atRestassuredAdvanced.dto.FavoriteDataDto.UNFAVORITED;

public class ImageTests extends BaseTest {

    /**
     * Позитивные тесты авторизованным пользователем
     */

    @Test
    public void uploadHdJpgImageTest() {
        ImageResponseDto imageDto = uploadImagePositive(JPG_HD_IMAGE);
        deleteImagePositive(imageDto);
    }

    @Test
    public void getHdJpgImageTest() {
        ImageResponseDto imageDto = uploadImagePositive(JPG_HD_IMAGE);
        getImagePositive(imageDto);
        deleteImagePositive(imageDto);
    }

    @Test
    public void updateHdJpgImageInfoTest() {
        ImageResponseDto imageDto = uploadImagePositive(JPG_HD_IMAGE);
        imageDto.getData()
                .setTitle("Это HD JPG картинка")
                .setDescription("Это описание HD JPG картинки");
        updateImagePositive(imageDto);
        getImagePositive(imageDto);
        deleteImagePositive(imageDto);
    }

    @Test
    public void favoriteHdJpgImageTest() {
        ImageResponseDto imageDto = uploadImagePositive(JPG_HD_IMAGE);
        favoriteImagePositive(imageDto);
        getImagePositive(imageDto);
        deleteImagePositive(imageDto);
    }

    @Test
    public void upload1x1bmpImageTest() {
        ImageResponseDto imageDto = uploadImagePositive(BMP_1x1_IMAGE);
        deleteImagePositive(imageDto);
    }

    @Test
    public void get1x1bmpImageTest() {
        ImageResponseDto imageDto = uploadImagePositive(BMP_1x1_IMAGE);
        getImagePositive(imageDto);
        deleteImagePositive(imageDto);
    }

    @Test
    public void update1x1bmpImageInfoTest() {
        ImageResponseDto imageDto = uploadImagePositive(BMP_1x1_IMAGE);
        imageDto.getData()
                .setTitle("Это BMP картинка 1x1px")
                .setDescription("Это описание BMP картинки 1x1px");
        updateImagePositive(imageDto);
        getImagePositive(imageDto);
        deleteImagePositive(imageDto);
    }

    @Test
    public void favorite1x1bmpImageTest() {
        ImageResponseDto imageDto = uploadImagePositive(BMP_1x1_IMAGE);
        favoriteImagePositive(imageDto);
        getImagePositive(imageDto);
        deleteImagePositive(imageDto);
    }

    @Test
    public void uploadPngImageTest() {
        ImageResponseDto imageDto = uploadImagePositive(PNG_IMAGE);
        deleteImagePositive(imageDto);
    }

    @Test
    public void getPngImageTest() {
        ImageResponseDto imageDto = uploadImagePositive(PNG_IMAGE);
        getImagePositive(imageDto);
        deleteImagePositive(imageDto);
    }

    @Test
    public void updatePngImageInfoTest() {
        ImageResponseDto imageDto = uploadImagePositive(PNG_IMAGE);
        imageDto.getData()
                .setTitle("Это PNG картинка")
                .setDescription("Это описание PNG картинки");
        updateImagePositive(imageDto);
        getImagePositive(imageDto);
        deleteImagePositive(imageDto);
    }

    @Test
    public void favoritePngImageTest() {
        ImageResponseDto imageDto = uploadImagePositive(PNG_IMAGE);
        favoriteImagePositive(imageDto);
        getImagePositive(imageDto);
        deleteImagePositive(imageDto);
    }

    @Test
    public void uploadGifImageTest() {
        ImageResponseDto imageDto = uploadImagePositive(GIF_IMAGE);
        deleteImagePositive(imageDto);
    }

    @Test
    public void getGifImageTest() {
        ImageResponseDto imageDto = uploadImagePositive(GIF_IMAGE);
        getImagePositive(imageDto);
        deleteImagePositive(imageDto);
    }

    @Test
    public void updateGifImageInfoTest() {
        ImageResponseDto imageDto = uploadImagePositive(PNG_IMAGE);
        imageDto.getData()
                .setTitle("Это GIF изображение")
                .setDescription("Это описание GIF изображения");
        updateImagePositive(imageDto);
        getImagePositive(imageDto);
        deleteImagePositive(imageDto);
    }

    @Test
    public void favoriteGifImageTest() {
        ImageResponseDto imageDto = uploadImagePositive(GIF_IMAGE);
        favoriteImagePositive(imageDto);
        getImagePositive(imageDto);
        deleteImagePositive(imageDto);
    }

    /**
     * Негативные тесты авторизованным пользователем
     */

    @Test
    public void uploadEmptyFileTest() {
        uploadUnknownFormatNegative(EMPTY_FILE);
    }

    @Test
    public void updateNonExistentImageTest() {
        ImageResponseDto imageDto = new ImageResponseDto();
        imageDto.setData(new ImageDataDto());
        imageDto.getData().setImageId(RandomStringUtils.randomAlphanumeric(7));
        imageDto.getData().setTitle("");
        imageDto.getData().setDescription("");
        updateNonExistentImage(imageDto);
    }

    @Test
    public void getNonExistentImageTest() {
        ImageResponseDto imageDto = new ImageResponseDto();
        imageDto.setData(new ImageDataDto());
        imageDto.getData().setImageId(RandomStringUtils.randomAlphanumeric(7));
        getNonExistentImage(imageDto);
    }

    @Test
    public void favoriteNonExistImageTest() {
        ImageResponseDto imageDto = new ImageResponseDto();
        imageDto.setData(new ImageDataDto());
        imageDto.getData().setImageId(RandomStringUtils.randomAlphanumeric(7));
        favoriteNonExistImage(imageDto);
    }

//    @Test
//    public void deleteNonExistentImageTest() {
//        ImageResponseDto imageDto = new ImageResponseDto();
//        imageDto.setData(new ImageDataDto());
//        imageDto.getData().setDeleteHash(RandomStringUtils.randomAlphanumeric(15));
//        deleteNonExistentImage(imageDto);
//    }

    /**
     * Тестовые методы для негативных тестов
     */


    private void uploadUnknownFormatNegative(File file) {
        Response response = uploadImageRequest(
                new RequestSpecBuilder()
                        .addRequestSpecification(requestSpecAuth)
                        .addMultiPart("image", file)
                        .build(),
                new ResponseSpecBuilder()
                        .expectStatusCode(400)
                        .expectBody("status", equalTo(400))
                        .expectBody("success", Matchers.is(false))
                        .expectContentType(ContentType.JSON)
                        .build()
        );
        assertThat(response.jsonPath().get("data.request"), equalTo("/3/image"));
        assertThat(response.jsonPath().get("data.method"), equalTo("POST"));
        assertThat(response.jsonPath().get("data.error.code"), equalTo(1003));
        assertThat(response.jsonPath().get("data.error.message"), equalTo("File type invalid (1)"));
        assertThat(response.jsonPath().get("data.error.type"), equalTo("ImgurException"));
    }

    private void updateNonExistentImage(ImageResponseDto imageDto) {
        Response response = updateImageInfoRequest(
                requestSpecAuth,
                new ResponseSpecBuilder()
                        .expectStatusCode(404)
                        .build(),
                imageDto);
        assertThat(response.header("content-type").contains("text/html"), equalTo(true));
    }

    private void getNonExistentImage(ImageResponseDto imageDto) {
        Response response = getImageRequest(
                requestSpecAuth,
                new ResponseSpecBuilder()
                        .expectStatusCode(404)
                        .build(),
                imageDto
        );
        assertThat(response.header("content-type").contains("text/html"), equalTo(true));
    }

    private void favoriteNonExistImage(ImageResponseDto imageDto) {
        Response response = favoriteImageRequest(
                requestSpecAuth,
                new ResponseSpecBuilder()
                        .expectStatusCode(404)
                        .build(),
                imageDto
        );
        assertThat(response.header("content-type").contains("text/html"), equalTo(true));
    }

//    TODO: rewrite
//    public void deleteNonExistentImage(ImageResponseDto imageDto) {
//        Response response = deleteImageRequest(
//                requestSpecAuth,
//                new ResponseSpecBuilder()
//                        .expectStatusCode(200)
//                        .build(),
//                imageDto
//        );
//        assertThat(response.header("content-type").contains("text/html"), equalTo(true));
//    }

    /**
     * Тестовые методы для позитивных тестов
     */

    private ImageResponseDto uploadImagePositive(File file) {
        Response response = uploadImageRequest(
                new RequestSpecBuilder()
                        .addRequestSpecification(requestSpecAuth)
                        .addMultiPart("image", file)
                        .build(),
                responsePositiveSpec
        );
        ImageResponseDto imageDto = response.getBody().as(ImageResponseDto.class);
        assertThat(imageDto.getData().getImageId(), is(notNullValue()));
        assertThat(imageDto.getData().getDeleteHash(), is(notNullValue()));
        return imageDto;
    }

    private void getImagePositive(ImageResponseDto imageDto) {
        Response response = getImageRequest(requestSpecAuth, responsePositiveSpec, imageDto);
        ImageResponseDto imageResponseDto = response.getBody().as(ImageResponseDto.class);
        assertThat(imageResponseDto, equalTo(imageDto));
    }

    private void favoriteImagePositive(ImageResponseDto imageDto) {
        Response response = favoriteImageRequest(requestSpecAuth, responsePositiveSpec, imageDto);
        FavoriteResponseDto favoriteDto = response.getBody().as(FavoriteResponseDto.class);
        if (!imageDto.getData().getFavorite()) {
            assertThat(favoriteDto.getData(), equalTo(FAVORITED.getValue()));
            imageDto.getData().setFavorite(true);
        } else if (imageDto.getData().getFavorite()) {
            assertThat(favoriteDto.getData(), equalTo(UNFAVORITED.getValue()));
            imageDto.getData().setFavorite(false);
        }
    }

    private void updateImagePositive(ImageResponseDto imageDto) {
        Response response = updateImageInfoRequest(
                new RequestSpecBuilder()
                        .addRequestSpecification(requestSpecAuth)
                        .addMultiPart(
                                new MultiPartSpecBuilder(imageDto.getData().getTitle())
                                        .charset(StandardCharsets.UTF_8)
                                        .controlName("title")
                                        .build()
                        )
                        .addMultiPart(
                                new MultiPartSpecBuilder(imageDto.getData().getDescription())
                                        .charset(StandardCharsets.UTF_8)
                                        .controlName("description")
                                        .build()
                        )
                        .build(),
                responsePositiveSpec,
                imageDto
        );
        assertThat(response.jsonPath().get("data"), equalTo(true));
    }

    private void deleteImagePositive(ImageResponseDto imageDto) {
        Response response = deleteImageRequest(requestSpecAuth, responsePositiveSpec, imageDto);
        assertThat(response.jsonPath().get("data"), equalTo(true));
    }
}
