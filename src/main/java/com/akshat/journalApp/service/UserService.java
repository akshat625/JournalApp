package com.akshat.journalApp.service;

import com.akshat.journalApp.model.User;
import com.akshat.journalApp.repo.UserRepo;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

    public void saveUser(User user) {
        userRepo.save(user);
    }

    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    public User getUserById(ObjectId id) {
        return userRepo.findById(id).orElse(null);
    }

    public void deleteUserById(ObjectId id) {
        userRepo.deleteById(id);
    }

}
