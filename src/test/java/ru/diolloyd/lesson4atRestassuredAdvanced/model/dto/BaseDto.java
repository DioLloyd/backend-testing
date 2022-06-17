package ru.diolloyd.lesson4atRestassuredAdvanced.model.dto;

import lombok.Data;

@Data
public class BaseDto <T> {
    protected T data;
    protected Boolean success;
    protected Integer status;
}
