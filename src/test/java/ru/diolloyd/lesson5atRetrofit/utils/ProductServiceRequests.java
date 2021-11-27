package ru.diolloyd.lesson5atRetrofit.utils;

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

    @SneakyThrows
    public Response<ArrayList<Product>> getProducts() {
        return productService.getProducts().execute();
    }

    @SneakyThrows
    public Response<Product> createProduct(Product product) {
        return productService.createProduct(product).execute();
    }

    @SneakyThrows
    public Response<Product> modifyProduct(Product product) {
        return productService.modifyProduct(product).execute();
    }

    @SneakyThrows
    public Response<Product> getProduct(Integer id) {
        return productService.getProduct(id).execute();
    }

    @SneakyThrows
    public Response<ResponseBody> deleteProduct(Integer id) {
        return productService.deleteProduct(id).execute();
    }
}
