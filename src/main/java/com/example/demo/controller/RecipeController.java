package com.example.demo.controller;

import com.example.demo.dto.RecipeCreateDto;
import com.example.demo.dto.RecipeResponseDto;
import com.example.demo.dto.RecipeUpdateDto;
import com.example.demo.mapper.RecipeMapper;
import com.example.demo.model.Recipe;
import com.example.demo.service.RecipeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/recipes")
public class RecipeController {

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    // CREATE (me DTO + categoryId)
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RecipeResponseDto create(@Valid @RequestBody RecipeCreateDto dto) {
        Recipe saved = recipeService.create(dto);
        return RecipeMapper.toResponseDto(saved);
    }

    // READ ALL (+ sorting)
    @GetMapping
    public List<RecipeResponseDto> getAll(
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) String dir
    ) {
        return recipeService.getAllSorted(sortBy, dir)
                .stream()
                .map(RecipeMapper::toResponseDto)
                .toList();
    }


    // READ BY ID
    @GetMapping("/{id}")
    public RecipeResponseDto getById(@PathVariable Long id) {
        return RecipeMapper.toResponseDto(recipeService.getById(id));
    }

    // UPDATE (me DTO + categoryId)
    @PutMapping("/{id}")
    public RecipeResponseDto update(@PathVariable Long id,
                                    @Valid @RequestBody RecipeUpdateDto dto) {
        Recipe updated = recipeService.update(id, dto);
        return RecipeMapper.toResponseDto(updated);
    }

    // DELETE
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        recipeService.delete(id);
    }

    // FILTER BY CATEGORY ID
    @GetMapping("/category/{categoryId}")
    public List<RecipeResponseDto> filterByCategory(@PathVariable Long categoryId) {
        return recipeService.filterByCategoryId(categoryId)
                .stream()
                .map(RecipeMapper::toResponseDto)
                .toList();
    }

    // SEARCH
    @GetMapping("/search")
    public List<RecipeResponseDto> search(@RequestParam String name) {
        return recipeService.searchByName(name)
                .stream()
                .map(RecipeMapper::toResponseDto)
                .toList();
    }
}
