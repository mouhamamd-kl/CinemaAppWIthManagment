/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javaapplication1.models;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 *
 * @author KL
 */
public class CinemaSeat implements Serializable {

    private int id;
    private int cinemaId;

    public CinemaSeat(int id, int cinemaId) {
        setId(id);
        setCinemaId(cinemaId);
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCinemaId() {
        return cinemaId;
    }

    public void setCinemaId(int cinemaId) {
        this.cinemaId = cinemaId;
    }

    @Override
    public String toString() {
        return "CinemaSeat [id=" + id + ", cinemaId=" + cinemaId + "]";
    }
}
