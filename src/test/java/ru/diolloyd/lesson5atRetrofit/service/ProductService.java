package ru.diolloyd.lesson5atRetrofit.service;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.*;
import ru.diolloyd.lesson5atRetrofit.dto.Product;

import java.util.ArrayList;

public interface ProductService {

    @GET("products")
    Call<ArrayList<Product>> getProducts();

    @POST("products")
    Call<Product> createProduct(@Body Product product);

    @DELETE("products/{id}")
    Call<ResponseBody> deleteProduct(@Path("id") Integer id);
}
