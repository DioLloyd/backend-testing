package ru.diolloyd.lesson4atRestassuredAdvanced.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ImageDataDto {
    @JsonProperty("id")
    protected String imageId;
    @JsonProperty("deletehash")
    private String deleteHash;
    private String title;
    private String description;
    @JsonProperty("type")
    private String imageType;
    private Integer width;
    private Integer height;
    private Boolean favorite;
}

