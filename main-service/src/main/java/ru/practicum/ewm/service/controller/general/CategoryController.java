package ru.practicum.ewm.service.controller.general;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.client.StatsClient;
import ru.practicum.ewm.dto.RequestUtils;
import ru.practicum.ewm.service.model.Category;
import ru.practicum.ewm.service.service.*;

import java.util.List;

@RestController("publicCategoryController")
@RequiredArgsConstructor
@RequestMapping("/categories")
public class CategoryController {
    private final StatsClient statsClient;
    private final RequestUtils requestUtils;
    private final CategoryService categoryService;

    @GetMapping
    public List<Category> findCategories(@RequestParam(defaultValue = "0") Integer from,
                                         @RequestParam(defaultValue = "10") Integer size,
                                         HttpServletRequest request) {
        statsClient.saveHit(requestUtils.createHit(request));
        return categoryService.findCategories(from, size);
    }

    @GetMapping("/{id}")
    public Category findCategoryById(@PathVariable Integer id) {
        return categoryService.findCategoryById(id);
    }

}
