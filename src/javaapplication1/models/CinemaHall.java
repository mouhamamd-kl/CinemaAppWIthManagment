/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javaapplication1.models;

/**
 *
 * @author KL
 */
import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;

public class CinemaHall implements Serializable {

    private int id;
    private int numberOfRows;
    private int rowSeatNumbers;
    private String name;

    public CinemaHall(int id, int numberOfRows, int rowSeatNumbers, String name) {
        setId(id);
        setNumberOfRows(numberOfRows);
        setRowSeatNumbers(rowSeatNumbers);
        setName(name);
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumberOfRows() {
        return numberOfRows;
    }

    public void setNumberOfRows(int numberOfRows) {
        this.numberOfRows = numberOfRows;
    }

    public int getRowSeatNumbers() {
        return rowSeatNumbers;
    }

    public void setRowSeatNumbers(int rowSeatNumbers) {
        this.rowSeatNumbers = rowSeatNumbers;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "CinemaHall [id=" + id + ", numberOfRows=" + numberOfRows + ", rowSeatNumbers=" + rowSeatNumbers + ", name=" + name + "]";
    }
}
