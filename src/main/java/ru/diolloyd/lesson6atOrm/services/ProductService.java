package ru.diolloyd.lesson6atOrm.services;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.*;
import ru.diolloyd.lesson6atOrm.dto.ProductDto;

import java.util.ArrayList;

public interface ProductService {

    @GET("products")
    Call<ArrayList<ProductDto>> getProducts();

    @POST("products")
    Call<ProductDto> createProduct(@Body ProductDto product);

    @PUT("products")
    Call<ProductDto> modifyProduct(@Body ProductDto product);

    @GET("products/{id}")
    Call<ProductDto> getProduct(@Path("id") Integer id);

    @DELETE("products/{id}")
    Call<ResponseBody> deleteProduct(@Path("id") Integer id);
}
