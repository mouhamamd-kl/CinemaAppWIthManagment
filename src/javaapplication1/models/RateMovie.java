/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javaapplication1.models;

/**
 *
 * @author KL
 */
import assistants.assistant;
import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class RateMovie implements Serializable {

    private int id;
    private int userId;
    private int movieId;
    private String comment;
    private RatingEnum rating;

    public RateMovie(int userId, int movieId, String comment, RatingEnum rating) {
        setId(id);
        setUserId(userId);
        setMovieId(movieId);
        setComment(comment);
        setRating(rating);
    }

    // Setters and Getters for userId, movieId, comment
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    // Setters and Getters for rating using Enum
    public RatingEnum getRating() {
        return rating;
    }

    public void setRating(RatingEnum rating) {
        this.rating = rating;
    }

    // Enum for movie ratings
    public enum RatingEnum {
        ONE_STAR, TWO_STARS, THREE_STARS, FOUR_STARS, FIVE_STARS
    }

    @Override
    public String toString() {
        return "RateMovie [id=" + id + ", userId=" + userId + ", movieId=" + movieId + ", comment=" + comment + ", rating=" + rating + "]";
    }
}
