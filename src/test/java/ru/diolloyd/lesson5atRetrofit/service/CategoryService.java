package ru.diolloyd.lesson5atRetrofit.service;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import ru.diolloyd.lesson5atRetrofit.dto.GetCategoryResponse;

public interface CategoryService {

    @GET("categories/{id}")
    Call<GetCategoryResponse> getCategory(@Path("id") Integer id);
}
