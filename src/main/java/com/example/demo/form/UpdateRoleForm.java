package com.example.demo.form;

import lombok.Data;

import java.util.List;

@Data
public class UpdateRoleForm {
    private List<Integer> userIds;
    private String roleName;
}
