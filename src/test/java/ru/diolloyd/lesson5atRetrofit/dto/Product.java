package ru.diolloyd.lesson5atRetrofit.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

//@JsonInclude(JsonInclude.Include.NON_NULL)
//@AllArgsConstructor
//@NoArgsConstructor
//@With
//@ToString
@Getter
@Setter
@Accessors(chain = true)
public class Product {
    private Integer id;
    private String title;
    private Integer price;
    private String categoryTitle;
}
