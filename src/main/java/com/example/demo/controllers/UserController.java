package com.example.demo.controllers;

import com.example.demo.dto.ApiMessageDto;
import com.example.demo.exceptions.BadRequestException;
import com.example.demo.form.*;
import com.example.demo.models.ERole;
import com.example.demo.models.Role;
import com.example.demo.models.User;
import com.example.demo.repositories.RoleRepository;
import com.example.demo.repositories.UserRepository;
import com.example.demo.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@RestController
@RequestMapping("/users")
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

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
    
    @PostMapping("/add-role")
    public void addRoleToUsers(@Valid @RequestBody AddRoleForm addRoleForm) {
        userService.addRoleToUsers(addRoleForm.getId(), addRoleForm.getRole());
    }

    @PutMapping("/update-role")
    public void updateRolesForUsers(@Valid @RequestBody UpdateRoleForm updateRoleForm) {
        userService.updateRolesForUsers(updateRoleForm.getId(), updateRoleForm.getNewRoles());
    }
    @DeleteMapping("/delete-role")
    public void deleteRolesFromUsers(@Valid @RequestBody DeleteRoleForm deleteRoleForm) {
        userService.deleteRolesFromUsers(deleteRoleForm.getId(), deleteRoleForm.getRole());
    }

    @PostMapping("/user-roles")
    public ApiMessageDto<Object> createdRole (@Valid @RequestBody CreateRoleForm createRoleForm) {
        if (userRepository.existsByUsername(createRoleForm.getUsername())) {
            throw new BadRequestException("Username da ton tai");
        }

        if (userRepository.existsByEmail(createRoleForm.getEmail())) {
            throw new BadRequestException("Email da ton tai");
        }
        User user = new User();
        user.setUsername(createRoleForm.getUsername());
        user.setEmail(createRoleForm.getEmail());
        user.setPhone(createRoleForm.getPhone());
        user.setFirstName(createRoleForm.getFirstName());
        user.setLastName(createRoleForm.getLastName());
        user.setPassword(passwordEncoder.encode(createRoleForm.getPassword()));

        Set<Long> roleIds = createRoleForm.getRoleIds();
        Set<Role> roles = new HashSet<>();

        if (roleIds == null || roleIds.isEmpty()) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new BadRequestException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            roleIds.forEach(roleId -> {
                Role role = roleRepository.findById(roleId)
                        .orElseThrow(() -> new BadRequestException("Error: Role is not found."));
                roles.add(role);
            });
        }
        user.setRoles(roles);
        userRepository.save(user);
        return makeResponse(true,user,"Role has been created successfully!");
    }
;}
