package com.example.demo.dto;

public class RecipeResponseDto {

    private Long id;
    private String name;
    private String ingredients;
    private String steps;
    private int preparationTime;
    private Long categoryId;

    public RecipeResponseDto(Long id,
                             String name,
                             String ingredients,
                             String steps,
                             int preparationTime,
                             Long categoryId) {
        this.id = id;
        this.name = name;
        this.ingredients = ingredients;
        this.steps = steps;
        this.preparationTime = preparationTime;
        this.categoryId = categoryId;
    }

    // getters only
    public Long getId() { return id; }
    public String getName() { return name; }
    public String getIngredients() { return ingredients; }
    public String getSteps() { return steps; }
    public int getPreparationTime() { return preparationTime; }
    public Long getCategoryId() { return categoryId; }
}
