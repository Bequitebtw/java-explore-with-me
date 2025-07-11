package ru.practicum.ewm.service.service;

import ru.practicum.ewm.service.model.Category;

import java.util.List;

public interface CategoryService {
    Category save(Category category);

    void delete(Integer id);

    Category edit(Integer id, Category category);

    List<Category> findCategories(Integer from, Integer size);

    Category findCategoryById(Integer id);
}
