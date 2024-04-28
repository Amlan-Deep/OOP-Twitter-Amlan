package com.amlan.ooptwitter.repository;

import com.amlan.ooptwitter.model.Post;
import com.amlan.ooptwitter.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    // find post by post_id
    Post findBypostID(int postID);

    // find post by postCreator
    Post findByPostCreator(User postCreator);

    List<Post> findAllByPostCreator(User postCreator);

    // find post by postDate
    Post findByPostDate(String postDate);

    // find post by postTime
    Post findByPostTime(String postTime);

}
