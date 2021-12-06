package com.example.bitguess.Models;

import java.time.LocalDateTime;

public class Tweet {

    private String id;
    private String user;
    private String fullName;
    private String url;
    private LocalDateTime timeStamp;
    private int replies;
    private int likes;
    private int retweets;
    private String text;
    private int Sentiment;

    public Tweet(String id, String user, String fullName, String url, LocalDateTime timeStamp, int replies, int likes, int retweets, String text, int sentiment) {
        this.id = id;
        this.user = user;
        this.fullName = fullName;
        this.url = url;
        this.timeStamp = timeStamp;
        this.replies = replies;
        this.likes = likes;
        this.retweets = retweets;
        this.text = text;
        Sentiment = sentiment;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }

    public int getReplies() {
        return replies;
    }

    public void setReplies(int replies) {
        this.replies = replies;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getRetweets() {
        return retweets;
    }

    public void setRetweets(int retweets) {
        this.retweets = retweets;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getSentiment() {
        return Sentiment;
    }

    public void setSentiment(int sentiment) {
        Sentiment = sentiment;
    }

    @Override
    public String toString() {
        return text;
    }
}
