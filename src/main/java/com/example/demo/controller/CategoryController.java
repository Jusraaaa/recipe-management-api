package com.example.demo.controller;

import com.example.demo.model.CategoryEntity;
import com.example.demo.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryEntity create(@Valid @RequestBody CategoryEntity category) {
        return categoryService.create(category);
    }

    @GetMapping
    public List<CategoryEntity> getAll() {
        return categoryService.getAll();
    }

    @GetMapping("/{id}")
    public CategoryEntity getById(@PathVariable Long id) {
        return categoryService.getById(id);
    }

    @PutMapping("/{id}")
    public CategoryEntity update(@PathVariable Long id,
                                 @Valid @RequestBody CategoryEntity category) {
        return categoryService.update(id, category);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        categoryService.delete(id);
    }
}
