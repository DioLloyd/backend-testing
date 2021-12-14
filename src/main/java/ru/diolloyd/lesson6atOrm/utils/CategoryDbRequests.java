package ru.diolloyd.lesson6atOrm.utils;

import ru.diolloyd.lesson6atOrm.db.dao.CategoryMapper;
import ru.diolloyd.lesson6atOrm.db.model.Category;
import ru.diolloyd.lesson6atOrm.db.model.CategoryExample;

import java.util.List;

public class CategoryDbRequests {

    private static final CategoryMapper mapper = DbUtils.getCategoryMapper();

    public static List<Category> getCategoriesFromDb() {
        return mapper.selectByExample(new CategoryExample());
    }

    public static void createCategoryInDb(Category category) {
        mapper.insert(category);
    }

    public static void modifyCategoryInDb(Category category) {
        mapper.updateByPrimaryKey(category);
    }

    public static Category getCategoryFromDb(int id) {
        return mapper.selectByPrimaryKey(id);
    }

    public static void deleteCategoryFromDb(int id) {
        mapper.deleteByPrimaryKey(id);
    }

}
