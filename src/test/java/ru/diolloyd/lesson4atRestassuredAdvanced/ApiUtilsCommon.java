package ru.diolloyd.lesson4atRestassuredAdvanced;

import io.restassured.builder.MultiPartSpecBuilder;
import io.restassured.response.Response;
import ru.diolloyd.lesson4atRestassuredAdvanced.dto.ImageResponseDto;

import java.io.File;
import java.nio.charset.StandardCharsets;

import static io.restassured.RestAssured.given;
import static ru.diolloyd.lesson4atRestassuredAdvanced.BaseTest.reqSpecAuth;
import static ru.diolloyd.lesson4atRestassuredAdvanced.Endpoints.*;

public class ApiUtilsCommon {

    /**
     * Методы отправки запросов
     */

    public static Response uploadImageRequest(File file) {
        return given(reqSpecAuth)
                .multiPart("image", file)
                .when()
                .post(UPLOAD_IMAGE)
                .prettyPeek();
    }

    public static Response getImageRequest(ImageResponseDto imageDto) {
        return given(reqSpecAuth)
                .when()
                .get(GET_IMAGE, imageDto.getData().getImageId())
                .prettyPeek();
    }

    public static Response favoriteImageRequest(ImageResponseDto imageDto) {
        return given(reqSpecAuth)
                .when()
                .post(FAVORITE_IMAGE, imageDto.getData().getImageId())
                .prettyPeek();
    }

    public static Response updateImageInfoRequest(ImageResponseDto imageDto) {
        return given(reqSpecAuth)
                .multiPart(
                        new MultiPartSpecBuilder(imageDto.getData().getTitle())
                                .charset(StandardCharsets.UTF_8)
                                .controlName("title")
                                .build()
                )
                .multiPart(
                        new MultiPartSpecBuilder(imageDto.getData().getDescription())
                                .charset(StandardCharsets.UTF_8)
                                .controlName("description")
                                .build()
                )
                .when()
                .post(UPDATE_IMAGE, imageDto.getData().getImageId())
                .prettyPeek();
    }

    public static Response deleteImageRequest(ImageResponseDto imageDto) {
        return given(reqSpecAuth)
                .when()
                .delete(DELETE_IMAGE, imageDto.getData().getDeleteHash())
                .prettyPeek();
    }
}
