package com.example.demo.service;

import com.example.demo.dto.RecipeCreateDto;
import com.example.demo.dto.RecipeUpdateDto;
import com.example.demo.exception.RecipeNotFoundException;
import com.example.demo.mapper.RecipeMapper;
import com.example.demo.model.CategoryEntity;
import com.example.demo.model.Ingredient;
import com.example.demo.model.Recipe;
import com.example.demo.repository.IngredientRepository;
import com.example.demo.repository.RecipeRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
public class RecipeService {

    private final RecipeRepository recipeRepository;
    private final CategoryService categoryService;
    private final IngredientRepository ingredientRepository;

    public RecipeService(RecipeRepository recipeRepository,
                         CategoryService categoryService,
                         IngredientRepository ingredientRepository) {
        this.recipeRepository = recipeRepository;
        this.categoryService = categoryService;
        this.ingredientRepository = ingredientRepository;
    }

    // CREATE (me DTO + categoryId + ingredientIds)
    public Recipe create(RecipeCreateDto dto) {
        CategoryEntity category = categoryService.getById(dto.getCategoryId());

        Recipe r = RecipeMapper.toEntity(dto);
        r.setCategory(category);


        if (dto.getIngredientIds() != null && !dto.getIngredientIds().isEmpty()) {
            List<Ingredient> ingredients = ingredientRepository.findAllById(dto.getIngredientIds());
            r.setIngredientEntities(new HashSet<>(ingredients));
        }

        return recipeRepository.save(r);
    }

    public List<Recipe> getAll() {
        return recipeRepository.findAll();
    }

    // GET ALL me sorting
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

    // UPDATE (me DTO + categoryId + ingredientIds)
    public Recipe update(Long id, RecipeUpdateDto dto) {
        Recipe existing = getById(id);

        RecipeMapper.updateEntity(existing, dto);

        CategoryEntity category = categoryService.getById(dto.getCategoryId());
        existing.setCategory(category);


        if (dto.getIngredientIds() != null) {
            List<Ingredient> ingredients = ingredientRepository.findAllById(dto.getIngredientIds());
            existing.setIngredientEntities(new HashSet<>(ingredients));
        }

        return recipeRepository.save(existing);
    }

    public void delete(Long id) {
        Recipe existing = getById(id);
        recipeRepository.delete(existing);
    }

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
