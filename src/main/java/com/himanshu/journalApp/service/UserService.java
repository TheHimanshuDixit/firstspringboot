package com.himanshu.journalApp.service;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;

import com.himanshu.journalApp.entity.User;
import com.himanshu.journalApp.repository.UserRepository;

public class UserService {
    @Autowired
    private UserRepository userRepository;

    public void saveUser(User user) {
        userRepository.save(user);
    }
    
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(ObjectId id) {
        return userRepository.findById(id).orElse(null);
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public void deleteUserById(ObjectId id) {
        userRepository.deleteById(id);
    }

    public void deleteUserByUsername(String username) {
        userRepository.deleteByUsername(username);
    }

}
