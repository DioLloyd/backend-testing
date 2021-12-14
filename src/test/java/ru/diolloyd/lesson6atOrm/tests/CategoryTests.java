package ru.diolloyd.lesson6atOrm.tests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import retrofit2.Response;
import ru.diolloyd.lesson6atOrm.db.model.Category;
import ru.diolloyd.lesson6atOrm.dto.CategoryDto;
import ru.diolloyd.lesson6atOrm.dto.ErrorResponseDto;
import ru.diolloyd.lesson6atOrm.services.CategoryService;
import ru.diolloyd.lesson6atOrm.utils.RetrofitUtils;

import java.time.Instant;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static ru.diolloyd.lesson6atOrm.dto.ProductDto.faker;
import static ru.diolloyd.lesson6atOrm.utils.CategoryDbRequests.createCategoryInDb;
import static ru.diolloyd.lesson6atOrm.utils.CategoryDbRequests.deleteCategoryFromDb;

public class CategoryTests {
    private static CategoryService categoryService;

    @BeforeAll
    static void BeforeAll() {
        categoryService = RetrofitUtils.getRetrofit().create(CategoryService.class);
    }

    @SneakyThrows
    @Test
    void getCategoryPositiveTest() {
        Category category = new Category();
        category.setTitle(faker.animal().name());
        createCategoryInDb(category);
        Response<CategoryDto> response = categoryService.getCategoryById(category.getId()).execute();
        assertThat(response.isSuccessful(), is(true));
        assertThat(response.body(), notNullValue());
        assertThat(response.body().getId(), equalTo(category.getId()));
        assertThat(response.body().getTitle(), equalTo(category.getTitle()));
        deleteCategoryFromDb(category.getId());
    }

    @SneakyThrows
    @Test
    void getCategoryNegativeTest() {
        int nonExistentId = (int) (Math.random() * (100000 - 100)) + 100;
        Response<CategoryDto> response = categoryService.getCategoryById(nonExistentId).execute();
        assertThat(response.code(), equalTo(404));
        assertThat(response.errorBody(), is(notNullValue()));
        ErrorResponseDto errorResponse = new ObjectMapper().registerModule(new JavaTimeModule()).readValue(response.errorBody().string(), ErrorResponseDto.class);
        assertThat(errorResponse.getStatus(), equalTo(404));
        assertThat(errorResponse.getMessage(), equalTo("Unable to find category with id: " + nonExistentId));
        long time = errorResponse.getTimestamp().getEpochSecond() - (Instant.now().getEpochSecond());
        assertThat(Math.abs(time) < 60L, is(true));
    }
}
