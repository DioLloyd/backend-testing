package ru.diolloyd.lesson5atRetrofit;

import com.github.javafaker.Faker;
import lombok.SneakyThrows;
import okhttp3.ResponseBody;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import retrofit2.Response;
import ru.diolloyd.lesson5atRetrofit.dto.Product;
import ru.diolloyd.lesson5atRetrofit.enums.CategoryType;
import ru.diolloyd.lesson5atRetrofit.service.ProductService;
import ru.diolloyd.lesson5atRetrofit.utils.RetrofitUtils;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class CreateProductTest {
    private static ProductService productService;
    private Product product;
    private final Faker faker = new Faker();

    @BeforeAll
    static void beforeAll() {
        productService = RetrofitUtils.getRetrofit()
                .create(ProductService.class);
    }

    @BeforeEach
    void setUp() {
        product = new Product()
                .withTitle(faker.food().dish())
                .withCategoryTitle(CategoryType.FOOD.getTitle())
                .withPrice((int) (Math.random() * (10000 - 1000 + 1) + 1000));
    }

    @Test
    @SneakyThrows
    void createProductFoodCategoryTest() {
        Response<Product> createResponse = productService
                .createProduct(product)
                .execute();
        assertThat(createResponse.isSuccessful(), is(true));
        assertThat(createResponse.body(), is(notNullValue()));
        System.out.println(createResponse.body());
        Response<ResponseBody> deleteResponse = productService
                .deleteProduct(createResponse.body().getId())
                .execute();
        assertThat(deleteResponse.isSuccessful(), is(true));
        System.out.println(deleteResponse);
        System.out.println(deleteResponse.body());
    }
}
