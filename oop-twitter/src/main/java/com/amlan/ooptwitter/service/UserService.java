package com.amlan.ooptwitter.service;

import com.amlan.ooptwitter.model.Post;
import com.amlan.ooptwitter.model.User;
import com.amlan.ooptwitter.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.amlan.ooptwitter.repository.PostRepository;


import java.util.List;

@Service
public class UserService {
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private PostRepository postRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //function for add user
    public void addUser(String email, String password, String name) {
        userRepository.save(new User(email, password, name));
    }

    //function for get user by email
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    //function for get user by userID
    public User getUserByuserID(int userID) {
        return userRepository.findByuserID(userID);
    }

    //function for get user by username
    public User getUserByUsername(String username) {
        return userRepository.findByName(username);
    }

    //get all posts by user
    public List<Post> getAllPostsByUser(User user) {
        return postRepository.findAllByPostCreator(user);
    }

    //get all users
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }



}
