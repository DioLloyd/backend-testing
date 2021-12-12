package ru.diolloyd.lesson6atOrm.utils;

import lombok.SneakyThrows;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import ru.diolloyd.lesson6atOrm.db.dao.CategoryMapper;
import ru.diolloyd.lesson6atOrm.db.dao.ProductMapper;

import java.io.InputStream;

public class DbUtils {
    private final static String resource = "mybatisConfig.xml";

    @SneakyThrows
    private static SqlSession getSqlSession() {
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        return sqlSessionFactory.openSession(true);
    }

    public static CategoryMapper getCategoryMapper() {
        return getSqlSession().getMapper(CategoryMapper.class);
    }

    public static ProductMapper getProductMapper() {
        return getSqlSession().getMapper(ProductMapper.class);
    }
}
