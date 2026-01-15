package com.example.demo.controller;

import com.example.demo.dto.RecipeCreateDto;
import com.example.demo.dto.RecipeUpdateDto;
import com.example.demo.model.CategoryEntity;
import com.example.demo.model.Recipe;
import com.example.demo.service.RecipeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RecipeController.class)
class RecipeControllerTest {

    @Autowired private MockMvc mockMvc;

    @MockitoBean
    private RecipeService recipeService;

    @Autowired private ObjectMapper objectMapper;

    private Recipe fullRecipe(Long id) {
        CategoryEntity cat = new CategoryEntity(); // pa setId

        Recipe r = new Recipe();
        r.setId(id);
        r.setName("Pancakes");
        r.setIngredients("Eggs, flour");
        r.setSteps("Mix and cook");
        r.setPreparationTime(10);
        r.setCategory(cat);
        return r;
    }

    @Test
    void create_shouldReturn201() throws Exception {
        RecipeCreateDto dto = new RecipeCreateDto();
        dto.setName("Pancakes");
        dto.setIngredients("Eggs, flour");
        dto.setSteps("Mix and cook");
        dto.setPreparationTime(10);
        dto.setCategoryId(1L);

        // ✅ Correct mock for create
        Mockito.when(recipeService.create(any(RecipeCreateDto.class)))
                .thenReturn(fullRecipe(1L));

        mockMvc.perform(post("/recipes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated());
    }

    @Test
    void getAll_shouldReturn200() throws Exception {
        // ✅ Controller tani thërret getAllSorted(null, null)
        Mockito.when(recipeService.getAllSorted(isNull(), isNull()))
                .thenReturn(List.of(fullRecipe(1L), fullRecipe(2L)));

        mockMvc.perform(get("/recipes"))
                .andExpect(status().isOk());
    }

    @Test
    void getById_shouldReturn200() throws Exception {
        Mockito.when(recipeService.getById(1L)).thenReturn(fullRecipe(1L));

        mockMvc.perform(get("/recipes/1"))
                .andExpect(status().isOk());
    }

    @Test
    void update_shouldReturn200() throws Exception {
        RecipeUpdateDto dto = new RecipeUpdateDto();
        dto.setName("Updated");
        dto.setIngredients("I");
        dto.setSteps("S");
        dto.setPreparationTime(5);
        dto.setCategoryId(1L);

        Mockito.when(recipeService.update(eq(1L), any(RecipeUpdateDto.class)))
                .thenReturn(fullRecipe(1L));

        mockMvc.perform(put("/recipes/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());
    }

    @Test
    void delete_shouldReturn204() throws Exception {
        mockMvc.perform(delete("/recipes/1"))
                .andExpect(status().isNoContent());

        Mockito.verify(recipeService).delete(1L);
    }

    @Test
    void filterByCategory_shouldReturn200() throws Exception {
        Mockito.when(recipeService.filterByCategoryId(1L)).thenReturn(List.of(fullRecipe(1L)));

        mockMvc.perform(get("/recipes/category/1"))
                .andExpect(status().isOk());
    }

    @Test
    void search_shouldReturn200() throws Exception {
        Mockito.when(recipeService.searchByName("pan")).thenReturn(List.of(fullRecipe(1L)));

        mockMvc.perform(get("/recipes/search").param("name", "pan"))
                .andExpect(status().isOk());
    }

    @Test
    void getAll_withSorting_shouldReturn200() throws Exception {
        Mockito.when(recipeService.getAllSorted(eq("preparationTime"), eq("desc")))
                .thenReturn(List.of(fullRecipe(1L)));

        mockMvc.perform(get("/recipes")
                        .param("sortBy", "preparationTime")
                        .param("dir", "desc"))
                .andExpect(status().isOk());
    }

}
