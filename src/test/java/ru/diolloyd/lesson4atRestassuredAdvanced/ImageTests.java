package ru.diolloyd.lesson4atRestassuredAdvanced;

import io.restassured.builder.MultiPartSpecBuilder;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import ru.diolloyd.lesson4atRestassuredAdvanced.model.dto.FavoriteResponseDto;
import ru.diolloyd.lesson4atRestassuredAdvanced.model.dto.ImageDataDto;
import ru.diolloyd.lesson4atRestassuredAdvanced.model.dto.ImageResponseDto;

import java.io.File;
import java.nio.charset.StandardCharsets;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
import static ru.diolloyd.lesson4atRestassuredAdvanced.ApiUtilsCommon.*;
import static ru.diolloyd.lesson4atRestassuredAdvanced.model.dto.FavoriteDataDto.FAVORITED;
import static ru.diolloyd.lesson4atRestassuredAdvanced.model.dto.FavoriteDataDto.UNFAVORITED;

public class ImageTests extends BaseTest {

    /**
     * Позитивные тесты авторизованным пользователем
     */

    @Test
    public void uploadHdJpgImageTest() {
        ImageDataDto imageDto = uploadImagePositive(JPG_HD_IMAGE);
        deleteImagePositive(imageDto);
    }

    @Test
    public void getHdJpgImageTest() {
        ImageDataDto imageDto = uploadImagePositive(JPG_HD_IMAGE);
        getImagePositive(imageDto);
        deleteImagePositive(imageDto);
    }

    @Test
    public void updateHdJpgImageInfoTest() {
        ImageDataDto imageDto = uploadImagePositive(JPG_HD_IMAGE);
        imageDto.setTitle("Это HD JPG картинка")
                .setDescription("Это описание HD JPG картинки");
        updateImagePositive(imageDto);
        getImagePositive(imageDto);
        deleteImagePositive(imageDto);
    }

    @Test
    public void favoriteHdJpgImageTest() {
        ImageDataDto imageDto = uploadImagePositive(JPG_HD_IMAGE);
        favoriteImagePositive(imageDto);
        getImagePositive(imageDto);
        deleteImagePositive(imageDto);
    }

    @Test
    public void upload1x1bmpImageTest() {
        ImageDataDto imageDto = uploadImagePositive(BMP_1x1_IMAGE);
        deleteImagePositive(imageDto);
    }

    @Test
    public void get1x1bmpImageTest() {
        ImageDataDto imageDto = uploadImagePositive(BMP_1x1_IMAGE);
        getImagePositive(imageDto);
        deleteImagePositive(imageDto);
    }

    @Test
    public void update1x1bmpImageInfoTest() {
        ImageDataDto imageDto = uploadImagePositive(BMP_1x1_IMAGE);
        imageDto.setTitle("Это BMP картинка 1x1px")
                .setDescription("Это описание BMP картинки 1x1px");
        updateImagePositive(imageDto);
        getImagePositive(imageDto);
        deleteImagePositive(imageDto);
    }

    @Test
    public void favorite1x1bmpImageTest() {
        ImageDataDto imageDto = uploadImagePositive(BMP_1x1_IMAGE);
        favoriteImagePositive(imageDto);
        getImagePositive(imageDto);
        deleteImagePositive(imageDto);
    }

    @Test
    public void uploadPngImageTest() {
        ImageDataDto imageDto = uploadImagePositive(PNG_IMAGE);
        deleteImagePositive(imageDto);
    }

    @Test
    public void getPngImageTest() {
        ImageDataDto imageDto = uploadImagePositive(PNG_IMAGE);
        getImagePositive(imageDto);
        deleteImagePositive(imageDto);
    }

    @Test
    public void updatePngImageInfoTest() {
        ImageDataDto imageDto = uploadImagePositive(PNG_IMAGE);
        imageDto.setTitle("Это PNG картинка")
                .setDescription("Это описание PNG картинки");
        updateImagePositive(imageDto);
        getImagePositive(imageDto);
        deleteImagePositive(imageDto);
    }

