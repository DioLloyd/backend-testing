package ru.diolloyd.lesson6atOrm.dto;

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
