package ru.diolloyd.lesson4atRestassuredAdvanced;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import ru.diolloyd.lesson4atRestassuredAdvanced.model.dto.ImageDataDto;

import java.util.Map;

import static io.restassured.RestAssured.given;
import static ru.diolloyd.lesson4atRestassuredAdvanced.model.Endpoints.*;

public class ApiUtilsCommon {

    /**
     * Методы отправки запросов
     */

    public static Response uploadImageRequest(RequestSpecification requestSpec, ResponseSpecification responseSpec) {
        return given(requestSpec, responseSpec)
                .post(UPLOAD_IMAGE)
                .prettyPeek();
    }

    public static Response getImageRequest(RequestSpecification requestSpec, ResponseSpecification responseSpec, ImageDataDto imageDto) {
        return given(requestSpec, responseSpec)
                .get(GET_IMAGE, imageDto.getImageId())
                .prettyPeek();
    }

    public static Response favoriteImageRequest(RequestSpecification requestSpec, ResponseSpecification responseSpec, ImageDataDto imageDto) {
        return given(requestSpec, responseSpec)
                .post(FAVORITE_IMAGE, imageDto.getImageId())
                .prettyPeek();
    }

    public static Response updateImageInfoRequest(RequestSpecification requestSpec, ResponseSpecification responseSpec, ImageDataDto imageDto) {
        return given(requestSpec, responseSpec)
                .post(UPDATE_IMAGE, imageDto.getImageId())
                .prettyPeek();
    }

    public static Response deleteImageRequest(RequestSpecification requestSpec, ResponseSpecification responseSpec, ImageDataDto imageDto) {
        return given(requestSpec, responseSpec)
                .delete(DELETE_IMAGE, Map.of(IMAGE_DELETE_HASH_PARAM, imageDto.getDeleteHash()))
                .prettyPeek();
    }
}
