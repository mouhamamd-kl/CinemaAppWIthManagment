/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javaapplication1.models;

import java.util.Date;

/**
 *
 * @author KL
 */
import java.io.*;
import java.time.LocalDate;
import java.util.LinkedList;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class ShowTime implements Serializable {

    private int movieId;
    private int id;
    private LocalTime time;
    private LocalDate date;
    private int cinemaId;
//    private LocalDateTime created_at;
//    private LocalDateTime updated_at;
    public ShowTime(int movieId, int id, LocalTime time, int cinemaId, LocalDate date) {
        setMovieId(movieId);
        setId(id);
        setCinemaId(cinemaId);
        setDate(date);
        setTime(time);
    }

    // Getters and Setters
    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public int getCinemaId() {
        return cinemaId;
    }

    public void setCinemaId(int cinemaId) {
        this.cinemaId = cinemaId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "ShowTime [movieId=" + movieId + ", id=" + id + ", time=" + time + ", date=" + date + ", cinemaId=" + cinemaId + "]";
    }
}
