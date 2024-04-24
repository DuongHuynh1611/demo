package com.example.demo.controllers;

import com.example.demo.form.CreateUserForm;
import com.example.demo.form.UpdateUserForm;
import com.example.demo.models.User;
import com.example.demo.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;
    @PostMapping()
    private Object createUser(@Valid @RequestBody CreateUserForm createForm){
        User user = new User();
        user.setId(createForm.getId());
        user.setEmail(createForm.getEmail());
        user.setPhone(createForm.getPhone());
        user.setPassword(createForm.getPassword());
        user.setUsername(createForm.getUsername());
        user.setFirstName(createForm.getFirstName());
        user.setLastName(createForm.getLastName());
        user.setNow(createForm.getNow());
        userService.saveUser(user);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(createForm.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    @PutMapping()
    private Object updateUser(@Valid @RequestBody UpdateUserForm updateForm){
        User user = userService.findById(updateForm.getId());
        user.setFirstName(updateForm.getFirstName());
        user.setLastName(updateForm.getLastName());
        user.setNow(updateForm.getNow());
        return userService.saveUser(user);
    }

    @GetMapping()
    List<User> finAllUser(){
        return userService.finAll();
    }
    @GetMapping("/{id}")
    private Object getById(@PathVariable(value = "id") Integer id) {
        User user = userService.findById(id);
        if (null == user)
            throw new UserNotFoundException("id-" + id);

        EntityModel<User> entityModel = EntityModel.of(user);
        Link link = WebMvcLinkBuilder.linkTo(
                methodOn(this.getClass()).finAllUser()).withRel("all-users");
        entityModel.add(link);
        return entityModel;
    }

    @DeleteMapping("/{id}")
    private void deleteById(@PathVariable(value = "id") Integer id) {
       User user = userService.deleteById(id);
        //return "Delete ok!";
        if (null == user)
            throw new UserNotFoundException("id-" + id);
    }
;}
