package com.example.demo.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.Instant;

@Data
public class CreatePostForm {
    @NotNull
    private Integer categoryId;

    @NotBlank
    private String title;

    private String content;
}
