package com.example.demo.service;

import com.example.demo.model.Ingredient;
import com.example.demo.repository.IngredientRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IngredientService {

    private final IngredientRepository ingredientRepository;

    public IngredientService(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    public Ingredient create(Ingredient ingredient) {
        return ingredientRepository.save(ingredient);
    }

    public List<Ingredient> getAll() {
        return ingredientRepository.findAll();
    }

    public Ingredient getById(Long id) {
        return ingredientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ingredient me id=" + id + " nuk u gjet"));
    }

    public Ingredient update(Long id, Ingredient updated) {
        Ingredient existing = getById(id);
        existing.setName(updated.getName());
        return ingredientRepository.save(existing);
    }

    public void delete(Long id) {
        ingredientRepository.deleteById(id);
    }
}
