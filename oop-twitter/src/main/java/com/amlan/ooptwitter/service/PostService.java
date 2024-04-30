package com.amlan.ooptwitter.service;

import com.amlan.ooptwitter.ErrorJSONFormatter;
import com.amlan.ooptwitter.model.Comment;
import com.amlan.ooptwitter.model.Post;
import com.amlan.ooptwitter.model.User;
import com.amlan.ooptwitter.repository.CommentRepository;
import com.amlan.ooptwitter.repository.PostRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.time.format.DateTimeFormatter;
import java.util.List;


@Service
public class PostService {
    @Autowired
    private final PostRepository postRepository;
    @Autowired
    private final CommentRepository commentRepository;

    public PostService(PostRepository postRepository , CommentRepository commentRepository) {
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
    }

    //function for add post
    public void addPost(User postCreator, String post) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter dtf_time = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        postRepository.save(new Post(postCreator, post, dtf.format(java.time.LocalDateTime.now()), dtf_time.format(java.time.LocalDateTime.now())));

    }

    //function to patch/edit post
    public ResponseEntity<Object> editPostContent(int postID, String postContent) {
        Post post = postRepository.findBypostID(postID);
        if (post == null) {
            return ErrorJSONFormatter.errorJSONResponse("Post does not exist");
        }
        post.setpostContent(postContent);
        postRepository.save(post);
        return ResponseEntity.ok("Post edited successfully");
    }

    // function for deleting a post
    @Transactional
    public ResponseEntity<Object> deletePost(int postID) {
        Post post = postRepository.findBypostID(postID);
        if (post == null) {
            return ErrorJSONFormatter.errorJSONResponse("Post does not exist");
        }

        commentRepository.deleteAllByCommentPost(post);
        postRepository.delete(post);
        return ResponseEntity.ok("Post deleted");
    }

    //function for get post by post_id
    public Post getPostBypostID(int postID) {
        return postRepository.findBypostID(postID);
    }

    // get all posts
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    //get the all comments of a post
    public List<Comment> getAllCommentsByPost(Post post) {
        return commentRepository.findAllByCommentPost(post);
    }



}
