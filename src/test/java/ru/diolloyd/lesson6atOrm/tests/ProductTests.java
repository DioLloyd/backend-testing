package ru.diolloyd.lesson6atOrm.tests;

import okhttp3.ResponseBody;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import retrofit2.Response;
import ru.diolloyd.lesson6atOrm.dto.ProductDto;
import ru.diolloyd.lesson6atOrm.enums.CategoryType;
import ru.diolloyd.lesson6atOrm.services.ProductService;
import ru.diolloyd.lesson6atOrm.utils.ProductServiceRequests;
import ru.diolloyd.lesson6atOrm.utils.RetrofitUtils;

import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static ru.diolloyd.lesson6atOrm.dto.ProductDto.createProductByType;
import static ru.diolloyd.lesson6atOrm.dto.ProductDto.modifyProductByType;

public class ProductTests {

    private final ProductService productService = RetrofitUtils.getRetrofit().create(ProductService.class);
    private final ProductServiceRequests requests = new ProductServiceRequests(productService);

    @Test
    void getProductsTest() {
        Response<ArrayList<ProductDto>> products = requests.getProducts();
        assertThat(products.body(), is(notNullValue()));
        assertThat(products.body().get(0).getId(), is(notNullValue()));
        assertThat(products.body().get(0).getTitle(), is(notNullValue()));
        assertThat(products.body().get(0).getPrice(), is(notNullValue()));
        assertThat(products.body().get(0).getCategoryTitle(), is(notNullValue()));
    }

    @ParameterizedTest
    @EnumSource(CategoryType.class)
    void createProductTest(CategoryType type) {
        Response<ProductDto> response = requests.createProduct(createProductByType(type));
        assertThat(response.isSuccessful(), is(true));
        assertThat(response.body(), is(notNullValue()));
        deleteRequest(response.body().getId());
    }

    @ParameterizedTest
    @EnumSource(CategoryType.class)
    void modifyProductTest(CategoryType type) {
        Response<ProductDto> createResponse = requests.createProduct(createProductByType(type));
        assertThat(createResponse.isSuccessful(), is(true));
        assertThat(createResponse.body(), is(notNullValue()));
        Response<ProductDto> modifyResponse = requests.modifyProduct(
                modifyProductByType(createResponse.body(), type)
        );
        assertThat(modifyResponse.isSuccessful(), is(true));
        assertThat(modifyResponse.body(), is(notNullValue()));
        assertThat(createResponse.body(), equalTo(modifyResponse.body()));
        deleteRequest(createResponse.body().getId());
    }

    @ParameterizedTest
    @EnumSource(CategoryType.class)
    void getProductTest(CategoryType type) {
        Response<ProductDto> createResponse = requests.createProduct(createProductByType(type));
        assertThat(createResponse.isSuccessful(), is(true));
        assertThat(createResponse.body(), is(notNullValue()));
        Response<ProductDto> getResponse = requests.getProduct(createResponse.body().getId());
        assertThat(getResponse.isSuccessful(), is(true));
        assertThat(getResponse.body(), is(notNullValue()));
        assertThat(createResponse.body(), equalTo(getResponse.body()));
        deleteRequest(createResponse.body().getId());
    }

    @ParameterizedTest
    @EnumSource(CategoryType.class)
    void createNoPriceNoTitleProductTest(CategoryType type) {
        Response<ProductDto> response = requests.createProduct(
                createProductByType(type)
                        .setPrice(null)
                        .setTitle(null)
        );
        assertThat(response.isSuccessful(), is(true));
        assertThat(response.body(), is(notNullValue()));
        deleteRequest(response.body().getId());
    }

    private void deleteRequest(Integer id) {
        Response<ResponseBody> deleteResponse = requests.deleteProduct(id);
        assertThat(deleteResponse.isSuccessful(), is(true));
    }
}
