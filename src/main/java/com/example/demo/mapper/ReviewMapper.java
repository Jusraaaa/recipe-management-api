package com.example.demo.mapper;

import com.example.demo.dto.ReviewResponseDto;
import com.example.demo.model.Review;

public class ReviewMapper {

    private ReviewMapper() {}

    public static ReviewResponseDto toResponseDto(Review review) {
        return new ReviewResponseDto(
                review.getId(),
                review.getComment(),
                review.getRating(),
                review.getRecipe().getId()
        );
    }
}
