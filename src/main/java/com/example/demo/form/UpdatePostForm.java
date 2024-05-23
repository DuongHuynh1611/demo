package com.example.demo.form;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdatePostForm extends CreatePostForm {
    @NotNull
    private Integer id;
}
