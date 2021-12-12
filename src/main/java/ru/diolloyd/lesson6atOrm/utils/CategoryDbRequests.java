package ru.diolloyd.lesson6atOrm.utils;

import ru.diolloyd.lesson6atOrm.db.dao.CategoryMapper;
import ru.diolloyd.lesson6atOrm.db.model.Category;
import ru.diolloyd.lesson6atOrm.db.model.CategoryExample;

import java.util.List;

public class CategoryDbRequests {

    private static final CategoryMapper mapper = DbUtils.getCategoryMapper();

    public static List<Category> getCategories() {
        return mapper.selectByExample(new CategoryExample());
    }

    public static void createCategory(Category category) {
        mapper.insert(category);
    }

    public static void modifyCategory(Category category) {
        mapper.updateByPrimaryKey(category);
    }

    public static void deleteCategory(int id) {
        mapper.deleteByPrimaryKey(id);
    }

}
