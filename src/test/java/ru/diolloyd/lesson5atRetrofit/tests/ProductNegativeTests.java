package ru.diolloyd.lesson5atRetrofit.tests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.SneakyThrows;
import okhttp3.ResponseBody;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;
import retrofit2.Response;
import ru.diolloyd.lesson5atRetrofit.dto.ErrorResponse;
import ru.diolloyd.lesson5atRetrofit.dto.Product;
import ru.diolloyd.lesson5atRetrofit.enums.CategoryType;
import ru.diolloyd.lesson5atRetrofit.services.ProductService;
import ru.diolloyd.lesson5atRetrofit.utils.ProductServiceRequests;
import ru.diolloyd.lesson5atRetrofit.utils.RetrofitUtils;

import java.time.Instant;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class ProductNegativeTests {

    private final ProductService productService = RetrofitUtils.getRetrofit().create(ProductService.class);
    private final ProductServiceRequests requests = new ProductServiceRequests(productService);
    private long time;
    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    @SneakyThrows
    @ParameterizedTest
    @EnumSource(CategoryType.class)
    void createProductWithIdNegativeTest(CategoryType type) {
        Product product = Product.createProductByType(type);
        product.setId((int) (Math.random() * 100));
        Response<Product> response = requests.createProduct(product);
        assertThat(response.code(), equalTo(400));
        assertThat(response.errorBody(), is(notNullValue()));
        ErrorResponse errorResponse = objectMapper.readValue(response.errorBody().string(), ErrorResponse.class);
        assertThat(errorResponse.getStatus(), equalTo(400));
        assertThat(errorResponse.getMessage(), equalTo("Id must be null for new entity"));
        time = errorResponse.getTimestamp().getEpochSecond() - (Instant.now().getEpochSecond());
        assertThat(Math.abs(time) < 60L, is(true));
    }

    /* Тест упадёт т.к. сервис создаёт продукт без категории. */
    @Disabled
    @ParameterizedTest
    @SneakyThrows
    @EnumSource(CategoryType.class)
    void createNoCategoryNoTitleProductNegativeTest(CategoryType type) {
        Product product = Product.createProductByType(type);
        product.setCategoryTitle(null);
        Response<Product> response = requests.createProduct(product);
        assertThat(response.code(), equalTo(400));
        assertThat(response.errorBody(), is(notNullValue()));
        ErrorResponse errorResponse = objectMapper.readValue(response.errorBody().string(), ErrorResponse.class);
        assertThat(errorResponse.getStatus(), equalTo(400));
        assertThat(errorResponse.getMessage(), equalTo("CategoryTitle must be null for new entity"));
        time = errorResponse.getTimestamp().getEpochSecond() - (Instant.now().getEpochSecond());
        assertThat(Math.abs(time) < 60L, is(true));
    }

    /* Тест упадёт т.к. сервис создаёт продукт с отрицательной ценой. */
    @Disabled
    @ParameterizedTest
    @SneakyThrows
    @EnumSource(CategoryType.class)
    void createNegativePriceProductNegativeTest(CategoryType type) {
        Product product = Product.createProductByType(type);
        product.setPrice((int) ((Math.random() * (10000 - 1000 + 1) + 1000)) * -1);
        Response<Product> response = requests.createProduct(product);
        assertThat(response.code(), equalTo(400));
        assertThat(response.errorBody(), is(notNullValue()));
        ErrorResponse errorResponse = objectMapper.readValue(response.errorBody().string(), ErrorResponse.class);
        assertThat(errorResponse.getStatus(), equalTo(400));
        assertThat(errorResponse.getMessage(), equalTo("The price cannot be negative"));
        time = errorResponse.getTimestamp().getEpochSecond() - (Instant.now().getEpochSecond());
        assertThat(Math.abs(time) < 60L, is(true));
    }

    @SneakyThrows
    @Test
    void modifyProductNegativeTest() {
        Response<Product> response = requests.modifyProduct(new Product());
        assertThat(response.code(), equalTo(400));
        assertThat(response.errorBody(), is(notNullValue()));
        ErrorResponse errorResponse = objectMapper.readValue(response.errorBody().string(), ErrorResponse.class);
        assertThat(errorResponse.getStatus(), equalTo(400));
        assertThat(errorResponse.getMessage(), equalTo("Id must be not null for new entity"));
        time = errorResponse.getTimestamp().getEpochSecond() - (Instant.now().getEpochSecond());
        assertThat(Math.abs(time) < 60L, is(true));
    }

    @ValueSource(ints = {-42, -1, 0, 9999999})
    @SneakyThrows
    @ParameterizedTest
    void getProductNegativeTest(int id) {
        Response<Product> response = requests.getProduct(id);
        assertThat(response.code(), equalTo(404));
        assertThat(response.errorBody(), is(notNullValue()));
        ErrorResponse errorResponse = objectMapper.readValue(response.errorBody().string(), ErrorResponse.class);
        assertThat(errorResponse.getStatus(), equalTo(404));
        assertThat(errorResponse.getMessage(), equalTo("Unable to find product with id: " + id));
        time = errorResponse.getTimestamp().getEpochSecond() - (Instant.now().getEpochSecond());
        assertThat(Math.abs(time) < 60L, is(true));
    }

    @ValueSource(ints = {-42, -1, 0, 9999999})
    @SneakyThrows
    @ParameterizedTest
    void deleteProductNegativeTest(int id) {
        Response<ResponseBody> response = requests.deleteProduct(id);
        assertThat(response.code(), equalTo(500));
        assertThat(response.errorBody(), is(notNullValue()));
        ErrorResponse errorResponse = objectMapper.readValue(response.errorBody().string(), ErrorResponse.class);
        assertThat(errorResponse.getStatus(), equalTo(500));
        assertThat(errorResponse.getMessage(), equalTo(""));
        assertThat(errorResponse.getError(), equalTo("Internal Server Error"));
        assertThat(errorResponse.getPath(), equalTo("/market/api/v1/products/" + id));
        time = errorResponse.getTimestamp().getEpochSecond() - (Instant.now().getEpochSecond());
        assertThat(Math.abs(time) < 60L, is(true));
    }
}
