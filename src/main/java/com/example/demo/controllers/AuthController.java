package com.example.demo.controllers;

import com.example.demo.dto.ApiMessageDto;
import com.example.demo.dto.TokenAuthDto;
import com.example.demo.exceptions.BadRequestException;
import com.example.demo.form.UpdateRoleForm;
import com.example.demo.form.AddRoleForm;
import com.example.demo.form.LoginForm;
import com.example.demo.form.SignupForm;
import com.example.demo.models.ERole;
import com.example.demo.models.Role;
import com.example.demo.models.User;
import com.example.demo.repositories.RoleRepository;
import com.example.demo.repositories.UserRepository;
import com.example.demo.security.JwtUtils;
import com.example.demo.security.UserDetailsImpl;
import com.example.demo.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/auth")
public class AuthController extends BaseController {

    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    JwtUtils jwtUtils;
    @Value("${app.jwt.expirationInMs}")
    private long jwtExpirationInMs;
    @Autowired
    ObjectMapper objectMapper;

    @PostMapping("/login")
    public ApiMessageDto<Object> authenticateUser(@Valid @RequestBody LoginForm loginForm) {
        Authentication authentication = authenticationManager
            .authenticate(new UsernamePasswordAuthenticationToken(loginForm.getUsername(), loginForm.getPassword()));
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(userDetails);
        TokenAuthDto tokenAuthDto = new TokenAuthDto(jwt, jwtExpirationInMs);
        return makeResponse(true, tokenAuthDto,"Login successful!");
    }

    @PostMapping("/signup")
    public ApiMessageDto<Object> registerUser(@Valid @RequestBody SignupForm signUpForm) {
        if (userRepository.existsByUsername(signUpForm.getUsername())) {
            throw new BadRequestException("Username da ton tai");
        }

        if (userRepository.existsByEmail(signUpForm.getEmail())) {
            throw new BadRequestException("Email da ton tai");
        }
        // Create new user's account
        User user = new User();
        user.setUsername(signUpForm.getUsername());
        user.setEmail(signUpForm.getEmail());
        user.setPhone(signUpForm.getPhone());
        user.setFirstName(signUpForm.getFirstName());
        user.setLastName(signUpForm.getLastName());
        user.setPassword(passwordEncoder.encode(signUpForm.getPassword()));

        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new BadRequestException("Error: Role is not found."));
        roles.add(userRole);

        user.setRoles(roles);
        userRepository.save(user);

        return makeResponse(true,user,"User registered successfully!");
    }

    @PostMapping("/add-role")
    public ApiMessageDto<Object> createdRole (@Valid @RequestBody AddRoleForm addRoleForm) {
        if (userRepository.existsByUsername(addRoleForm.getUsername())) {
            throw new BadRequestException("Username da ton tai");
        }

        if (userRepository.existsByEmail(addRoleForm.getEmail())) {
            throw new BadRequestException("Email da ton tai");
        }
        User user = new User();
        user.setUsername(addRoleForm.getUsername());
        user.setEmail(addRoleForm.getEmail());
        user.setPhone(addRoleForm.getPhone());
        user.setFirstName(addRoleForm.getFirstName());
        user.setLastName(addRoleForm.getLastName());
        user.setPassword(passwordEncoder.encode(addRoleForm.getPassword()));

        Set<String> strRoles = addRoleForm.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new BadRequestException("Error: Role is not found."));
            roles.add(userRole);
        } else
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new BadRequestException("Error: Role is not found."));
                        roles.add(adminRole);
                        break;
                    default:
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new BadRequestException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        user.setRoles(roles);
        userRepository.save(user);
        return makeResponse(true,user,"Role has been created successfully!");
    }

    @PutMapping("/update-role")
    public ResponseEntity<?> updateRoleToUsers(@Valid @RequestBody UpdateRoleForm updateRoleForm) {
        userService.updateRoleToUsers(updateRoleForm.getUserIds(), updateRoleForm.getRoleName());
        return ResponseEntity.ok().build();
    }
    
}
