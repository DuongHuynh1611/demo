package com.example.demo.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.Instant;

@Data
public class UpdateUserForm {
    @NotNull
    private Integer id;

    @NotBlank
    private String firstName;

    private String lastName;

    Instant now = Instant.now();
}
