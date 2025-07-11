package ru.practicum.ewm.service.controller.admin;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.client.StatsClient;
import ru.practicum.ewm.dto.RequestUtils;
import ru.practicum.ewm.service.model.Category;
import ru.practicum.ewm.service.service.*;

@RestController("adminCategoryController")
@RequiredArgsConstructor
@RequestMapping("/admin")
public class CategoryController {
    private final RequestUtils requestUtils;
    private final StatsClient statsClient;
    private final CategoryService categoryService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/categories")
    public Category createCategory(@Valid @RequestBody Category category, HttpServletRequest request) {
        statsClient.saveHit(requestUtils.createHit(request));
        return categoryService.save(category);
    }

    @PatchMapping("/categories/{id}")
    public Category editCategory(@Valid @RequestBody Category category, @PathVariable Integer id, HttpServletRequest request) {
        statsClient.saveHit(requestUtils.createHit(request));
        return categoryService.edit(id, category);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/categories/{id}")
    public void deleteCategory(@PathVariable Integer id, HttpServletRequest request) {
        statsClient.saveHit(requestUtils.createHit(request));
        categoryService.delete(id);
    }
}
