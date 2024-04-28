package com.amlan.ooptwitter.controller;

import com.amlan.ooptwitter.model.Comment;
import com.amlan.ooptwitter.model.Post;
import com.amlan.ooptwitter.model.User;
import com.amlan.ooptwitter.service.PostService;
import com.amlan.ooptwitter.service.UserService;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@RestController
public class PostController {

    @Autowired
    private final PostService postService;
    @Autowired
    private final UserService userService;
    @Autowired
    private final ObjectMapper objectMapper = new ObjectMapper();

    public PostController(PostService postService , UserService userService) {
        this.postService = postService;
        this.userService = userService;
    }



    static class postContentConfig{
        @JsonProperty("postCreator")
        private int postCreator;

        @JsonProperty("post")
        private String post;

        public int getPostCreator() {
            return postCreator;
        }

        public String getPost() {
            return post;
        }
    }
    @PostMapping("/post")
    public ResponseEntity<String> addPost(@RequestBody postContentConfig post){
        //check if user exists
        User user1 = userService.getUserByuserID(post.getPostCreator());
        if(user1 == null){
            return ResponseEntity.badRequest().body("User does not exist");
        }
        else{
            //create new post
            postService.addPost(user1, post.getPost());
            return ResponseEntity.ok("Post created successfully");
        }

    }
    @PatchMapping("/post")
    public ResponseEntity<String> editPostContent(@RequestBody Map<String, Object> payload){
        int postID = Integer.parseInt(payload.get("postID").toString());
        String postContent = payload.get("postContent").toString();
        String result = postService.editPostContent(postID, postContent);
        return ResponseEntity.ok(result);
    }
    @DeleteMapping("/post")
    public ResponseEntity<String> deletePost(@RequestBody Map<String, Object> payload){
        int postID = Integer.parseInt(payload.get("postID").toString());
        String result = postService.deletePost(postID);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/")
    //get all posts on reverse chronological order of creation with their comments
    public ResponseEntity<Object> getAllPosts(){
        List<Post> posts = postService.getAllPosts();
        posts.sort((p1, p2) -> p2.getPostTime().compareTo(p1.getPostTime()));
        List<ObjectNode> postNodes = new ArrayList<>();
        for(Post post: posts){
            List<Comment> comments = postService.getAllCommentsByPost(post);
            ObjectNode postNode = objectMapper.createObjectNode();
            postNode.put(String.valueOf(post.getpostID()), post.getpostContent());
            postNode.put("date", post.getPostDate());
            postNode.put("postID", post.getpostID());
            postNode.put("postContent", post.getpostContent());
            List<ObjectNode> commentNodes = new ArrayList<>();
            for(Comment comment: comments){
                ObjectNode commentNode = objectMapper.createObjectNode();
                commentNode.put("commentID", comment.getcommentID());
                commentNode.put("commentBody", comment.getCommentBody());
                ObjectNode commentCreator = objectMapper.createObjectNode();
                commentCreator.put("userID", comment.getCommentCreator().getuserID());
                commentCreator.put("name", comment.getCommentCreator().getName());
                commentNode.putPOJO("commentCreator", commentCreator);
                commentNodes.add(commentNode);
            }
            postNode.putPOJO("comments", commentNodes);
            postNodes.add(postNode);
        }
        return ResponseEntity.ok(postNodes);
    }

}


