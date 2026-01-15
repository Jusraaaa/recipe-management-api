package com.example.demo.controller;

import com.example.demo.dto.ReviewCreateDto;
import com.example.demo.dto.ReviewResponseDto;
import com.example.demo.mapper.ReviewMapper;
import com.example.demo.model.Review;
import com.example.demo.service.ReviewService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    // CREATE REVIEW
    @PostMapping("/recipes/{recipeId}/reviews")
    @ResponseStatus(HttpStatus.CREATED)
    public ReviewResponseDto create(@PathVariable Long recipeId,
                                    @Valid @RequestBody ReviewCreateDto dto) {
        Review saved = reviewService.createForRecipe(recipeId, dto);
        return ReviewMapper.toResponseDto(saved);
    }

    // GET REVIEWS FOR RECIPE
    @GetMapping("/recipes/{recipeId}/reviews")
    public List<ReviewResponseDto> getByRecipe(@PathVariable Long recipeId) {
        return reviewService.getByRecipeId(recipeId)
                .stream()
                .map(ReviewMapper::toResponseDto)
                .toList();
    }


    @GetMapping("/recipes/{recipeId}/average-rating")
    public double getAverageRating(@PathVariable Long recipeId) {
        return reviewService.getAverageRating(recipeId);
    }
}
