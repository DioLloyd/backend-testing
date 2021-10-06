package ru.diolloyd.lesson5atRetrofit.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class GetCategoryResponse {
    private Integer id;
    private String title;
    private List<Product> products = new ArrayList<>();
}
