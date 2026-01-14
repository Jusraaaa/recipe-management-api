package com.example.demo.service;

import com.example.demo.dto.ReviewCreateDto;
import com.example.demo.model.Recipe;
import com.example.demo.model.Review;
import com.example.demo.repository.ReviewRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReviewServiceTest {

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private RecipeService recipeService;

    @InjectMocks
    private ReviewService reviewService;

    @Test
    void createForRecipe_shouldSaveReview() {
        Recipe recipe = new Recipe();
        recipe.setId(1L);

        when(recipeService.getById(1L)).thenReturn(recipe);
        when(reviewRepository.save(any(Review.class))).thenAnswer(inv -> inv.getArgument(0));

        ReviewCreateDto dto = new ReviewCreateDto();
        dto.setComment("Nice!");
        dto.setRating(5);

        Review saved = reviewService.createForRecipe(1L, dto);

        assertNotNull(saved);
        assertEquals("Nice!", saved.getComment());
        assertEquals(5, saved.getRating());
        assertNotNull(saved.getRecipe());

        verify(recipeService).getById(1L);
        verify(reviewRepository).save(any(Review.class));
    }

    @Test
    void getByRecipeId_shouldReturnList() {
        when(reviewRepository.findByRecipeId(1L)).thenReturn(List.of(new Review(), new Review()));

        List<Review> result = reviewService.getByRecipeId(1L);

        assertEquals(2, result.size());
        verify(reviewRepository).findByRecipeId(1L);
    }

    @Test
    void getAverageRating_shouldReturn0_whenNull() {
        when(reviewRepository.getAverageRating(1L)).thenReturn(null);

        double avg = reviewService.getAverageRating(1L);

        assertEquals(0.0, avg);
    }
}
