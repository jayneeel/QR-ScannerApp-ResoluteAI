package com.example.scanqr_jokeio.helper;

public class Joke {

    String title, joke, timestamp;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getJoke() {
        return joke;
    }

    public void setJoke(String joke) {
        this.joke = joke;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "Joke{" +
                "title='" + title + '\'' +
                ", joke='" + joke + '\'' +
                ", timestamp='" + timestamp + '\'' +
                '}';
    }

    public Joke(String title, String joke, String timestamp) {
        this.title = title;
        this.joke = joke;
        this.timestamp = timestamp;
    }

    public Joke() {
    }
}
