package com.example.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "recipes")
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Recipe name is required")
    @Column(nullable = false)
    private String name;

    @NotBlank(message = "Ingredients are required")
    @Column(nullable = false, length = 2000)
    private String ingredients;

    @NotBlank(message = "Steps are required")
    @Column(nullable = false, length = 4000)
    private String steps;

    @Min(value = 1, message = "Preparation time must be at least 1 minute")
    @Column(nullable = false)
    private int preparationTime;

    @ManyToOne(optional = false)
    @JoinColumn(name = "category_id", nullable = false)
    private CategoryEntity category;

    public Recipe() {
    }

    public Long getId() { return id; }
    // rekomandim: mos e përdor setId, po s’po ta fshij nëse e don
     public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getIngredients() { return ingredients; }
    public void setIngredients(String ingredients) { this.ingredients = ingredients; }

    public String getSteps() { return steps; }
    public void setSteps(String steps) { this.steps = steps; }

    public int getPreparationTime() { return preparationTime; }
    public void setPreparationTime(int preparationTime) { this.preparationTime = preparationTime; }

    public CategoryEntity getCategory() { return category; }
    public void setCategory(CategoryEntity category) { this.category = category; }
}
