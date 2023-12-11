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
import java.time.Duration;
import java.util.LinkedList;

public class Movie implements Serializable {

    private int id;
    private String name;
    private Duration duration;
    
    public Movie(int id, String name, Duration duration) {
        setId(id);
        setName(name);
        setDuration(duration);
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "Movie [id=" + id + ", name=" + name + ", duration=" + duration + "]";
    }
}
