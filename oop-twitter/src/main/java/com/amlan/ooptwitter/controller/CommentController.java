package com.amlan.ooptwitter.controller;

import com.amlan.ooptwitter.ErrorJSONFormatter;
import com.amlan.ooptwitter.model.Comment;
import com.amlan.ooptwitter.model.Post;
import com.amlan.ooptwitter.model.User;
import com.amlan.ooptwitter.service.CommentService;
import com.amlan.ooptwitter.service.PostService;
import com.amlan.ooptwitter.service.UserService;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
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
    @Autowired
    private final ObjectMapper objectMapper = new ObjectMapper();

    public CommentController(CommentService commentService, PostService postService, UserService userService) {
        this.commentService = commentService;
        this.postService = postService;
        this.userService = userService;
    }

    static class CommentBodyConfig{
        @JsonProperty("userID")
        private int commentCreator;

        @JsonProperty("commentBody")
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

    @GetMapping("/comment")
    public ResponseEntity<Object> getComment(@RequestParam int commentID){
        Comment comment = commentService.getCommentByCommentID(commentID);
        if(comment == null){
            return ErrorJSONFormatter.errorJSONResponse("Comment does not exist");
        }
        ObjectNode response = objectMapper.createObjectNode();
        response.put("commentID", comment.getcommentID());
        ObjectNode commentCreator = objectMapper.createObjectNode();
        commentCreator.put("userID", comment.getCommentCreator().getuserID());
        commentCreator.put("name", comment.getCommentCreator().getName());
        response.set("commentCreator", commentCreator);
        response.put("commentBody", comment.getCommentBody());
        return ResponseEntity.ok(response);

    }

    @PostMapping("/comment")
    public ResponseEntity<Object> addComment(@RequestBody CommentBodyConfig comment){
        //check if user exists
        User user1 = userService.getUserByuserID(comment.getCommentCreator());
        if(user1 == null){
            return ErrorJSONFormatter.errorJSONResponse("User does not exist");
        }
        else {
            //check if post exists
            Post post = postService.getPostBypostID(comment.getpostID());
            if (post == null) {
                return ErrorJSONFormatter.errorJSONResponse("Post does not exist");
            } else {
                //create new comment
                commentService.addComment(user1, comment.getComment(), post);
                return ResponseEntity.ok("Comment created successfully");
            }

        }
    }
    @PatchMapping("/comment")
    public ResponseEntity<Object> editCommentContent(@RequestBody Map<String, Object> payload){
        int commentID = Integer.parseInt(payload.get("commentID").toString());
        String commentContent = payload.get("commentBody").toString();
        ResponseEntity<Object> response = commentService.editCommentContent(commentID, commentContent);
        return response;
    }

    @DeleteMapping("/comment")
    public ResponseEntity<Object> deleteComment(@RequestParam int commentID){
        if(commentService.getCommentByCommentID(commentID) == null){
            return ErrorJSONFormatter.errorJSONResponse("Comment does not exist");
        }
        else{
            commentService.deleteComment(commentID);
            return ResponseEntity.ok("Comment deleted");
        }
    }
}
