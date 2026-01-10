package com.example.demo.mapper;

import com.example.demo.dto.RecipeCreateDto;
import com.example.demo.dto.RecipeUpdateDto;
import com.example.demo.dto.RecipeResponseDto;
import com.example.demo.model.Recipe;

public class RecipeMapper {

    private RecipeMapper() {}

    // CREATE DTO -> ENTITY (pa category)
    public static Recipe toEntity(RecipeCreateDto dto) {
        Recipe r = new Recipe();
        r.setName(dto.getName());
        r.setIngredients(dto.getIngredients());
        r.setSteps(dto.getSteps());
        r.setPreparationTime(dto.getPreparationTime());
        return r;
    }

    // UPDATE DTO -> EXISTING ENTITY (pa category)
    public static void updateEntity(Recipe existing, RecipeUpdateDto dto) {
        existing.setName(dto.getName());
        existing.setIngredients(dto.getIngredients());
        existing.setSteps(dto.getSteps());
        existing.setPreparationTime(dto.getPreparationTime());
    }

    // ENTITY -> RESPONSE DTO
    public static RecipeResponseDto toResponseDto(Recipe recipe) {
        return new RecipeResponseDto(
                recipe.getId(),
                recipe.getName(),
                recipe.getIngredients(),
                recipe.getSteps(),
                recipe.getPreparationTime(),
                recipe.getCategory().getId() // vetëm id
        );
    }
}
