package ru.diolloyd.lesson6atOrm.tests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import retrofit2.Response;
import ru.diolloyd.lesson6atOrm.db.model.Category;
import ru.diolloyd.lesson6atOrm.dto.CategoryDto;
import ru.diolloyd.lesson6atOrm.dto.ErrorResponseDto;
import ru.diolloyd.lesson6atOrm.enums.CategoryType;
import ru.diolloyd.lesson6atOrm.services.CategoryService;
import ru.diolloyd.lesson6atOrm.utils.RetrofitUtils;

import java.time.Instant;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static ru.diolloyd.lesson6atOrm.dto.ProductDto.faker;
import static ru.diolloyd.lesson6atOrm.utils.DbUtils.*;

public class CategoryTests {
    private static CategoryService categoryService;

    @BeforeAll
    static void BeforeAll() {
        categoryService = RetrofitUtils.getRetrofit().create(CategoryService.class);
    }

    @EnumSource(CategoryType.class)
    @SneakyThrows
    @ParameterizedTest
    void getCategoryByIdPositiveTest(CategoryType type) {
        Response<CategoryDto> response = categoryService.getCategoryById(type.getId()).execute();
        assertThat(response.isSuccessful(), is(true));
        assertThat(response.body(), notNullValue());
        assertThat(response.body().getId(), equalTo(type.getId()));
        assertThat(response.body().getTitle(), equalTo(type.getTitle()));
        response.body().getProducts().forEach(
                product -> assertThat(product.getCategoryTitle(), equalTo(type.getTitle()))
        );
    }

    @Test
    @SneakyThrows
    void getCategoryByIdNegativeTest() {
        int nonExistentId = (int) (Math.random() * (1000 - 100)) + 100;
        Response<CategoryDto> response = categoryService.getCategoryById(nonExistentId).execute();
        assertThat(response.code(), equalTo(404));
        assertThat(response.errorBody(), is(notNullValue()));
        ErrorResponseDto errorResponse = new ObjectMapper().registerModule(new JavaTimeModule()).readValue(response.errorBody().string(), ErrorResponseDto.class);
        assertThat(errorResponse.getStatus(), equalTo(404));
        assertThat(errorResponse.getMessage(), equalTo("Unable to find category with id: " + nonExistentId));
        long time = errorResponse.getTimestamp().getEpochSecond() - (Instant.now().getEpochSecond());
        assertThat(Math.abs(time) < 60L, is(true));
    }

    @Test
    void testMethod() {
        Category category = new Category();
        category.setTitle(faker.animal().name());
        createCategory(category);
        category.setTitle(faker.animal().name());
        modifyCategory(category);
//        Category categoryFromDb = DbUtils.getCategories().stream()
//                .filter(searchCategory -> searchCategory.getTitle().equals(category.getTitle()))
//                .findFirst()
//                .get();
//        assertThat(category.getTitle(), equalTo(categoryFromDb.getTitle()));
//        deleteCategoryById(categoryFromDb.getId());
        deleteCategory(category);
    }
}
