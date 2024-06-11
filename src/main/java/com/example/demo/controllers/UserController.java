package com.example.demo.controllers;

import com.example.demo.exceptions.BadRequestException;
import com.example.demo.form.CreateUserForm;
import com.example.demo.form.UpdateUserForm;
import com.example.demo.models.User;
import com.example.demo.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;


@RestController
@RequestMapping("/users")
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @PostMapping()
    private Object createUser(@Valid @RequestBody CreateUserForm createForm){
        if(userService.existsByPhone(createForm.getPhone())) {
            throw new BadRequestException("SDT da ton tai");
        }
        else if (userService.existsByEmail(createForm.getEmail())){
            throw new BadRequestException("Email da ton tai");
        }else if (userService.existsByUserName(createForm.getUsername())){
            throw new BadRequestException("Username da ton tai");
        }
        User user = new User();
        user.setId(createForm.getId());
        user.setEmail(createForm.getEmail());
        user.setPhone(createForm.getPhone());
        user.setPassword(passwordEncoder.encode(createForm.getPassword()));
        user.setUsername(createForm.getUsername());
        user.setFirstName(createForm.getFirstName());
        user.setLastName(createForm.getLastName());
        userService.saveUser(user);

        return makeResponse(true, user, "Create user successful!");
    }

    @PutMapping()
    private Object updateUser(@Valid @RequestBody UpdateUserForm updateForm){
        User user = userService.findById(updateForm.getId());
        user.setFirstName(updateForm.getFirstName());
        user.setLastName(updateForm.getLastName());
        return userService.saveUser(user);
    }

    @GetMapping()
    List<User> finAllUser(){
        return userService.finAll();
    }
    @GetMapping("/{id}")
    private Object getById(@PathVariable(value = "id") Integer id) {
        User user = userService.findById(id);
       return user;
    }

    @DeleteMapping("/{id}")
    private void deleteById(@PathVariable(value = "id") Integer id) {
       User user = userService.deleteById(id);
    }
;}
