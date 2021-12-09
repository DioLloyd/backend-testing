package ru.diolloyd.lesson6atOrm.utils;

import lombok.SneakyThrows;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import ru.diolloyd.lesson6atOrm.db.dao.CategoryMapper;
import ru.diolloyd.lesson6atOrm.db.dao.ProductMapper;
import ru.diolloyd.lesson6atOrm.db.model.Category;
import ru.diolloyd.lesson6atOrm.db.model.CategoryExample;

import java.io.InputStream;
import java.util.List;

public class DbUtils {
    private final static String resource = "mybatisConfig.xml";

    @SneakyThrows
    private static SqlSession getSqlSession() {
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        return sqlSessionFactory.openSession(true);
    }

    private static CategoryMapper getCategoryMapper() {
        return getSqlSession().getMapper(CategoryMapper.class);
    }

    private static ProductMapper getProductMapper() {
        return getSqlSession().getMapper(ProductMapper.class);
    }

    public static List<Category> getCategories() {
        return getCategoryMapper().selectByExample(new CategoryExample());
    }

    public static void createCategory(Category category) {
        getCategoryMapper().insert(category);
    }

    public static void deleteCategory(Category category) {
        getCategoryMapper().deleteByPrimaryKey(category.getId());
    }

    public static void modifyCategory(Category category) {
        getCategoryMapper().updateByPrimaryKey(category);
    }
}
