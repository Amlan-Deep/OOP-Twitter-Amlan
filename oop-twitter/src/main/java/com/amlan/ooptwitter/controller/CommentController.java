package com.amlan.ooptwitter.controller;

import com.amlan.ooptwitter.model.Comment;
import com.amlan.ooptwitter.model.Post;
import com.amlan.ooptwitter.model.User;
import com.amlan.ooptwitter.service.CommentService;
import com.amlan.ooptwitter.service.PostService;
import com.amlan.ooptwitter.service.UserService;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class CommentController {
    @Autowired
    private final CommentService commentService;
    @Autowired
    private final PostService postService;
    @Autowired
    private final UserService userService;

    public CommentController(CommentService commentService, PostService postService, UserService userService) {
        this.commentService = commentService;
        this.postService = postService;
        this.userService = userService;
    }

    static class CommentBodyConfig{
        @JsonProperty("commentCreator")
        private int commentCreator;

        @JsonProperty("comment")
        private String comment;

        @JsonProperty("postID")
        private int postID;

        public int getCommentCreator() {
            return commentCreator;
        }

        public String getComment() {
            return comment;
        }

        public int getpostID() {
            return postID;
        }
    }

    @PostMapping("/comment")
    public ResponseEntity<String> addComment(@RequestBody CommentBodyConfig comment){
        //check if user exists
        User user1 = userService.getUserByuserID(comment.getCommentCreator());
        if(user1 == null){
            return ResponseEntity.badRequest().body("User does not exist");
        }
        else {
            //check if post exists
            Post post = postService.getPostBypostID(comment.getpostID());
            if (post == null) {
                return ResponseEntity.badRequest().body("Post does not exist");
            } else {
                //create new comment
                commentService.addComment(user1, comment.getComment(), post);
                return ResponseEntity.ok("Comment created successfully");
            }

        }
    }
    @PatchMapping("/comment")
    public ResponseEntity<String> editCommentContent(@RequestBody Map<String, Object> payload){
        int commentID = Integer.parseInt(payload.get("commentID").toString());
        String commentContent = payload.get("commentContent").toString();
        String result = commentService.editCommentContent(commentID, commentContent);
        return ResponseEntity.ok(result);
    }
    @DeleteMapping("/comment")
    public ResponseEntity<String> deleteComment(@RequestBody Map<String, Object> payload){
        int commentID = Integer.parseInt(payload.get("commentID").toString());
        String result = commentService.deleteComment(commentID);
        return ResponseEntity.ok(result);
    }
}
