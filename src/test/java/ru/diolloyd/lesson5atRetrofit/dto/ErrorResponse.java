package ru.diolloyd.lesson5atRetrofit.dto;

import lombok.Data;

import java.time.Instant;

@Data
public class ErrorResponse {
    private Integer status;
    private String message;
    private Instant timestamp;
    private String error;
    private String path;
}
