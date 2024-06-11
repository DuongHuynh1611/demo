package com.example.demo.services;

import com.example.demo.exceptions.BadRequestException;
import com.example.demo.models.ERole;
import com.example.demo.models.Role;
import com.example.demo.models.User;
import com.example.demo.repositories.RoleRepository;
import com.example.demo.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public List<User> finAll(){
        return userRepository.findAll();
    }

    public User findById(Integer id) {
        return userRepository.findById(id).orElseThrow(
                ()->new EntityNotFoundException("User not found with Id: "+id));
    }

    public User deleteById(int id) {
        userRepository.deleteById(id);
        Iterator<User> iterator = userRepository.findAll().iterator();
        while (iterator.hasNext()){
            User user = iterator.next();
            if (user.getId()==id){
                iterator.remove();
                return user;
            }
        }
        return null;
    }

    public boolean existsByPhone(String phone) {
        return userRepository.existsByPhone(phone);
    }

    public boolean existsByEmail(String email){
        return userRepository.existsByEmail(email);
    }

    public boolean existsByUserName(String username){
        return userRepository.existsByUsername(username);
    }

    @Transactional
    public void addRoleToUsers(List<Integer> id, String roles) {
        Optional<Role> roleOpt = roleRepository.findByName(ERole.valueOf(roles));
        if (!roleOpt.isPresent()) {
            throw new BadRequestException("Role not found");
        }
        Role role = roleOpt.get();
        List<User> users = userRepository.findAllById(id);

        for (User user : users) {
            user.getRoles().add(role);
        }
        userRepository.saveAll(users);
    }

    @Transactional
    public void updateRolesForUsers(List<Integer> id, String newRoles) {
        Optional<Role> roleOpt = roleRepository.findByName(ERole.valueOf(newRoles));
        if (!roleOpt.isPresent()) {
            throw new BadRequestException("Role not found");
        }
        Role newRole = roleOpt.get();

        List<User> users = userRepository.findAllById(id);

        if (users.size() != id.size()) {
            throw new BadRequestException("User not found");
        }

        for (User user : users) {
            Set<Role> roles = new HashSet<>();
            roles.add(newRole);
            user.setRoles(roles);
        }

        userRepository.saveAll(users);
    }
}
