package ru.diolloyd.lesson6atOrm.tests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.SneakyThrows;
import okhttp3.ResponseBody;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import retrofit2.Response;
import ru.diolloyd.lesson6atOrm.db.model.Category;
import ru.diolloyd.lesson6atOrm.dto.ErrorResponseDto;
import ru.diolloyd.lesson6atOrm.dto.ProductDto;
import ru.diolloyd.lesson6atOrm.services.ProductService;
import ru.diolloyd.lesson6atOrm.utils.CategoryDao;
import ru.diolloyd.lesson6atOrm.utils.ProductServiceRequests;
import ru.diolloyd.lesson6atOrm.utils.RetrofitUtils;

import java.time.Instant;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static ru.diolloyd.lesson6atOrm.dto.ProductDto.createProductDto;
import static ru.diolloyd.lesson6atOrm.dto.ProductDto.faker;

public class ProductNegativeTests {

    private final ProductService productService = RetrofitUtils.getRetrofit().create(ProductService.class);
    private final ProductServiceRequests requests = new ProductServiceRequests(productService);
    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
    private long time;

    private Category category;

    @BeforeEach
    void createCategoryInDb() {
        category = new Category();
        category.setTitle(faker.aviation().aircraft());
        CategoryDao.createCategoryInDb(category);
    }

    @AfterEach
    void deleteCategoryFromDb() {
        CategoryDao.deleteCategoryFromDb(category.getId());
    }

    @SneakyThrows
    @Test
    void createProductWithIdNegativeTest() {
        ProductDto product = createProductDto(category.getTitle());
        product.setId((int) (1 + (Math.random() * 999)));
        Response<ProductDto> response = requests.createProduct(product);
        assertThat(response.code(), equalTo(400));
        assertThat(response.errorBody(), is(notNullValue()));
        ErrorResponseDto errorResponse = objectMapper.readValue(response.errorBody().string(), ErrorResponseDto.class);
        assertThat(errorResponse.getStatus(), equalTo(400));
        assertThat(errorResponse.getMessage(), equalTo("Id must be null for new entity"));
        time = errorResponse.getTimestamp().getEpochSecond() - (Instant.now().getEpochSecond());
        assertThat(Math.abs(time) < 60L, is(true));
    }

    /* Тест упадёт т.к. сервис создаёт продукт без категории. */
    @Disabled
    @SneakyThrows
    @Test
    void createProductWithoutCategoryTitleNegativeTest() {
        ProductDto product = createProductDto(category.getTitle());
        product.setCategoryTitle(null);
        Response<ProductDto> response = requests.createProduct(product);
        assertThat(response.code(), equalTo(400));
        assertThat(response.errorBody(), is(notNullValue()));
        ErrorResponseDto errorResponse = objectMapper.readValue(response.errorBody().string(), ErrorResponseDto.class);
        assertThat(errorResponse.getStatus(), equalTo(400));
        assertThat(errorResponse.getMessage(), equalTo("CategoryTitle must be null for new entity"));
        time = errorResponse.getTimestamp().getEpochSecond() - (Instant.now().getEpochSecond());
        assertThat(Math.abs(time) < 60L, is(true));
    }

    /* Тест упадёт т.к. сервис создаёт продукт без цены. */
    @Disabled
    @SneakyThrows
    @Test
    void createProductWithoutPriceNegativeTest() {
        ProductDto product = createProductDto(category.getTitle());
        product.setPrice(null);
        Response<ProductDto> response = requests.createProduct(product);
        assertThat(response.code(), equalTo(400));
        assertThat(response.errorBody(), is(notNullValue()));
        ErrorResponseDto errorResponse = objectMapper.readValue(response.errorBody().string(), ErrorResponseDto.class);
        assertThat(errorResponse.getStatus(), equalTo(400));
        assertThat(errorResponse.getMessage(), equalTo("Price must be null for new entity"));
        time = errorResponse.getTimestamp().getEpochSecond() - (Instant.now().getEpochSecond());
        assertThat(Math.abs(time) < 60L, is(true));
    }

    /* Тест упадёт т.к. сервис создаёт продукт с отрицательной ценой. */
    @Disabled
    @SneakyThrows
    @Test
    void createNegativePriceProductNegativeTest() {
        ProductDto product = createProductDto(category.getTitle());
        product.setPrice((int) (-1000 + (Math.random() * 999)));
        Response<ProductDto> response = requests.createProduct(product);
        assertThat(response.code(), equalTo(400));
        assertThat(response.errorBody(), is(notNullValue()));
        ErrorResponseDto errorResponse = objectMapper.readValue(response.errorBody().string(), ErrorResponseDto.class);
        assertThat(errorResponse.getStatus(), equalTo(400));
        assertThat(errorResponse.getMessage(), equalTo("The price cannot be negative"));
        time = errorResponse.getTimestamp().getEpochSecond() - (Instant.now().getEpochSecond());
        assertThat(Math.abs(time) < 60L, is(true));
    }

    @SneakyThrows
    @Test
    void modifyProductNegativeTest() {
        Response<ProductDto> response = requests.modifyProduct(new ProductDto());
        assertThat(response.code(), equalTo(400));
        assertThat(response.errorBody(), is(notNullValue()));
        ErrorResponseDto errorResponse = objectMapper.readValue(response.errorBody().string(), ErrorResponseDto.class);
        assertThat(errorResponse.getStatus(), equalTo(400));
        assertThat(errorResponse.getMessage(), equalTo("Id must be not null for new entity"));
        time = errorResponse.getTimestamp().getEpochSecond() - (Instant.now().getEpochSecond());
        assertThat(Math.abs(time) < 60L, is(true));
    }

    @ValueSource(ints = {-42, -1, 0, 9999999})
    @SneakyThrows
    @ParameterizedTest
    void getProductNegativeTest(int id) {
        Response<ProductDto> response = requests.getProduct(id);
        assertThat(response.code(), equalTo(404));
        assertThat(response.errorBody(), is(notNullValue()));
        ErrorResponseDto errorResponse = objectMapper.readValue(response.errorBody().string(), ErrorResponseDto.class);
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
        ErrorResponseDto errorResponse = objectMapper.readValue(response.errorBody().string(), ErrorResponseDto.class);
        assertThat(errorResponse.getStatus(), equalTo(500));
        assertThat(errorResponse.getMessage(), equalTo(""));
        assertThat(errorResponse.getError(), equalTo("Internal Server Error"));
        assertThat(errorResponse.getPath(), equalTo("/market/api/v1/products/" + id));
        time = errorResponse.getTimestamp().getEpochSecond() - (Instant.now().getEpochSecond());
        assertThat(Math.abs(time) < 60L, is(true));
    }
}
