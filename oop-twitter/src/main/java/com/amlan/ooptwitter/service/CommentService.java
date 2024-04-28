package com.amlan.ooptwitter.service;

import com.amlan.ooptwitter.model.Comment;
import com.amlan.ooptwitter.model.Post;
import com.amlan.ooptwitter.model.User;
import com.amlan.ooptwitter.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;

@Service
public class CommentService {
    @Autowired
    private final CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    //function for add comment
    public void addComment(User commentCreator, String comment, Post post) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        DateTimeFormatter dtf1 = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        Comment comment1 = new Comment(commentCreator, post, comment, dtf.format(java.time.LocalDateTime.now()), dtf1.format(java.time.LocalDateTime.now()));
        commentRepository.save(comment1);
    }

    // function for editing comment content
    public String editCommentContent(int commentID, String commentContent) {
        Comment comment = commentRepository.findByCommentID(commentID);
        if (comment == null) {
            return "Comment does not exist";
        }
        comment.setCommentBody(commentContent);
        commentRepository.save(comment);
        return "Comment edited successfully";
    }
    // CommentService.java

    // function for deleting a comment
    public String deleteComment(int commentID) {
        Comment comment = commentRepository.findByCommentID(commentID);
        if (comment == null) {
            return "Comment does not exist";
        }
        commentRepository.delete(comment);
        return "Comment deleted";
    }



}
