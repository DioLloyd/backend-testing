package ru.diolloyd.lesson6atOrm.services;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import ru.diolloyd.lesson6atOrm.dto.CategoryDto;

public interface CategoryService {

    @GET("categories/{id}")
    Call<CategoryDto> getCategoryById(@Path("id") Integer id);
}
