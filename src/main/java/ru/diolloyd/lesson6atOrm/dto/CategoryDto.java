package ru.diolloyd.lesson6atOrm.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

@Accessors(chain = true)
@Data
public class CategoryDto {
    private Integer id;
    private String title;
    private List<ProductDto> products = new ArrayList<>();
}