    @Test
    public void favoritePngImageTest() {
        ImageDataDto imageDto = uploadImagePositive(PNG_IMAGE);
        favoriteImagePositive(imageDto);
        getImagePositive(imageDto);
        deleteImagePositive(imageDto);
    }

    @Test
    public void uploadGifImageTest() {
        ImageDataDto imageDto = uploadImagePositive(GIF_IMAGE);
        deleteImagePositive(imageDto);
    }

    @Test
    public void getGifImageTest() {
        ImageDataDto imageDto = uploadImagePositive(GIF_IMAGE);
        getImagePositive(imageDto);
        deleteImagePositive(imageDto);
    }

    @Test
    public void updateGifImageInfoTest() {
        ImageDataDto imageDto = uploadImagePositive(PNG_IMAGE);
        imageDto.setTitle("Это GIF изображение")
                .setDescription("Это описание GIF изображения");
        updateImagePositive(imageDto);
        getImagePositive(imageDto);
        deleteImagePositive(imageDto);
    }

    @Test
    public void favoriteGifImageTest() {
        ImageDataDto imageDto = uploadImagePositive(GIF_IMAGE);
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
        updateNonExistentImage(imageDto.getData());
    }

    @Test
    public void getNonExistentImageTest() {
        ImageResponseDto imageDto = new ImageResponseDto();
        imageDto.setData(new ImageDataDto());
        imageDto.getData().setImageId(RandomStringUtils.randomAlphanumeric(7));
        getNonExistentImage(imageDto.getData());
    }

    @Test
    public void favoriteNonExistImageTest() {
        ImageResponseDto imageDto = new ImageResponseDto();
        imageDto.setData(new ImageDataDto());
        imageDto.getData().setImageId(RandomStringUtils.randomAlphanumeric(7));
        favoriteNonExistImage(imageDto.getData());
    }

    @Test
    public void deleteNonExistentImageTest() {
        ImageResponseDto imageDto = new ImageResponseDto();
        imageDto.setData(new ImageDataDto());
        imageDto.getData().setDeleteHash(RandomStringUtils.randomAlphanumeric(15));
        deleteNonExistentImage(imageDto.getData());
    }

    /**
     * Негативные тесты неавторизованным пользователем
     */

    @Test
    public void uploadImageNonAuthUserTest() {

    }

    /**
     * Методы для негативных тестов
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
                        .expectBody("success", is(false))
                        .expectContentType(ContentType.JSON)
                        .build()
        );
        assertThat(response.jsonPath().get("data.request"), equalTo("/3/image"));
        assertThat(response.jsonPath().get("data.method"), equalTo("POST"));
        assertThat(response.jsonPath().get("data.error.code"), equalTo(1003));
        assertThat(response.jsonPath().get("data.error.message"), equalTo("File type invalid (1)"));
        assertThat(response.jsonPath().get("data.error.type"), equalTo("ImgurException"));
    }

    private void updateNonExistentImage(ImageDataDto imageDto) {
        updateImageInfoRequest(
                new RequestSpecBuilder()
                        .addRequestSpecification(requestSpecAuth)
                        .build(),
                new ResponseSpecBuilder()
                        .addResponseSpecification(response404Spec)
                        .build(),
                imageDto);
    }

    private void getNonExistentImage(ImageDataDto imageDto) {
        getImageRequest(
                new RequestSpecBuilder()
                        .addRequestSpecification(requestSpecAuth)
                        .build(),
                new ResponseSpecBuilder()
                        .addResponseSpecification(response404Spec)
                        .build(),
                imageDto
        );
    }

    private void favoriteNonExistImage(ImageDataDto imageDto) {
        favoriteImageRequest(
                new RequestSpecBuilder()
                        .addRequestSpecification(requestSpecAuth)
                        .build(),
                new ResponseSpecBuilder()
                        .addResponseSpecification(response404Spec)
                        .build(),
                imageDto
        );
    }

    private void uploadImageNonAuthUser(File file) {
        Response response = uploadImageRequest(
                new RequestSpecBuilder()
                        .addMultiPart("image", file)
                        .build(),
                new ResponseSpecBuilder()
                        .expectStatusCode(401)
                        .expectBody("status", equalTo(401))
                        .expectBody("success", is(false))
                        .expectContentType(ContentType.JSON)
                        .build()
        );
        assertThat(response.jsonPath().get("data.request"), equalTo("/3/image"));
        assertThat(response.jsonPath().get("data.method"), equalTo("POST"));
        assertThat(response.jsonPath().get("data.error"), equalTo("Authentication required"));
    }

    /* Сервис возвращает успешное удаление даже для несуществующих id */
    public void deleteNonExistentImage(ImageDataDto imageDto) {
        deleteImageRequest(
                new RequestSpecBuilder()
                        .addRequestSpecification(requestSpecAuth)
                        .build(),
                new ResponseSpecBuilder()
                        .addResponseSpecification(responsePositiveSpec)
                        .addResponseSpecification(responseDataTrueSpec)
                        .build(),
                imageDto
        );
    }

