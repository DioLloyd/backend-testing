package ru.diolloyd.lesson4atRestassuredAdvanced.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@Accessors(chain = true)
//@JsonRootName("data")
public class ImageDataDto {
    @JsonProperty("id")
    private String imageId;
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

