package com.example.demo.dto;

public class ReviewResponseDto {

    private Long id;
    private String comment;
    private int rating;
    private Long recipeId;

    public ReviewResponseDto(Long id, String comment, int rating, Long recipeId) {
        this.id = id;
        this.comment = comment;
        this.rating = rating;
        this.recipeId = recipeId;
    }

    public Long getId() { return id; }
    public String getComment() { return comment; }
    public int getRating() { return rating; }
    public Long getRecipeId() { return recipeId; }
}
