package com.example.demo.form;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateCategoryForm extends CreateCategoryForm{
    @NotNull
    private Integer id;
}
