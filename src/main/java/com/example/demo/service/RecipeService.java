package com.example.demo.service;

import com.example.demo.dto.RecipeCreateDto;
import com.example.demo.dto.RecipeUpdateDto;
import com.example.demo.exception.RecipeNotFoundException;
import com.example.demo.mapper.RecipeMapper;
import com.example.demo.model.CategoryEntity;
import com.example.demo.model.Recipe;
import com.example.demo.repository.RecipeRepository;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Sort;

import java.util.List;

@Service
public class RecipeService {

    private final RecipeRepository recipeRepository;
    private final CategoryService categoryService;

    public RecipeService(RecipeRepository recipeRepository, CategoryService categoryService) {
        this.recipeRepository = recipeRepository;
        this.categoryService = categoryService;
    }

    // CREATE (me DTO + categoryId)
    public Recipe create(RecipeCreateDto dto) {
        CategoryEntity category = categoryService.getById(dto.getCategoryId());

        Recipe r = RecipeMapper.toEntity(dto);
        r.setCategory(category);

        return recipeRepository.save(r);
    }

    public List<Recipe> getAll() {
        return recipeRepository.findAll();
    }

    // ✅ NEW: GET ALL with sorting
    public List<Recipe> getAllSorted(String sortBy, String dir) {

        String sortField = (sortBy == null || sortBy.isBlank()) ? "id" : sortBy;
        String direction = (dir == null || dir.isBlank()) ? "asc" : dir;

        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortField).descending()
                : Sort.by(sortField).ascending();

        return recipeRepository.findAll(sort);
    }

    public Recipe getById(Long id) {
        return recipeRepository.findById(id)
                .orElseThrow(() -> new RecipeNotFoundException(id));
    }

    // UPDATE (me DTO + categoryId)
    public Recipe update(Long id, RecipeUpdateDto dto) {
        Recipe existing = getById(id);

        RecipeMapper.updateEntity(existing, dto);

        CategoryEntity category = categoryService.getById(dto.getCategoryId());
        existing.setCategory(category);

        return recipeRepository.save(existing);
    }

    public void delete(Long id) {
        Recipe existing = getById(id);
        recipeRepository.delete(existing);
    }

    // Filter me CategoryEntity (nëse e përdor diku)
    public List<Recipe> filterByCategory(CategoryEntity category) {
        return recipeRepository.findByCategory(category);
    }

    public List<Recipe> filterByCategoryId(Long categoryId) {
        CategoryEntity category = categoryService.getById(categoryId);
        return recipeRepository.findByCategory(category);
    }

    public List<Recipe> searchByName(String name) {
        return recipeRepository.findByNameContainingIgnoreCase(name);
    }
}
