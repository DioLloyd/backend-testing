package ru.diolloyd.lesson5atRetrofit.utils;

import io.qameta.allure.Step;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import okhttp3.ResponseBody;
import retrofit2.Response;
import ru.diolloyd.lesson5atRetrofit.dto.Product;
import ru.diolloyd.lesson5atRetrofit.services.ProductService;

import java.util.ArrayList;

@AllArgsConstructor
public class ProductServiceRequests {

    private ProductService productService;

    @Step("Получить список продуктов")
    @SneakyThrows
    public Response<ArrayList<Product>> getProducts() {
        return productService.getProducts().execute();
    }

    @Step("Создание продукта")
    @SneakyThrows
    public Response<Product> createProduct(Product product) {
        return productService.createProduct(product).execute();
    }

    @Step("Изменение продукта")
    @SneakyThrows
    public Response<Product> modifyProduct(Product product) {
        return productService.modifyProduct(product).execute();
    }

    @Step("Получение продукта")
    @SneakyThrows
    public Response<Product> getProduct(Integer id) {
        return productService.getProduct(id).execute();
    }

    @Step("Удаление продукта")
    @SneakyThrows
    public Response<ResponseBody> deleteProduct(Integer id) {
        return productService.deleteProduct(id).execute();
    }
}
