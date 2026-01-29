package com.example.demo.controller;

import com.example.demo.model.Ingredient;
import com.example.demo.service.IngredientService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.lang.reflect.Field;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(IngredientController.class)
class IngredientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private IngredientService ingredientService;

    @Autowired
    private ObjectMapper objectMapper;


    private Ingredient ingredientWithId(Long id, String name) {
        Ingredient i = new Ingredient();
        i.setName(name);

        try {
            Field f = Ingredient.class.getDeclaredField("id");
            f.setAccessible(true);
            f.set(i, id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return i;
    }

    @Test
    void create_shouldReturn201() throws Exception {
        Ingredient body = new Ingredient();
        body.setName("Eggs");

        Mockito.when(ingredientService.create(any(Ingredient.class)))
                .thenReturn(ingredientWithId(1L, "Eggs"));

        mockMvc.perform(post("/ingredients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body)))
                .andExpect(status().isCreated());
    }

    @Test
    void create_invalidBody_shouldReturn400() throws Exception {

        mockMvc.perform(post("/ingredients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getAll_shouldReturn200() throws Exception {
        Mockito.when(ingredientService.getAll())
                .thenReturn(List.of(
                        ingredientWithId(1L, "Salt"),
                        ingredientWithId(2L, "Sugar")
                ));

        mockMvc.perform(get("/ingredients"))
                .andExpect(status().isOk());
    }

    @Test
    void getById_shouldReturn200() throws Exception {
        Mockito.when(ingredientService.getById(1L))
                .thenReturn(ingredientWithId(1L, "Salt"));

        mockMvc.perform(get("/ingredients/1"))
                .andExpect(status().isOk());
    }

    @Test
    void update_shouldReturn200() throws Exception {
        Ingredient body = new Ingredient();
        body.setName("Updated");

        Mockito.when(ingredientService.update(eq(1L), any(Ingredient.class)))
                .thenReturn(ingredientWithId(1L, "Updated"));

        mockMvc.perform(put("/ingredients/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body)))
                .andExpect(status().isOk());
    }

    @Test
    void delete_shouldReturn204() throws Exception {
        mockMvc.perform(delete("/ingredients/1"))
                .andExpect(status().isNoContent());

        Mockito.verify(ingredientService).delete(1L);
    }
}
