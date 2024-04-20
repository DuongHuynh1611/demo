package com.example.demo.controllers;

import com.example.demo.form.CreateUserForm;
import com.example.demo.form.UpdateUserForm;
import com.example.demo.models.User;
import com.example.demo.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;
    @PostMapping()
    private Object createUser(@Valid @RequestBody CreateUserForm createForm){
        User user = new User();
        user.setEmail(createForm.getEmail());
        user.setPhone(createForm.getPhone());
        user.setPassword(createForm.getPassword());
        user.setUsername(createForm.getUsername());
        user.setFirstName(createForm.getFirstName());
        user.setLastName(createForm.getLastName());
        return userService.saveUser(user);
    }

    @PutMapping()
    private Object updateUser(@Valid @RequestBody UpdateUserForm updateForm){
        User user = userService.findById(updateForm.getId());
        user.setFirstName(updateForm.getFirstName());
        user.setLastName(updateForm.getLastName());
        return userService.saveUser(user);
    }

    @GetMapping("/{id}")
    private Object getById(@PathVariable(value = "id") Integer id) {
        return userService.findById(id);
    }

    @DeleteMapping("/{id}")
    private Object deleteById(@PathVariable(value = "id") Integer id) {
        userService.deleteById(id);
        return "Delete ok!";
    }
}
