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

import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class ProductTests {
    private static ProductService productService;
//    private Product product;
    private final Faker faker = new Faker();

    @BeforeAll
    static void beforeAll() {
        productService = RetrofitUtils.getRetrofit()
                .create(ProductService.class);
    }

//    @BeforeEach
//    void setUp() {
//        product = new Product()
//                .withTitle(faker.food().dish())
//                .withCategoryTitle(CategoryType.FOOD.getTitle())
//                .withPrice((int) (Math.random() * (10000 - 1000 + 1) + 1000));
//    }

    /* Тест будет падать, ошибка в сервисе */
    @SneakyThrows
    @Test
    void getProductsTest() {
        Response<ArrayList<Product>> products = productService
                .getProducts()
                .execute();
        assertThat(products.body(), is(notNullValue()));
        System.out.println(products.body().toString());
    }

    @SneakyThrows
    @Test
    void createProductFoodCategoryTest() {
        Response<Product> response = productService
                .createProduct(getProduct())
                .execute();
        assertThat(response.isSuccessful(), is(true));
        assertThat(response.body(), is(notNullValue()));
        deleteRequest(response.body().getId());
    }

    @SneakyThrows
    @Test
    void modifyProductTest() {
        Response<Product> createResponse = productService
                .createProduct(getProduct())
                .execute();
        assertThat(createResponse.isSuccessful(), is(true));
        assertThat(createResponse.body(), is(notNullValue()));
        createResponse.body()
                .setPrice((int) (Math.random() * (10000 - 1000 + 1) + 1000))
                .setTitle(faker.food().dish());
        Response<Product> modifyResponse = productService
                .modifyProduct(createResponse.body())
                .execute();
        assertThat(modifyResponse.isSuccessful(), is(true));
        assertThat(modifyResponse.body(), is(notNullValue()));
        assertThat(createResponse.body(), equalTo(modifyResponse.body()));
    }

    @SneakyThrows
    @Test
    void getProductTest() {
        Response<Product> createResponse = productService
                .createProduct(getProduct())
                .execute();
        assertThat(createResponse.isSuccessful(), is(true));
        assertThat(createResponse.body(), is(notNullValue()));
        Response<Product> getResponse = productService
                .getProduct(createResponse.body().getId())
                .execute();
        assertThat(getResponse.isSuccessful(), is(true));
        assertThat(getResponse.body(), is(notNullValue()));
        assertThat(createResponse.body(), equalTo(getResponse.body()));
        deleteRequest(createResponse.body().getId());
    }

    @SneakyThrows
    private void deleteRequest(Integer id) {
        Response<ResponseBody> deleteResponse = productService
                .deleteProduct(id)
                .execute();
        assertThat(deleteResponse.isSuccessful(), is(true));
    }

    private Product getProduct() {
        return new Product()
                .setTitle(faker.food().dish())
                .setCategoryTitle(CategoryType.FOOD.getTitle())
                .setPrice((int) (Math.random() * (10000 - 1000 + 1) + 1000));
    }
}
