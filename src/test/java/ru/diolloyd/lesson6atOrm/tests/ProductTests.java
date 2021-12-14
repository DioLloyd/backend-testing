package ru.diolloyd.lesson6atOrm.tests;

import okhttp3.ResponseBody;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import retrofit2.Response;
import ru.diolloyd.lesson6atOrm.db.model.Category;
import ru.diolloyd.lesson6atOrm.db.model.Product;
import ru.diolloyd.lesson6atOrm.dto.ProductDto;
import ru.diolloyd.lesson6atOrm.services.ProductService;
import ru.diolloyd.lesson6atOrm.utils.CategoryDbRequests;
import ru.diolloyd.lesson6atOrm.utils.ProductDbRequests;
import ru.diolloyd.lesson6atOrm.utils.ProductServiceRequests;
import ru.diolloyd.lesson6atOrm.utils.RetrofitUtils;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static ru.diolloyd.lesson6atOrm.dto.ProductDto.createProductDto;
import static ru.diolloyd.lesson6atOrm.dto.ProductDto.faker;

public class ProductTests {

    private final ProductService productService = RetrofitUtils.getRetrofit().create(ProductService.class);
    private final ProductServiceRequests requests = new ProductServiceRequests(productService);

    private Category category = new Category();

    @BeforeEach
    void createCategoryInDb() {
        category.setTitle(faker.aviation().aircraft());
        CategoryDbRequests.createCategoryInDb(category);
    }

    @AfterEach
    void deleteCategoryFromDb() {
        CategoryDbRequests.deleteCategoryFromDb(category.getId());
    }

    @Test
    void getProductsTest() {
        Product productModel = createProductModel(category);
        ProductDbRequests.addProductInDb(productModel);
        List<Product> productsFromDb = ProductDbRequests.getProductsFromDb();

        Response<ArrayList<ProductDto>> response = requests.getProducts();
        assertThat(response.body(), is(notNullValue()));
        assertThat(response.body().size(), equalTo(productsFromDb.size()));

        ProductDto productDto = response.body().stream()
                .filter(productStream -> productStream.getId().equals(productModel.getId().intValue()))
                .findFirst()
                .get();

        assertThat(productDto.getId(), equalTo(productModel.getId()));
        assertThat(productDto.getTitle(), equalTo(productModel.getTitle()));
        assertThat(productDto.getPrice(), equalTo(productModel.getPrice()));
        assertThat(productDto.getCategoryTitle(), equalTo(category.getTitle()));

        ProductDbRequests.deleteProductFromDb(productModel.getId());
    }

    @Test
    void createProductTest() {
        ProductDto product = createProductDto(category.getTitle());

        Response<ProductDto> response = requests.createProduct(product);
        assertThat(response.isSuccessful(), is(true));
        assertThat(response.body(), is(notNullValue()));
        assertThat(response.body().getId(), is(notNullValue()));
        assertThat(response.body().getTitle(), equalTo(product.getTitle()));
        assertThat(response.body().getPrice(), equalTo(product.getPrice()));
        assertThat(response.body().getCategoryTitle(), equalTo(product.getCategoryTitle()));

        ProductDbRequests.deleteProductFromDb(response.body().getId().longValue());
    }

    @Test
    void modifyProductTest() {
        Product productModel = createProductModel(category);
        ProductDbRequests.addProductInDb(productModel);
        int categoryId = category.getId();

        category.setTitle(faker.aviation().aircraft());
        CategoryDbRequests.createCategoryInDb(category);

        ProductDto productDto = createProductDto(category.getTitle())
                .setId(productModel.getId().intValue());

        Response<ProductDto> response = requests.modifyProduct(productDto);
        assertThat(response.isSuccessful(), is(true));
        assertThat(response.body(), is(notNullValue()));
        assertThat(response.body().getId(), equalTo(productDto.getId()));
        assertThat(response.body().getTitle(), equalTo(productDto.getTitle()));
        assertThat(response.body().getPrice(), equalTo(productDto.getPrice()));
        assertThat(response.body().getCategoryTitle(), equalTo(productDto.getCategoryTitle()));

        ProductDbRequests.deleteProductFromDb(response.body().getId().longValue());
        CategoryDbRequests.deleteCategoryFromDb(categoryId);
    }

    @Test
    void getProductTest() {
        Product productModel = createProductModel(category);
        ProductDbRequests.addProductInDb(productModel);

        Response<ProductDto> response = requests.getProduct(productModel.getId().intValue());
        assertThat(response.isSuccessful(), is(true));
        assertThat(response.body(), is(notNullValue()));
        assertThat(response.body().getId(), equalTo(productModel.getId().intValue()));
        assertThat(response.body().getTitle(), equalTo(productModel.getTitle()));
        assertThat(response.body().getPrice(), equalTo(productModel.getPrice()));
        assertThat(
                response.body().getCategoryTitle(),
                equalTo(CategoryDbRequests.getCategoryFromDb(category.getId()).getTitle())
        );

        ProductDbRequests.deleteProductFromDb(response.body().getId().longValue());
    }

    @Test
    void deleteProductTest() {
        Product productModel = createProductModel(category);
        ProductDbRequests.addProductInDb(productModel);

        Response<ResponseBody> response = requests.deleteProduct(productModel.getId().intValue());
        assertThat(response.isSuccessful(), is(true));
        List<Product> products = ProductDbRequests.getProductsFromDb();
        assertThat(
                products.stream()
                        .noneMatch(productStream -> productStream.getId().equals(productModel.getId())),
                is(true)
        );
    }

    @Test
    void createNoPriceNoTitleProductTest() {
        Response<ProductDto> response = requests.createProduct(new ProductDto().setCategoryTitle(category.getTitle()));
        assertThat(response.isSuccessful(), is(true));
        assertThat(response.body(), is(notNullValue()));

        Product product = ProductDbRequests.getProductFromDb(response.body().getId().longValue());

        assertThat(response.body().getId(), equalTo(product.getId().intValue()));
        assertThat(response.body().getTitle(), equalTo(product.getTitle()));
        assertThat(response.body().getPrice(), equalTo(product.getPrice()));
        assertThat(response.body().getCategoryTitle(), equalTo(category.getTitle()));

        ProductDbRequests.deleteProductFromDb(response.body().getId().longValue());
    }

    private Product createProductModel(Category category) {
        Product product = new Product();
        product.setTitle(faker.color().name() + " " + category.getTitle());
        product.setPrice((int) (Math.random() * (10000 - 1000 + 1) + 1000));
        product.setCategory_id(category.getId().longValue());
        return product;
    }
}
