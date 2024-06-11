package com.example.demo.form;

import lombok.Data;

import java.util.List;

@Data
public class AddRoleForm {
    private List<Integer> id;
    private String role;
}
