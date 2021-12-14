package ru.diolloyd.lesson6atOrm.utils;

import ru.diolloyd.lesson6atOrm.db.dao.ProductMapper;
import ru.diolloyd.lesson6atOrm.db.model.Product;
import ru.diolloyd.lesson6atOrm.db.model.ProductExample;

import java.util.List;

public class ProductDbRequests {

    private static final ProductMapper mapper = DbUtils.getProductMapper();

    public static List<Product> getProductsFromDb() {
        return mapper.selectByExample(new ProductExample());
    }

    public static void addProductInDb(Product product) {
        mapper.insert(product);
    }

    public static void modifyProductInDb(Product product) {
        mapper.updateByPrimaryKey(product);
    }

    public static Product getProductFromDb(Long id) {
        return mapper.selectByPrimaryKey(id);
    }

    public static void deleteProductFromDb(Long id) {
        mapper.deleteByPrimaryKey(id);
    }

}
