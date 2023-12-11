/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import assistants.assistant;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedList;
import javaapplication1.models.RateMovie;

/**
 *
 * @author KL
 */
public class RateMovieController {

    public static void WriteMoviesRatingsToFile(LinkedList<RateMovie> rateMovies) {
        try {
            FileOutputStream fileOut = new FileOutputStream(assistant.MovieRatingFile);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(rateMovies); // Write the updated list to the file
            objectOut.close();
            System.out.println("RateMovie data has been written to " + assistant.MovieRatingFile);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    // Method to write RateMovie data to a file

    public static void writeMovieRatingToFile(RateMovie newRateMovie) {
        LinkedList<RateMovie> rateMovies = readMovieRatingsFromFile(); // Get existing data from file
        if (rateMovies == null) {
            rateMovies = new LinkedList<>(); // Initialize if file is empty or doesn't exist
        }

        // Check if the new RateMovie object matches any existing object by userId and movieId
        for (RateMovie existingRateMovie : rateMovies) {
            if (existingRateMovie.getUserId() == newRateMovie.getUserId()
                    && existingRateMovie.getMovieId() == newRateMovie.getMovieId()) {
                rateMovies.remove(existingRateMovie); // Remove existing object
                break; // Exit the loop after removing the first match
            }
        }

        rateMovies.add(newRateMovie); // Add the new RateMovie object

        try {
            FileOutputStream fileOut = new FileOutputStream(assistant.MovieRatingFile);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(rateMovies); // Write the updated list to the file
            objectOut.close();
            System.out.println("RateMovie data has been written to " + assistant.MovieRatingFile);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    // Method to read RateMovie data from a file and return as a List
    public static void deleteRateById(int id) {
        LinkedList<RateMovie> rateMovies = readMovieRatingsFromFile(); // Get existing data from file
        if (rateMovies == null) {
            rateMovies = new LinkedList<>(); // Initialize if file is empty or doesn't exist
        }

        // Check if the new RateMovie object matches any existing object by userId and movieId
        for (RateMovie existingRateMovie : rateMovies) {
            if (existingRateMovie.getId() == id) {
                rateMovies.remove(existingRateMovie); // Remove existing object
                break; // Exit the loop after removing the first match
            }
        }

        try {
            FileOutputStream fileOut = new FileOutputStream(assistant.MovieRatingFile);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(rateMovies); // Write the updated list to the file
            objectOut.close();
            System.out.println("RateMovie data has been written to " + assistant.MovieRatingFile);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    // Method to read RateMovie data from a file and return as a List
    public static void deleteRateByUserId(int id) {
        LinkedList<RateMovie> rateMovies = readMovieRatingsFromFile(); // Get existing data from file
        if (rateMovies == null) {
            rateMovies = new LinkedList<>(); // Initialize if file is empty or doesn't exist
        }

        // Check if the new RateMovie object matches any existing object by userId and movieId
        for (RateMovie existingRateMovie : rateMovies) {
            if (existingRateMovie.getUserId() == id) {
                rateMovies.remove(existingRateMovie); // Remove existing object
                break; // Exit the loop after removing the first match
            }
        }

        try {
            FileOutputStream fileOut = new FileOutputStream(assistant.MovieRatingFile);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(rateMovies); // Write the updated list to the file
            objectOut.close();
            System.out.println("RateMovie data has been written to " + assistant.MovieRatingFile);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void deleteRateByMovieId(int movieId) {
        LinkedList<RateMovie> rateMovies = readMovieRatingsFromFile(); // Get existing data from file
        if (rateMovies == null) {
            rateMovies = new LinkedList<>(); // Initialize if file is empty or doesn't exist
        }

        // Check if the new RateMovie object matches any existing object by userId and movieId
        for (RateMovie existingRateMovie : rateMovies) {
            if (existingRateMovie.getMovieId() == movieId) {
                rateMovies.remove(existingRateMovie); // Remove existing object
                break; // Exit the loop after removing the first match
            }
        }

        try {
            FileOutputStream fileOut = new FileOutputStream(assistant.MovieRatingFile);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(rateMovies); // Write the updated list to the file
            objectOut.close();
            System.out.println("RateMovie data has been written to " + assistant.MovieRatingFile);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static LinkedList<RateMovie> readMovieRatingsFromFile() {
        LinkedList<RateMovie> rateMovies = new LinkedList<>();
        try {
            FileInputStream fileIn = new FileInputStream(assistant.MovieRatingFile);
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            rateMovies = (LinkedList<RateMovie>) objectIn.readObject();
            objectIn.close();
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return rateMovies;
    }
    public static RateMovie getMovieRatingById(int RateMovieId){
    LinkedList<RateMovie>RateMovies=readMovieRatingsFromFile();
    return RateMovies.stream()
            .filter(seat -> seat.getId() == RateMovieId)
            .findFirst()
            .orElse(null);
    }
    public static void editRateMovie(RateMovie updatedRateMovie) throws IllegalArgumentException {
        LinkedList<RateMovie> rateMovies = readMovieRatingsFromFile(); // Read rate movies from file

        // Check for conflicts in the updated rate movie
        for (RateMovie rateMovie : rateMovies) {
            if (rateMovie.getMovieId() == updatedRateMovie.getMovieId()
                    && rateMovie.getUserId() == updatedRateMovie.getUserId()
                    && rateMovie.getId() != updatedRateMovie.getId()) {
                throw new IllegalArgumentException("RateMovie conflicts with an existing entry. Update cannot be performed.");
            }
        }

        // Find the rate movie with the provided ID and update details
        for (RateMovie rateMovie : rateMovies) {
            if (rateMovie.getId() == updatedRateMovie.getId()) {
                rateMovie.setMovieId(updatedRateMovie.getMovieId());
                rateMovie.setUserId(updatedRateMovie.getUserId());
                rateMovie.setComment(updatedRateMovie.getComment());
                rateMovie.setRating(updatedRateMovie.getRating());
                WriteMoviesRatingsToFile(rateMovies); // Write updated rate movie list back to the file
                return; // Stop searching once found and updated
            }
        }

        // Throw exception if rate movie not found for update
        throw new IllegalArgumentException("RateMovie not found for the provided ID. Update cannot be performed.");
    }

}
