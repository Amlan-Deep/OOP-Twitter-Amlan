package com.amlan.ooptwitter.controller;
import com.amlan.ooptwitter.model.User;
import com.amlan.ooptwitter.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthController {

    private final UserService userService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user) {
        //check if user exists
        User user1 = userService.getUserByEmail(user.getEmail());
        if (user1 == null) {
            return ResponseEntity.badRequest().body("User does not exist");
        } else {
            //check if password is correct
            if (user1.getPassword().equals(user.getPassword())) {
                return ResponseEntity.ok("Login successful");
            } else {
                return ResponseEntity.badRequest().body("Incorrect password");
            }
        }
    }

    //signup functionality
    @PostMapping("/signup")
    public ResponseEntity<String> signIn(@RequestBody User user) {
        //check if user already exists
        if (userService.getUserByEmail(user.getEmail()) != null) {
            return ResponseEntity.badRequest().body("User already exists");
        } else {
            //create new user
            userService.addUser(user.getEmail(), user.getPassword(), user.getName());
            return ResponseEntity.ok("User created successfully");
        }

    }

}

