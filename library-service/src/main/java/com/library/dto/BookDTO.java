package com.library.dto;

import jakarta.validation.constraints.NotBlank;

public class BookDTO {

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Author is required")
    private String author;

    public BookDTO() {}

    public BookDTO(String title, String author) {
        this.title = title;
        this.author = author;
    }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }
}
