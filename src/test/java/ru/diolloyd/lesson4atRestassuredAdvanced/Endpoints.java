package ru.diolloyd.lesson4atRestassuredAdvanced;

public class Endpoints {
    public static final String UPLOAD_IMAGE = "/image";
    public static final String GET_IMAGE = "/image/{imageId}";
    public static final String FAVORITE_IMAGE = "/image/{imageId}/favorite";
    public static final String UPDATE_IMAGE = "/image/{imageId}";
    public static final String DELETE_IMAGE = "/image/{imageDeleteHash}";
}
