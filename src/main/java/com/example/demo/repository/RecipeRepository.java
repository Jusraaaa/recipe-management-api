package com.example.demo.repository;

import com.example.demo.model.Recipe;
import com.example.demo.model.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    List<Recipe> findByCategory(CategoryEntity category);

    List<Recipe> findByNameContainingIgnoreCase(String name);
}
