package com.example.demo.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateUserForm {
    @NotNull
    private Integer id;

    @NotBlank
    private String firstName;

    private String lastName;
}
