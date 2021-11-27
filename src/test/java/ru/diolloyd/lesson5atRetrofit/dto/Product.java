package ru.diolloyd.lesson5atRetrofit.dto;

import com.github.javafaker.Faker;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import ru.diolloyd.lesson5atRetrofit.enums.CategoryType;

/**
 * Альтернативное создание объекта с помощью аннотации @With
 * Для корректной работы аннотации @With требуются следующие аннотации у Product:
 * With
 * AllArgsConstructor
 * NoArgsConstructor
 * Тогда создание продукта будет выглядеть так:
 * new Product()
 * .withTitle(faker.food().dish())
 * .withCategoryTitle(CategoryType.FOOD.getTitle())
 * .withPrice((int) (Math.random() * (10000 - 1000 + 1) + 1000));
 */

@Getter
@Setter
@Accessors(chain = true)
@EqualsAndHashCode
public class Product {
    private Integer id;
    private String title;
    private Integer price;
    private String categoryTitle;
    private static final Faker faker = new Faker();

    public static Product getProductByType(CategoryType type) {
        switch (type) {
            case FOOD:
                return new Product()
                        .setTitle(faker.food().dish())
                        .setCategoryTitle(CategoryType.FOOD.getTitle())
                        .setPrice((int) (Math.random() * (10000 - 1000 + 1) + 1000));
            case ELECTRONIC:
                return new Product()
                        .setTitle("electric " + faker.aviation().aircraft())
                        .setCategoryTitle(CategoryType.ELECTRONIC.getTitle())
                        .setPrice((int) (Math.random() * (10000 - 1000 + 1) + 1000));
            case FURNITURE:
                return new Product()
                        .setTitle(faker.color().name() + " commode")
                        .setCategoryTitle(CategoryType.FURNITURE.getTitle())
                        .setPrice((int) (Math.random() * (10000 - 1000 + 1) + 1000));
        }
        return null;
    }

    public static Product modifyProductByType(Product product, CategoryType type) {
        switch (type) {
            case FOOD:
                return product
                        .setTitle(faker.food().dish())
                        .setPrice((int) (Math.random() * (10000 - 1000 + 1) + 1000));
            case ELECTRONIC:
                return product
                        .setTitle("electric " + faker.aviation().aircraft())
                        .setPrice((int) (Math.random() * (10000 - 1000 + 1) + 1000));
            case FURNITURE:
                return product
                        .setTitle(faker.color().name() + " commode")
                        .setPrice((int) (Math.random() * (10000 - 1000 + 1) + 1000));
        }
        return null;
    }
}
