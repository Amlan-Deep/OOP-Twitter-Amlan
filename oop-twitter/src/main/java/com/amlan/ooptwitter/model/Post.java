package com.amlan.ooptwitter.model;
import jakarta.persistence.*;

@Entity
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private int postID;

    @Column(name = "postContent")
    private String postContent;

    @Column(name = "postDate")
    private String postDate;

    @Column(name = "postTime")
    private String postTime;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User postCreator;

    //setters
    public void setpostID(int postID) {

        this.postID = postID;
    }

    public void setpostContent(String postContent) {
        this.postContent = postContent;
    }

    public void setPostDate(String postDate) {

        this.postDate = postDate;
    }

    public void setPostTime(String postTime) {
        this.postTime = postTime;
    }

    public void setPostCreator(User postCreator) {

        this.postCreator = postCreator;
    }

    //getters

    public int getpostID() {
        return postID;
    }

    public String getpostContent() {

        return postContent;
    }

    public String getPostDate() {

        return postDate;
    }

    public String getPostTime() {

        return postTime;
    }

    public User getPostCreator() {
        return postCreator;
    }
    public Post() {
    }
    public Post(User postCreator,String postContent, String postDate, String postTime) {
        this.postContent = postContent;
        this.postDate = postDate;
        this.postTime = postTime;
        this.postCreator = postCreator;
    }

}
