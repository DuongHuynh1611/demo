package com.example.demo.services;

import com.example.demo.models.User;
import com.example.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public List<User> finAll(){
        return userRepository.findAll();
    }


    public User findById(Integer id) {
        return userRepository.findById(id).orElse(null);
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
}
