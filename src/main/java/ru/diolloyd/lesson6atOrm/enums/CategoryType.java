package ru.diolloyd.lesson6atOrm.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.function.Supplier;

import static ru.diolloyd.lesson6atOrm.dto.Product.faker;

@AllArgsConstructor
@Getter
public enum CategoryType {

    FOOD("Food", 1, () -> faker.food().dish()),
    ELECTRONIC("Electronic", 2, () -> "electric " + faker.aviation().aircraft()),
    FURNITURE("Furniture", 3, () -> faker.color().name() + " commode");

    private final String title;
    private final Integer id;
    private final Supplier<String> productTitleGenerator;
}
