/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import assistants.assistant;
import static controllers.ShowTimeController.readShowTimesFromFile;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedList;
import javaapplication1.models.CinemaHall;
import javaapplication1.models.CinemaSeat;
import javaapplication1.models.ShowTime;
import javaapplication1.models.Ticket;

/**
 *
 * @author KL
 */
public class CinemaHallController {

    // Method to write CinemaHall data to a file
    public static void writeCinemaHallsToFile(LinkedList<CinemaHall> cinemaHalls) {
        try {
            FileOutputStream fileOut = new FileOutputStream(assistant.CinemaFile);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(cinemaHalls);
            objectOut.close();
            System.out.println("Cinema hall data has been written to " + assistant.CinemaFile);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void writeCinemaHallToFile(CinemaHall newCinemaHall) {
        LinkedList<CinemaHall> cinemaHalls = readCinemaHallsFromFile();

        // Check if a cinema with the same name already exists
        for (CinemaHall hall : cinemaHalls) {
            if (hall.getName().equals(newCinemaHall.getName())) {
                // Throw an exception if a cinema with the same name is found
                throw new IllegalArgumentException("Cinema with the same name already exists.");
            }
        }

        // If no cinema with the same name exists, add the new cinema and write to file
        cinemaHalls.add(newCinemaHall);
        try {
            FileOutputStream fileOut = new FileOutputStream(assistant.CinemaFile);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(cinemaHalls);
            objectOut.close();
            System.out.println("Cinema hall data has been written to " + assistant.CinemaFile);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    // Method to read CinemaHall data from a file and return as an array
    public static CinemaHall getCinemaHallById(int CinemaHallId){
    LinkedList<CinemaHall>cinemaHalls=readCinemaHallsFromFile();
    return cinemaHalls.stream()
            .filter(seat -> seat.getId() == CinemaHallId)
            .findFirst()
            .orElse(null);
    }
    public static LinkedList<CinemaHall> readCinemaHallsFromFile() {
        LinkedList<CinemaHall> cinemaHalls = new LinkedList<>();
        try {
            FileInputStream fileIn = new FileInputStream(assistant.CinemaFile);
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            cinemaHalls = (LinkedList<CinemaHall>) objectIn.readObject();
            objectIn.close();
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return cinemaHalls;
    }
    // Method to read RateMovie data from a file and return as a List

    public static void deleteCinemaHall(int id) {
        LinkedList<CinemaHall> CinemaHalls = readCinemaHallsFromFile(); // Get existing data from file

        // Check if the new RateMovie object matches any existing object by userId and movieId
        for (CinemaHall cinemaHall : CinemaHalls) {
            if (cinemaHall.getId() == id) {
                CinemaHalls.remove(cinemaHall);
                TicketController.RemoveTicketByCinemaId(cinemaHall.getId());// Remove existing object
                ShowTimeController.deleteShowTimeByCinemaId(id);
                CinemaSeatController.deleteCinemaSeatsByCinemaId(id);
                break; // Exit the loop after removing the first match
            }
        }

        try {
            FileOutputStream fileOut = new FileOutputStream(assistant.MovieRatingFile);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(CinemaHalls); // Write the updated list to the file
            objectOut.close();
            System.out.println("RateMovie data has been written to " + assistant.MovieRatingFile);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void editCinemaHall(CinemaHall updatedCinemaHall) throws IllegalArgumentException {
        LinkedList<CinemaHall> cinemaHalls = readCinemaHallsFromFile(); // Read cinema halls from file

        // Check for conflicts in the updated cinema hall
        for (CinemaHall cinemaHall : cinemaHalls) {
            if (cinemaHall.getName().equals(updatedCinemaHall.getName())
                    && cinemaHall.getId() != updatedCinemaHall.getId()) {
                throw new IllegalArgumentException("CinemaHall conflicts with an existing entry. Update cannot be performed.");
            }
        }

        // Find the cinema hall with the provided ID and update details
        for (CinemaHall cinemaHall : cinemaHalls) {
            if (cinemaHall.getId() == updatedCinemaHall.getId()) {
                cinemaHall.setName(updatedCinemaHall.getName());
                cinemaHall.setNumberOfRows(updatedCinemaHall.getNumberOfRows());
                cinemaHall.setRowSeatNumbers(updatedCinemaHall.getRowSeatNumbers());
                writeCinemaHallsToFile(cinemaHalls); // Write updated cinema hall list back to the file
                return; // Stop searching once found and updated
            }
        }

        // Throw exception if cinema hall not found for update
        throw new IllegalArgumentException("CinemaHall not found for the provided ID. Update cannot be performed.");
    }

    public static LinkedList<ShowTime> getShowTimesForCinemaHall(int CinemaId) {
        LinkedList<ShowTime> ShowTimes = readShowTimesFromFile();
        LinkedList<ShowTime> ShowTimesForCinema = new LinkedList<>();
        for (ShowTime sh : ShowTimes) {
            if (sh.getCinemaId() == CinemaId) {
                ShowTimesForCinema.add(sh);
            }
        }
        return ShowTimesForCinema;
    }

    public static LinkedList<CinemaSeat> getCinemaSeatForCinemaHall(int CinemaId) {
        LinkedList<CinemaSeat> cinemaSeats = new LinkedList<>();
        LinkedList<CinemaSeat> cinemaSeatsForCinema = new LinkedList<>();
        for (CinemaSeat cS : cinemaSeats) {
            if (cS.getCinemaId() == CinemaId) {
                cinemaSeatsForCinema.add(cS);
            }
        }
        return cinemaSeatsForCinema;
    }

    public static LinkedList<ShowTime> getCinemaShowTimes(int CinemaId) {
        LinkedList<ShowTime> showTimes = ShowTimeController.readShowTimesFromFile();
        LinkedList<ShowTime> showTimesForCinema = new LinkedList<>();
        for (ShowTime rm : showTimes) {
            if (rm.getCinemaId() == CinemaId) {
                showTimesForCinema.add(rm);
            }
        }
        return showTimesForCinema;
    }
    public static LinkedList<CinemaSeat>getAvailableSeatsForCinemaAndMovie(int CinemaId,int MovieId,int ShowTimeId){
    LinkedList<CinemaSeat>CinemaSeats=getCinemaSeatForCinemaHall(CinemaId);
    LinkedList<Ticket> tickets = TicketController.readTicketsFromFile();
    LinkedList<CinemaSeat>notAvailableSeats=new LinkedList<>();
    
        for (Ticket t:tickets) {
            if(t.getCinemaId()==CinemaId&&t.getmovieId()==MovieId&&t.getShowTimeId()==ShowTimeId){
            CinemaSeat s=CinemaSeatController.getCinemaSeatById(t.getSeatId());
            notAvailableSeats.add(s);
            }
        }
       CinemaSeats.removeAll(notAvailableSeats);
        return CinemaSeats;
    }
}
