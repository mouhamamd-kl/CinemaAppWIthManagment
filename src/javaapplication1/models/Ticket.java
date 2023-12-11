package javaapplication1.models;

import java.io.*;
import java.util.ArrayList;
import java.util.Objects;

import java.io.*;
import java.util.LinkedList;

public class Ticket implements Serializable {

    private int id;
    private int showTimeId;
    private int cinemaId;
    private int seatId;
    private int userId;
    private int ticketPrice;
    private int movieId;

    public Ticket(int id, int showTimeId, int cinemaId, int seatId, int userId, int movieId, int ticketPrice) {
        setId(id);
        setShowTimeId(showTimeId);
        setSeatId(seatId);
        setUserId(userId);
        setTicketPrice(ticketPrice);
        setCinemaId(cinemaId);
        setMovieId(movieId);
    }

    // Getters and Setters (omitted for brevity)
    // Getters and Setters
    public int getmovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.id = movieId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getShowTimeId() {
        return showTimeId;
    }

    public void setShowTimeId(int showTimeId) {
        this.showTimeId = showTimeId;
    }

    public int getCinemaId() {
        return cinemaId;
    }

    public void setCinemaId(int cinemaId) {
        this.cinemaId = cinemaId;
    }

    public int getSeatId() {
        return seatId;
    }

    public void setSeatId(int seatId) {
        this.seatId = seatId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(int ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    @Override
    public String toString() {
        return "Ticket [id=" + id + ", showTimeId=" + showTimeId + ", cinemaId=" + cinemaId + ", seatId=" + seatId + ", userId=" + userId + ", ticketPrice=" + ticketPrice + ", movieId=" + movieId + "]";
    }
}
