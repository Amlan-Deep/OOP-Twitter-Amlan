
package com.amlan.ooptwitter.model;


import jakarta.persistence.*;

@Entity
@Table(name = "comments")
public class Comment {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "comment_id")
        private int commentID;

        @Column(name = "commentBody")
        private String commentBody;

        @Column(name = "commentDate")
        private String commentDate;

        @Column(name = "commentTime")
        private String commentTime;

        @ManyToOne
        @JoinColumn(name = "user_id")
        private User commentCreator;

        @ManyToOne
        @JoinColumn(name = "post_id")
        private Post commentPost;

        //setters
        public void setcommentID(int commentID) {
            this.commentID = commentID;
        }

        public void setCommentBody(String commentBody) {
            this.commentBody = commentBody;
        }

        public void setCommentDate(String commentDate) {
            this.commentDate = commentDate;
        }

        public void setCommentTime(String commentTime) {
            this.commentTime = commentTime;
        }

        public void setCommentCreator(User commentCreator) {
            this.commentCreator = commentCreator;
        }

        public void setCommentPost(Post commentPost) {
            this.commentPost = commentPost;
        }

        //getters

        public int getcommentID() {
            return commentID;
        }

        public String getCommentBody() {
            return commentBody;
        }

        public String getCommentDate() {
            return commentDate;
        }

        public String getCommentTime() {
            return commentTime;
        }

        public User getCommentCreator() {
            return commentCreator;
        }

        public Post getCommentPost() {
            return commentPost;
        }

        public Comment() {
        }

        public Comment(User commentCreator, Post commentPost,String commentBody, String commentDate, String commentTime) {
            this.commentBody = commentBody;
            this.commentDate = commentDate;
            this.commentTime = commentTime;
            this.commentCreator = commentCreator;
            this.commentPost = commentPost;
        }
}
