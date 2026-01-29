package com.example.demo.controller;

import com.example.demo.dto.ReviewCreateDto;
import com.example.demo.model.Recipe;
import com.example.demo.model.Review;
import com.example.demo.model.User;
import com.example.demo.service.ReviewService;
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

@WebMvcTest(ReviewController.class)
class ReviewControllerTest {

    @Autowired private MockMvc mockMvc;

    @MockitoBean
    private ReviewService reviewService;

    @Autowired private ObjectMapper objectMapper;

    private Review fullReview() {
        Recipe recipe = new Recipe();
        recipe.setId(1L);

        User user = new User();


        Review r = new Review();
        r.setComment("Great!");
        r.setRating(5);
        r.setRecipe(recipe);
        r.setReviewer(user);
        return r;
    }

    @Test
    void createReview_shouldReturn201() throws Exception {
        ReviewCreateDto dto = new ReviewCreateDto();
        dto.setComment("Great!");
        dto.setRating(5);
        dto.setUserId(1L);

        Mockito.when(reviewService.createForRecipe(eq(1L), any(ReviewCreateDto.class)))
                .thenReturn(fullReview());

        mockMvc.perform(post("/recipes/1/reviews")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated());
    }

    @Test
    void getReviewsForRecipe_shouldReturn200() throws Exception {
        Mockito.when(reviewService.getByRecipeId(1L))
                .thenReturn(List.of(fullReview(), fullReview()));

        mockMvc.perform(get("/recipes/1/reviews"))
                .andExpect(status().isOk());
    }

    @Test
    void getAverageRating_shouldReturn200() throws Exception {
        Mockito.when(reviewService.getAverageRating(1L)).thenReturn(4.5);

        mockMvc.perform(get("/recipes/1/average-rating"))
                .andExpect(status().isOk())
                .andExpect(content().string("4.5"));
    }
}
