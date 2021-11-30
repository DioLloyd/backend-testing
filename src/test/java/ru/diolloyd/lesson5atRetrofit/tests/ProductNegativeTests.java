package ru.diolloyd.lesson5atRetrofit.tests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.SneakyThrows;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import retrofit2.Response;
import ru.diolloyd.lesson5atRetrofit.dto.ErrorResponse;
import ru.diolloyd.lesson5atRetrofit.dto.Product;
import ru.diolloyd.lesson5atRetrofit.enums.CategoryType;
import ru.diolloyd.lesson5atRetrofit.services.ProductService;
import ru.diolloyd.lesson5atRetrofit.utils.ProductServiceRequests;
import ru.diolloyd.lesson5atRetrofit.utils.RetrofitUtils;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class ProductNegativeTests {

    private final ProductService productService = RetrofitUtils.getRetrofit().create(ProductService.class);
    ProductServiceRequests requests = new ProductServiceRequests(productService);

    @SneakyThrows
    @ParameterizedTest
    @EnumSource(CategoryType.class)
    void createProductWithIdNegativeTest(CategoryType type) {
        Product product = Product.createProductByType(type);
        product.setId((int) (Math.random() * 100));
        Response<Product> response = requests.createProduct(product);
        assertThat(response.code(), equalTo(400));
        assertThat(response.errorBody(), is(notNullValue()));
        ErrorResponse errorResponse = new ObjectMapper().registerModule(new JavaTimeModule()).readValue(response.errorBody().string(), ErrorResponse.class);
        assertThat(errorResponse.getStatus(), equalTo(400));
        assertThat(errorResponse.getMessage(), equalTo("Id must be null for new entity"));
    }

    /* Тест упадёт т.к. сервис создаёт продукт без категории. */
//    @ParameterizedTest
    @SneakyThrows
    @EnumSource(CategoryType.class)
    void createNoCategoryTitleProduct(CategoryType type) {
        Product product = Product.createProductByType(type);
        product.setCategoryTitle(null);
        Response<Product> response = requests.createProduct(product);
        assertThat(response.code(), equalTo(400));
        assertThat(response.errorBody(), is(notNullValue()));
        ErrorResponse errorResponse = new ObjectMapper().registerModule(new JavaTimeModule()).readValue(response.errorBody().string(), ErrorResponse.class);
        assertThat(errorResponse.getStatus(), equalTo(400));
        assertThat(errorResponse.getMessage(), equalTo("CategoryTitle must be null for new entity"));
    }

    /* Тест упадёт т.к. сервис создаёт продукт с отрицательной ценой. */
//    @ParameterizedTest
    @SneakyThrows
    @EnumSource(CategoryType.class)
    void createNegativePriceProduct(CategoryType type) {
        Product product = Product.createProductByType(type);
        product.setPrice((int) ((Math.random() * (10000 - 1000 + 1) + 1000)) * -1);
        Response<Product> response = requests.createProduct(product);
        assertThat(response.code(), equalTo(400));
        assertThat(response.errorBody(), is(notNullValue()));
        ErrorResponse errorResponse = new ObjectMapper().registerModule(new JavaTimeModule()).readValue(response.errorBody().string(), ErrorResponse.class);
        assertThat(errorResponse.getStatus(), equalTo(400));
        assertThat(errorResponse.getMessage(), equalTo("The price cannot be negative"));
    }
}
