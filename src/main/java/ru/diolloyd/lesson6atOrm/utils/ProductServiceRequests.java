package ru.diolloyd.lesson6atOrm.utils;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import okhttp3.ResponseBody;
import retrofit2.Response;
import ru.diolloyd.lesson6atOrm.dto.ProductDto;
import ru.diolloyd.lesson6atOrm.services.ProductService;

import java.util.ArrayList;

@AllArgsConstructor
public class ProductServiceRequests {

    private ProductService productService;

    @SneakyThrows
    public Response<ArrayList<ProductDto>> getProducts() {
        return productService.getProducts().execute();
    }

    @SneakyThrows
    public Response<ProductDto> createProduct(ProductDto product) {
        return productService.createProduct(product).execute();
    }

    @SneakyThrows
    public Response<ProductDto> modifyProduct(ProductDto product) {
        return productService.modifyProduct(product).execute();
    }

    @SneakyThrows
    public Response<ProductDto> getProduct(Integer id) {
        return productService.getProduct(id).execute();
    }

    @SneakyThrows
    public Response<ResponseBody> deleteProduct(Integer id) {
        return productService.deleteProduct(id).execute();
    }
}
