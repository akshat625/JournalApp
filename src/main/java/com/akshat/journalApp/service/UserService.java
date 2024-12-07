package com.akshat.journalApp.service;

import com.akshat.journalApp.model.User;
import com.akshat.journalApp.repo.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;


    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

//    private static final Logger logger = LoggerFactory.getLogger(UserService.class);


    //controlled by public controller
    public ResponseEntity<?> saveUser(User user) {
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRoles(Arrays.asList("USER"));
            userRepo.save(user);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
//            log.error("Error while saving user: " + e.getMessage());
            log.error("Error occurred for {} :", user.getUserName(), e);
//          logger.error("Error while saving user: " + e.getMessage());
//            logger.warn("Error occurred for {} :", user.getUserName(), e);
            e.printStackTrace( );
            return new ResponseEntity<>(HttpStatusCode.valueOf(403));
        }
    }


    //controlled by admin controller
    public ResponseEntity<List<User>> getAllUsers() {
        try {
            if(!userRepo.findAll().isEmpty()) {
                return new ResponseEntity<>(userRepo.findAll(), HttpStatus.OK);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    //not controlled by any controller
    public User getUserById(ObjectId id) {
        return userRepo.findById(id).orElse(null);
    }

    //not controlled by any controller
    public User findByUserName(String username) {
        return userRepo.findByUserName(username);
    }

    //controlled by user controller
    public ResponseEntity<User> updateUser(User user) {
        try {
            //Authentication object is used to get the current logged-in user.
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userName = authentication.getName();
            User userInDb = userRepo.findByUserName(userName);
            userInDb.setUserName(user.getUserName());
            userInDb.setPassword(user.getPassword());
            saveUser(userInDb);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    //controlled by user controller
    public ResponseEntity<?> deleteUser() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            userRepo.deleteByUserName(authentication.getName());
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<?> greeting() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return new ResponseEntity<>("Hello " + authentication.getName(), HttpStatus.OK);
    }
}
