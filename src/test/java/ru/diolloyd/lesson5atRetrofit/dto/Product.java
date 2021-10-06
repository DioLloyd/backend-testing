package ru.diolloyd.lesson5atRetrofit.dto;

import lombok.EqualsAndHashCode;
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
@EqualsAndHashCode
public class Product {
    private Integer id;
    private String title;
    private Integer price;
    private String categoryTitle;
}
