package com.example.demo.form;

import com.example.demo.models.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.util.Set;

@Data
public class SignupForm  {
    @NotBlank
    private String firstName;

    private String lastName;
    @NotBlank
    private String phone;
    @NotBlank
    private String username;
    @NotBlank
    private String password;
    @NotBlank
    private String email;

    private Role role;

}
