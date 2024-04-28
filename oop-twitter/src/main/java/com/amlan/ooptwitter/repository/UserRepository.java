package com.amlan.ooptwitter.repository;

import com.amlan.ooptwitter.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface UserRepository extends JpaRepository<User, Long> {

    //find user by email
    User findByEmail(String email);
    //find user by username
    User findByName(String name);
    //find user by user_id
    User findByuserID(int userID);

}