    /**
     * Методы для позитивных тестов
     */

    private ImageDataDto uploadImagePositive(File file) {
        Response response = uploadImageRequest(
                new RequestSpecBuilder()
                        .addRequestSpecification(requestSpecAuth)
                        .addMultiPart("image", file)
                        .build(),
                new ResponseSpecBuilder()
                        .addResponseSpecification(responsePositiveSpec)
                        .build()
        );
        ImageResponseDto imageDto = response.getBody().as(ImageResponseDto.class);
        assertThat(imageDto.getData().getImageId(), is(notNullValue()));
        assertThat(imageDto.getData().getDeleteHash(), is(notNullValue()));
        return imageDto.getData();
    }

    private void getImagePositive(ImageDataDto imageDto) {
        Response response = getImageRequest(
                new RequestSpecBuilder()
                        .addRequestSpecification(requestSpecAuth)
                        .build(),
                new ResponseSpecBuilder()
                        .addResponseSpecification(responsePositiveSpec)
                        .build(),
                imageDto);
        ImageDataDto imageResponseDataDto = response.getBody().as(ImageResponseDto.class).getData();
        assertThat(imageResponseDataDto, equalTo(imageDto));
    }

    private void favoriteImagePositive(ImageDataDto imageDto) {
        Response response = favoriteImageRequest(
                new RequestSpecBuilder()
                        .addRequestSpecification(requestSpecAuth)
                        .build(),
                new ResponseSpecBuilder()
                        .addResponseSpecification(responsePositiveSpec)
                        .build(),
                imageDto);
        FavoriteResponseDto favoriteDto = response.getBody().as(FavoriteResponseDto.class);
        if (!imageDto.getFavorite()) {
            assertThat(favoriteDto.getData(), equalTo(FAVORITED.getValue()));
            imageDto.setFavorite(true);
        } else if (imageDto.getFavorite()) {
            assertThat(favoriteDto.getData(), equalTo(UNFAVORITED.getValue()));
            imageDto.setFavorite(false);
        }
    }

    private void updateImagePositive(ImageDataDto imageDto) {
        updateImageInfoRequest(
                new RequestSpecBuilder()
                        .addRequestSpecification(requestSpecAuth)
                        .addMultiPart(
                                new MultiPartSpecBuilder(imageDto.getTitle())
                                        .charset(StandardCharsets.UTF_8)
                                        .controlName("title")
                                        .build()
                        )
                        .addMultiPart(
                                new MultiPartSpecBuilder(imageDto.getDescription())
                                        .charset(StandardCharsets.UTF_8)
                                        .controlName("description")
                                        .build()
                        )
                        .build(),
                new ResponseSpecBuilder()
                        .addResponseSpecification(responsePositiveSpec)
                        .addResponseSpecification(responseDataTrueSpec)
                        .build(),
                imageDto
        );
    }

    private void deleteImagePositive(ImageDataDto imageDto) {
        deleteImageRequest(
                new RequestSpecBuilder()
                        .addRequestSpecification(requestSpecAuth)
                        .build(),
                new ResponseSpecBuilder()
                        .addResponseSpecification(responsePositiveSpec)
                        .addResponseSpecification(responseDataTrueSpec)
                        .build(),
                imageDto);
    }
}
