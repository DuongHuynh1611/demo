package com.example.demo.form;

import com.example.demo.models.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.Set;

@Data
public class CreateRoleForm {
    private Integer id;
    @NotBlank
    private String username;
    @NotBlank
    private String phone;
    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String firstName;

    @NotBlank
    private String password;

    private String lastName;

    private Set<Long> roleIds;
}
