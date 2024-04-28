package com.amlan.ooptwitter.repository;

import com.amlan.ooptwitter.model.Comment;
import com.amlan.ooptwitter.model.Post;
import com.amlan.ooptwitter.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    //find all comments by post

    List<Comment> findAllByCommentPost(Post post);
    //find by comment_id
    Comment findByCommentID(int commentID);
}
