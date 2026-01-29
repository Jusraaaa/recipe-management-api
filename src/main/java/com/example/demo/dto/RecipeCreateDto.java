package com.example.demo.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public class RecipeCreateDto {

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Ingredients are required")
    private String ingredients;

    @NotBlank(message = "Steps are required")
    private String steps;

    @Min(value = 1, message = "Preparation time must be at least 1")
    private int preparationTime;

    @NotNull(message = "Category id is required")
    private Long categoryId;


    private List<Long> ingredientIds;

    public RecipeCreateDto() {}

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getIngredients() { return ingredients; }
    public void setIngredients(String ingredients) { this.ingredients = ingredients; }

    public String getSteps() { return steps; }
    public void setSteps(String steps) { this.steps = steps; }

    public int getPreparationTime() { return preparationTime; }
    public void setPreparationTime(int preparationTime) { this.preparationTime = preparationTime; }

    public Long getCategoryId() { return categoryId; }
    public void setCategoryId(Long categoryId) { this.categoryId = categoryId; }

    public List<Long> getIngredientIds() { return ingredientIds; }
    public void setIngredientIds(List<Long> ingredientIds) { this.ingredientIds = ingredientIds; }
}
