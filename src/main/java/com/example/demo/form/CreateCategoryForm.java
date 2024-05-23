package com.example.demo.form;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.Instant;

@Data
public class CreateCategoryForm {

    @NotBlank
    private String name;

}
