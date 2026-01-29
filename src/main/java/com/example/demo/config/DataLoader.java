package com.example.demo.config;

import com.example.demo.model.CategoryEntity;
import com.example.demo.model.Recipe;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.RecipeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {

    private final RecipeRepository recipeRepository;
    private final CategoryRepository categoryRepository;

    public DataLoader(RecipeRepository recipeRepository,
                      CategoryRepository categoryRepository) {
        this.recipeRepository = recipeRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void run(String... args) {


        if (recipeRepository.count() > 0) return;


        CategoryEntity breakfast = categoryRepository.save(
                new CategoryEntity("BREAKFAST")
        );
        CategoryEntity lunch = categoryRepository.save(
                new CategoryEntity("LUNCH")
        );
        CategoryEntity dinner = categoryRepository.save(
                new CategoryEntity("DINNER")
        );
        CategoryEntity dessert = categoryRepository.save(
                new CategoryEntity("DESSERT")
        );

        // 👉 recipes
        Recipe r1 = new Recipe();
        r1.setName("Pancakes");
        r1.setIngredients("Flour, milk, eggs");
        r1.setSteps("Mix ingredients, cook on pan");
        r1.setPreparationTime(15);
        r1.setCategory(breakfast);

        Recipe r2 = new Recipe();
        r2.setName("Chicken Salad");
        r2.setIngredients("Chicken, lettuce, olive oil, salt");
        r2.setSteps("Cut and mix everything");
        r2.setPreparationTime(10);
        r2.setCategory(lunch);

        Recipe r3 = new Recipe();
        r3.setName("Spaghetti Bolognese");
        r3.setIngredients("Spaghetti, minced meat, tomato sauce");
        r3.setSteps("Cook pasta, prepare sauce, mix");
        r3.setPreparationTime(30);
        r3.setCategory(dinner);

        Recipe r4 = new Recipe();
        r4.setName("Chocolate Brownies");
        r4.setIngredients("Chocolate, butter, eggs, flour");
        r4.setSteps("Mix, bake, cool");
        r4.setPreparationTime(35);
        r4.setCategory(dessert);

        recipeRepository.saveAll(List.of(r1, r2, r3, r4));
    }
}
