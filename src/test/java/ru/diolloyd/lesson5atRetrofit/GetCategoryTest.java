package ru.diolloyd.lesson5atRetrofit;

import lombok.SneakyThrows;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import retrofit2.Response;
import ru.diolloyd.lesson5atRetrofit.dto.GetCategoryResponse;
import ru.diolloyd.lesson5atRetrofit.service.CategoryService;
import ru.diolloyd.lesson5atRetrofit.utils.RetrofitUtils;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class GetCategoryTest {
    private static CategoryService categoryService;

    @BeforeAll
    static void BeforeAll() {
        categoryService = RetrofitUtils.getRetrofit().create(CategoryService.class);
    }

    @SneakyThrows
    @Test
    public void getCategoryByIdPositiveTest() {
        Response<GetCategoryResponse> response = categoryService.getCategory(1).execute();
        assertThat(response.isSuccessful(), is(true));
        assertThat(response.body().getId(), equalTo(1));
        assertThat(response.body().getTitle(), equalTo(1));
        response.body().getProducts().forEach(
                product -> assertThat(product.getCategoryTitle(), equalTo("Food"))
        );
    }
}
