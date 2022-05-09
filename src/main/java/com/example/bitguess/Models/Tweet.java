package com.example.bitguess.Models;

import java.time.LocalDateTime;

public class Tweet {
    private LocalDateTime date;
    private String text;
    private String sentiment;

    public Tweet(LocalDateTime date, String text, String sentiment) {
        this.date = date;
        this.text = text;
        this.sentiment = sentiment;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getSentiment() {
        return sentiment;
    }

    public void setSentiment(String sentiment) {
        this.sentiment = sentiment;
    }

    @Override
    public String toString() {
        return text;
    }
}
