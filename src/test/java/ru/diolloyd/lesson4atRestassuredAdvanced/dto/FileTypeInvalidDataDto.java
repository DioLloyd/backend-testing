package ru.diolloyd.lesson4atRestassuredAdvanced.dto;

import lombok.Data;

import java.util.Map;

@Data
public class FileTypeInvalidDataDto {
    private Map<String, Object> error;
    private String request;
    private String method;
}
