package com.example.demo.service;

import com.example.demo.exception.RecipeNotFoundException;
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

    @InjectMocks
    private RecipeService recipeService;

    @Test
    void getById_shouldReturnRecipe_whenExists() {
        Recipe recipe = new Recipe();
        recipe.setId(1L);
        recipe.setName("Pancakes");

        when(recipeRepository.findById(1L))
                .thenReturn(Optional.of(recipe));

        Recipe result = recipeService.getById(1L);

        assertNotNull(result);
        assertEquals("Pancakes", result.getName());
    }

    @Test
    void getById_shouldThrowException_whenNotExists() {
        when(recipeRepository.findById(99L))
                .thenReturn(Optional.empty());

        assertThrows(
                RecipeNotFoundException.class,
                () -> recipeService.getById(99L)
        );
    }
}
