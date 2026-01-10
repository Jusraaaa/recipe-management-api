package com.example.demo.service;

import com.example.demo.dto.ReviewCreateDto;
import com.example.demo.model.Recipe;
import com.example.demo.model.Review;
import com.example.demo.repository.ReviewRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final RecipeService recipeService;

    public ReviewService(ReviewRepository reviewRepository, RecipeService recipeService) {
        this.reviewRepository = reviewRepository;
        this.recipeService = recipeService;
    }

    public Review createForRecipe(Long recipeId, ReviewCreateDto dto) {
        Recipe recipe = recipeService.getById(recipeId);

        Review review = new Review();
        review.setComment(dto.getComment());
        review.setRating(dto.getRating());
        review.setRecipe(recipe);

        return reviewRepository.save(review);
    }

    public List<Review> getByRecipeId(Long recipeId) {
        return reviewRepository.findByRecipeId(recipeId);
    }
    public double getAverageRating(Long recipeId) {
        Double avg = reviewRepository.getAverageRating(recipeId);
        return avg == null ? 0.0 : avg;
    }

}
