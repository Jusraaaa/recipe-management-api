package com.example.demo.service;

import com.example.demo.dto.RecipeCreateDto;
import com.example.demo.dto.RecipeUpdateDto;
import com.example.demo.exception.RecipeNotFoundException;
import com.example.demo.model.CategoryEntity;
import com.example.demo.model.Recipe;
import com.example.demo.repository.RecipeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RecipeServiceTest {

    @Mock
    private RecipeRepository recipeRepository;

    @Mock
    private CategoryService categoryService;

    @InjectMocks
    private RecipeService recipeService;

    @Test
    void getById_shouldReturnRecipe_whenExists() {
        Recipe recipe = new Recipe();
        recipe.setId(1L);
        recipe.setName("Pancakes");

        when(recipeRepository.findById(1L)).thenReturn(Optional.of(recipe));

        Recipe result = recipeService.getById(1L);

        assertNotNull(result);
        assertEquals("Pancakes", result.getName());
    }

    @Test
    void getById_shouldThrowException_whenNotExists() {
        when(recipeRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RecipeNotFoundException.class, () -> recipeService.getById(99L));
    }

    @Test
    void create_shouldSaveRecipe_withCategory() {
        RecipeCreateDto dto = new RecipeCreateDto();
        dto.setName("Cake");
        dto.setIngredients("Flour, eggs");
        dto.setSteps("Mix and bake");
        dto.setPreparationTime(30);
        dto.setCategoryId(1L);

        CategoryEntity cat = new CategoryEntity();
        when(categoryService.getById(1L)).thenReturn(cat);


        when(recipeRepository.save(any(Recipe.class))).thenAnswer(inv -> inv.getArgument(0));

        Recipe saved = recipeService.create(dto);

        assertNotNull(saved);
        assertEquals("Cake", saved.getName());
        assertNotNull(saved.getCategory());
        verify(categoryService).getById(1L);
        verify(recipeRepository).save(any(Recipe.class));
    }

    @Test
    void update_shouldUpdateRecipe_andSave() {
        Recipe existing = new Recipe();
        existing.setId(1L);
        existing.setName("Old");

        when(recipeRepository.findById(1L)).thenReturn(Optional.of(existing));

        RecipeUpdateDto dto = new RecipeUpdateDto();
        dto.setName("New");
        dto.setIngredients("I");
        dto.setSteps("S");
        dto.setPreparationTime(10);
        dto.setCategoryId(2L);

        CategoryEntity cat = new CategoryEntity();
        when(categoryService.getById(2L)).thenReturn(cat);

        when(recipeRepository.save(any(Recipe.class))).thenAnswer(inv -> inv.getArgument(0));

        Recipe updated = recipeService.update(1L, dto);

        assertNotNull(updated);
        assertEquals("New", updated.getName());
        assertNotNull(updated.getCategory());
        verify(categoryService).getById(2L);
        verify(recipeRepository).save(existing);
    }
}
