package ru.diolloyd.lesson6atOrm.dto;

import com.github.javafaker.Faker;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import ru.diolloyd.lesson6atOrm.enums.CategoryType;

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

@NoArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
@EqualsAndHashCode
public class Product {
    private Integer id;
    private String title;
    private Integer price;
    private String categoryTitle;
    public static final Faker faker = new Faker();

    public static Product createProductByType(CategoryType type) {
        return new Product()
                .setTitle(type.getProductTitleGenerator().get())
                .setCategoryTitle(type.getTitle())
                .setPrice(generateRandomPrice());
    }

    public static Product modifyProductByType(Product product, CategoryType type) {
        return product
                .setTitle(type.getProductTitleGenerator().get())
                .setPrice(generateRandomPrice());
    }

    private static int generateRandomPrice() {
        return (int) (Math.random() * (10000 - 1000 + 1) + 1000);
    }
}
