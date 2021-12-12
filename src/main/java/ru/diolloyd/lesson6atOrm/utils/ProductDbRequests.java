package ru.diolloyd.lesson6atOrm.utils;

import ru.diolloyd.lesson6atOrm.db.dao.ProductMapper;
import ru.diolloyd.lesson6atOrm.db.model.Product;
import ru.diolloyd.lesson6atOrm.db.model.ProductExample;

import java.util.List;

public class ProductDbRequests {

    private static final ProductMapper mapper = DbUtils.getProductMapper();

    public static List<Product> getProductsDb() {
        return mapper.selectByExample(new ProductExample());
    }

    public static void createProductDb(Product product) {
        mapper.insert(product);
    }

    public static void modifyProductDb(Product product) {
        mapper.updateByPrimaryKey(product);
    }

    public static void getProductDb(Long id) {
        mapper.selectByPrimaryKey(id);
    }

    public static void deleteProductDb(Long id) {
        mapper.deleteByPrimaryKey(id);
    }

}
