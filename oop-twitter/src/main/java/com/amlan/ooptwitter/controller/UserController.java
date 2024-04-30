package com.amlan.ooptwitter.controller;

import com.amlan.ooptwitter.ErrorJSONFormatter;
import com.amlan.ooptwitter.model.User;
import com.amlan.ooptwitter.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

import static com.fasterxml.jackson.databind.cfg.CoercionInputShape.Array;

@RestController
public class UserController {
    @Autowired
    private final UserService userService;
    @Autowired
    private final ObjectMapper objectMapper = new ObjectMapper();

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user")
    public ResponseEntity<Object> getUser(@RequestParam int userID){
        User user = userService.getUserByuserID(userID);
        if(user == null){
            return ErrorJSONFormatter.errorJSONResponse("User does not exist");
        }
        else{
            ObjectNode userNode = objectMapper.createObjectNode();
            userNode.put("userID", user.getuserID());
            userNode.put("email", user.getEmail());
            userNode.put("name", user.getName());
            return ResponseEntity.ok(userNode);
        }
    }

    @GetMapping("/users")
    public ResponseEntity<ObjectNode[]> getUsers(){
        User[] users = userService.getAllUsers().toArray(new User[0]);
        ObjectNode[] userNodes = Arrays.stream(users).map(user -> {
            ObjectNode userNode = objectMapper.createObjectNode();
            userNode.put("userID", user.getuserID());
            userNode.put("email", user.getEmail());
            userNode.put("name", user.getName());

            return userNode;
        }).toArray(ObjectNode[]::new);
        return ResponseEntity.ok(userNodes);
    }
}