package ru.diolloyd.lesson5atRetrofit.services;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import ru.diolloyd.lesson5atRetrofit.dto.Category;

public interface CategoryService {

    @GET("categories/{id}")
    Call<Category> getCategoryById(@Path("id") Integer id);
}
