package ru.diolloyd.lesson5atRetrofit.dto;

import lombok.*;

//@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@AllArgsConstructor
@NoArgsConstructor
@With
@ToString
public class Product {
    private Integer id;
    private String title;
    private Integer price;
    private String categoryTitle;
}
