package com.example.demo.controller;

import com.example.demo.model.CategoryEntity;
import com.example.demo.service.CategoryService;
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

@WebMvcTest(CategoryController.class)
class CategoryControllerTest {

    @Autowired private MockMvc mockMvc;

    @MockitoBean
    private CategoryService categoryService;

    @Autowired private ObjectMapper objectMapper;


    private CategoryEntity categoryWithId(Long id, String name) {
        CategoryEntity c = new CategoryEntity();
        c.setName(name);

        try {
            Field f = CategoryEntity.class.getDeclaredField("id");
            f.setAccessible(true);
            f.set(c, id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return c;
    }

    @Test
    void create_shouldReturn201() throws Exception {
        CategoryEntity body = new CategoryEntity();
        body.setName("Desserts");

        Mockito.when(categoryService.create(any(CategoryEntity.class)))
                .thenReturn(categoryWithId(1L, "Desserts"));

        mockMvc.perform(post("/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body)))
                .andExpect(status().isCreated());
    }

    @Test
    void create_invalidBody_shouldReturn400() throws Exception {

        mockMvc.perform(post("/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getAll_shouldReturn200() throws Exception {
        Mockito.when(categoryService.getAll())
                .thenReturn(List.of(
                        categoryWithId(1L, "A"),
                        categoryWithId(2L, "B")
                ));

        mockMvc.perform(get("/categories"))
                .andExpect(status().isOk());
    }

    @Test
    void getById_shouldReturn200() throws Exception {
        Mockito.when(categoryService.getById(1L))
                .thenReturn(categoryWithId(1L, "Desserts"));

        mockMvc.perform(get("/categories/1"))
                .andExpect(status().isOk());
    }

    @Test
    void update_shouldReturn200() throws Exception {
        CategoryEntity body = new CategoryEntity();
        body.setName("Updated");

        Mockito.when(categoryService.update(eq(1L), any(CategoryEntity.class)))
                .thenReturn(categoryWithId(1L, "Updated"));

        mockMvc.perform(put("/categories/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body)))
                .andExpect(status().isOk());
    }

    @Test
    void delete_shouldReturn204() throws Exception {
        mockMvc.perform(delete("/categories/1"))
                .andExpect(status().isNoContent());

        Mockito.verify(categoryService).delete(1L);
    }
}
