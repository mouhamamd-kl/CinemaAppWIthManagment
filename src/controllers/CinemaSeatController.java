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
import javaapplication1.models.CinemaSeat;

/**
 *
 * @author KL
 */
public class CinemaSeatController {
    // Method to write CinemaSeat data to a file

    public static void writeSeatsToFile(LinkedList<CinemaSeat> cinemaSeats) {
        try {
            FileOutputStream fileOut = new FileOutputStream(assistant.CinemaSeatFile);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(cinemaSeats);
            objectOut.close();
            System.out.println("CinemaSeat data has been written to " + assistant.CinemaSeatFile);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void writeSeatToFile(CinemaSeat newCinemaSeat) {
        LinkedList<CinemaSeat> cinemaSeats = readSeatsFromFile();

        // Check if a seat with the same ID already exists in the CinemaHall
        for (CinemaSeat seat : cinemaSeats) {
            if (seat.getId() == newCinemaSeat.getId() && seat.getCinemaId() == newCinemaSeat.getCinemaId()) {
                // Throw an exception if a seat with the same ID and cinema exists
                throw new IllegalArgumentException("Seat with the same ID already exists in the CinemaHall.");
            }
        }

        // If no seat with the same ID and cinema exists, add the new seat and write to file
        cinemaSeats.add(newCinemaSeat);
        try {
            FileOutputStream fileOut = new FileOutputStream(assistant.CinemaSeatFile);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(cinemaSeats);
            objectOut.close();
            System.out.println("CinemaSeat data has been written to " + assistant.CinemaSeatFile);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    // Method to read CinemaSeat data from a file and return as a LinkedList
   public static CinemaSeat getCinemaSeatById(int Id) {
    LinkedList<CinemaSeat> cinemaSeats = readSeatsFromFile();
    return cinemaSeats.stream()
            .filter(seat -> seat.getId() == Id)
            .findFirst()
            .orElse(null);
}

    
    public static LinkedList<CinemaSeat> readSeatsFromFile() {
        LinkedList<CinemaSeat> cinemaSeats = new LinkedList<>();
        try {
            FileInputStream fileIn = new FileInputStream(assistant.CinemaSeatFile);
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            cinemaSeats = (LinkedList<CinemaSeat>) objectIn.readObject();
            objectIn.close();
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return cinemaSeats;
    }

    public static void deleteCinemaSeatsById(int id) {
        LinkedList<CinemaSeat> CinemaSeats = readSeatsFromFile(); // Get existing data from file

        // Check if the new RateMovie object matches any existing object by userId and movieId
        for (CinemaSeat cinemaSeat : CinemaSeats) {
            if (cinemaSeat.getId() == id) {
                CinemaSeats.remove(cinemaSeat);

                break; // Exit the loop after removing the first match
            }
        }

        try {
            FileOutputStream fileOut = new FileOutputStream(assistant.MovieRatingFile);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(CinemaSeats); // Write the updated list to the file
            objectOut.close();
            System.out.println("RateMovie data has been written to " + assistant.MovieRatingFile);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void deleteCinemaSeatsByCinemaId(int id) {
        LinkedList<CinemaSeat> CinemaSeats = readSeatsFromFile(); // Get existing data from file

        // Check if the new RateMovie object matches any existing object by userId and movieId
        for (CinemaSeat cinemaSeat : CinemaSeats) {
            if (cinemaSeat.getId() == id) {
                CinemaSeats.remove(cinemaSeat);

                break; // Exit the loop after removing the first match
            }
        }

        try {
            FileOutputStream fileOut = new FileOutputStream(assistant.MovieRatingFile);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(CinemaSeats); // Write the updated list to the file
            objectOut.close();
            System.out.println("RateMovie data has been written to " + assistant.MovieRatingFile);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void editCinemaSeat(CinemaSeat updatedCinemaSeat) throws IllegalArgumentException {
        LinkedList<CinemaSeat> cinemaSeats = readSeatsFromFile(); // Read cinema seats from file

        // Check for conflicts in the updated cinema seat
        for (CinemaSeat cinemaSeat : cinemaSeats) {
            if (cinemaSeat.getCinemaId() == updatedCinemaSeat.getCinemaId()
                    && cinemaSeat.getId() != updatedCinemaSeat.getId()) {
                throw new IllegalArgumentException("CinemaSeat conflicts with an existing entry. Update cannot be performed.");
            }
        }

        // Find the cinema seat with the provided ID and update details
        for (CinemaSeat cinemaSeat : cinemaSeats) {
            if (cinemaSeat.getId() == updatedCinemaSeat.getId()) {
                cinemaSeat.setCinemaId(updatedCinemaSeat.getCinemaId());
                writeSeatsToFile(cinemaSeats); // Write updated cinema seat list back to the file
                return; // Stop searching once found and updated
            }
        }

        // Throw exception if cinema seat not found for update
        throw new IllegalArgumentException("CinemaSeat not found for the provided ID. Update cannot be performed.");
    }
    
}
