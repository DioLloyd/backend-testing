package ru.diolloyd.lesson5atRetrofit.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CategoryType {

    FOOD("Food", 1),
    ELECTRONIC("Electronic", 2),
    FURNITURE("Furniture", 3);

    private final String title;
    private final Integer id;
}
