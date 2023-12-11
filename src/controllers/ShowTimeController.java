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
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javaapplication1.models.ShowTime;
import javaapplication1.models.Ticket;

/**
 *
 * @author KL
 */
public class ShowTimeController {

    public static boolean ShowTimeEqualsShowTime(ShowTime sh, ShowTime sh1) {
        boolean isValidTimeShowTime = sh.getTime().equals(sh1.getTime());
        boolean isValidDateShowTime = sh.getDate().equals(sh1.getDate());

        return isValidTimeShowTime && isValidDateShowTime;
    }

    public static void writeShowTimeToFile(ShowTime newShowTime) {
        LinkedList<ShowTime> showTimes = readShowTimesFromFile();

        // Check if a movie is scheduled at the same time in the same cinema hall
        for (ShowTime showTime : showTimes) {
            if (ShowTimeEqualsShowTime(showTime, newShowTime) && showTime.getCinemaId() == newShowTime.getCinemaId()) {
                // Throw an exception if a movie at the same time in the same hall is found
                throw new IllegalArgumentException("Thre'es already a movie in the same time at this cinema hall.");
            }

        }

        // If no movie at the same time in the same hall exists, add the new show time and write to file
        showTimes.add(newShowTime);
        try {
            FileOutputStream fileOut = new FileOutputStream(assistant.ShowTimeFile);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(showTimes);
            objectOut.close();
            System.out.println("ShowTime data has been written to " + assistant.ShowTimeFile);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void writeShowTimesToFile(LinkedList<ShowTime> ShowTimes) {
        try {
            FileOutputStream fileOut = new FileOutputStream(assistant.MovieRatingFile);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(ShowTimes); // Write the updated list to the file
            objectOut.close();
            System.out.println("RateMovie data has been written to " + assistant.MovieRatingFile);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    // Method to read ShowTime data from a file and return as an array
    public static ShowTime getShowTimeById(int ShowTimeId) {
        LinkedList<ShowTime> ShowTimes = readShowTimesFromFile();
        return ShowTimes.stream()
                .filter(seat -> seat.getId() == ShowTimeId)
                .findFirst()
                .orElse(null);
    }

    public static LinkedList<ShowTime> readShowTimesFromFile() {
        LinkedList<ShowTime> showTimes = new LinkedList<>();
        try {
            FileInputStream fileIn = new FileInputStream(assistant.ShowTimeFile);
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            showTimes = (LinkedList<ShowTime>) objectIn.readObject();
            objectIn.close();
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return showTimes;
    }

    public static void deleteShowTimeById(int id) {
        LinkedList<ShowTime> ShowTimes = readShowTimesFromFile(); // Get existing data from file

        // Check if the new RateMovie object matches any existing object by userId and movieId
        for (ShowTime showTime : ShowTimes) {
            if (showTime.getId() == id) {
                ShowTimes.remove(showTime); // Remove existing object
                TicketController.RemoveTicketByShowTimeId(id);
                break; // Exit the loop after removing the first match
            }
        }
        writeShowTimesToFile(ShowTimes);

    }

    public static void deleteShowTimeByCinemaId(int cinemaId) {
        LinkedList<ShowTime> ShowTimes = readShowTimesFromFile(); // Get existing data from file

        // Check if the new RateMovie object matches any existing object by userId and movieId
        for (ShowTime showTime : ShowTimes) {
            if (showTime.getCinemaId() == cinemaId) {
                ShowTimes.remove(showTime); // Remove existing object
                break; // Exit the loop after removing the first match
            }
        }
        writeShowTimesToFile(ShowTimes);
    }

    public static void deleteShowTimeByMovieId(int movieId) {
        LinkedList<ShowTime> ShowTimes = readShowTimesFromFile(); // Get existing data from file

        // Check if the new RateMovie object matches any existing object by userId and movieId
        for (ShowTime showTime : ShowTimes) {
            if (showTime.getMovieId() == movieId) {
                ShowTimes.remove(showTime); // Remove existing object
                break; // Exit the loop after removing the first match
            }
        }

        writeShowTimesToFile(ShowTimes);
    }

    public static void editShowTimeByMovie(ShowTime updatedShowTime) {
        LinkedList<ShowTime> showTimes = readShowTimesFromFile(); // Get existing data from file

        boolean isConflict = showTimes.stream()
                .anyMatch(showTime -> showTime.getCinemaId() == updatedShowTime.getCinemaId()
                && showTime.getTime().equals(updatedShowTime.getTime())
                && showTime.getMovieId() != updatedShowTime.getMovieId());

        if (isConflict) {
            throw new IllegalArgumentException("Conflicting ShowTime entry detected. Choose a different time.");
        }

        // No conflict, update the ShowTime object
        for (ShowTime showTime : showTimes) {
            if (showTime.getId() == updatedShowTime.getId()) {
                // Update the existing ShowTime object with the new values
                showTime.setMovieId(updatedShowTime.getMovieId());
                showTime.setTime(updatedShowTime.getTime());
                showTime.setCinemaId(updatedShowTime.getCinemaId());
                break; // Exit loop after updating
            }
        }

        writeShowTimesToFile(showTimes); // Write the updated list back to the file
    }

    public static LinkedList<ShowTime> getMostShowTimeFromTopToWorst() {
        LinkedList<Ticket> tickets = TicketController.readTicketsFromFile();
        LinkedList<ShowTime> showTimes = readShowTimesFromFile();
        Map<Integer, Long> showTimeTicketCounts = tickets.stream().collect(Collectors.toMap(
                Ticket::getShowTimeId, // Key mapper: Extracts movieId
                t -> 1L, // Value mapper: Always returns 1 for each ticket
                Long::sum // Merge function: Sums the counts if keys are the same
        ));
        // Sort the showTimes based on the ticket counts from the map
        showTimes.sort(Comparator.comparingLong(showTime -> showTimeTicketCounts.getOrDefault(showTime.getId(), 0L)));
        Collections.reverse(showTimes); // Reverse to get the highest ticket count first
        return showTimes;
    }

    public static LinkedList<ShowTime> getMostShowTimeFromWorstToTop() {
        LinkedList<ShowTime> sh = getMostShowTimeFromTopToWorst();
        Collections.reverse(sh);
        return sh;
    }
}
