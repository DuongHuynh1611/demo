package com.example.demo.form;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.Instant;

@Data
public class CreateCategoryForm {
    private Integer id;

    @NotBlank
    private String firstName;

    private String lastName;

    Instant createdDate = Instant.now();
}
