package ru.practicum.ewm.service.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.service.exception.CategoryNotFoundException;
import ru.practicum.ewm.service.model.Category;
import ru.practicum.ewm.service.repository.CategoryRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Transactional
    @Override
    public Category save(Category category) {
        return categoryRepository.save(category);
    }


    @Transactional
    @Override
    public void delete(Integer id) {
        categoryRepository.findById(id).orElseThrow(() -> new CategoryNotFoundException(id));
        categoryRepository.deleteById(id);
    }

    @Transactional
    @Override
    public Category edit(Integer id, Category category) {
        categoryRepository.findById(id).orElseThrow(() -> new CategoryNotFoundException(id));
        category.setId(id);
        return categoryRepository.save(category);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Category> findCategories(Integer from, Integer size) {
        return categoryRepository.findAll(PageRequest.of(from / size, size)).stream().toList();
    }

    @Transactional(readOnly = true)
    @Override
    public Category findCategoryById(Integer id) {
        return categoryRepository.findById(id).orElseThrow(() -> new CategoryNotFoundException(id));
    }
}
