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
import java.util.LinkedList;
import javaapplication1.models.ShowTime;
import javaapplication1.models.Ticket;

/**
 *
 * @author KL
 */
public class TicketController {

    // Verification method to ensure ticket uniqueness before adding to file
    public static boolean isTicketUnique(LinkedList<Ticket> tickets, Ticket newTicket) {
        for (Ticket ticket : tickets) {
            if (ticket.getCinemaId() == newTicket.getCinemaId()
                    && ticket.getShowTimeId() == newTicket.getShowTimeId()
                    && ticket.getSeatId() == newTicket.getSeatId()) {
                return false; // Ticket with same seat, cinema, and time already exists
            }
        }
        return true; // Ticket is unique
    }

    public static boolean CurrentTimeIsBeforeShowTime(ShowTime sh) {
        boolean isValidTimeShowTime = LocalTime.now().isBefore(sh.getTime());
        boolean isValidDateShowTime = LocalDate.now().isBefore(sh.getDate());

        return isValidTimeShowTime && isValidDateShowTime;
    }

    // Method to add a ticket to the file after verification
    public static void addTicketToFile(Ticket newTicket) {
        LinkedList<Ticket> tickets = readTicketsFromFile();
        ShowTime sh = ShowTimeController.getShowTimeById(newTicket.getShowTimeId());
        if (!CurrentTimeIsBeforeShowTime(sh)) {
            throw new IllegalArgumentException("Can't Book a Ticket for an already started movie");

        }
        if (!isTicketUnique(tickets, newTicket)) {
            throw new IllegalArgumentException("the ticket is not unique cant book a movie for this seat");

        }

        tickets.add(newTicket);
        try {

            FileOutputStream fileOut = new FileOutputStream(assistant.TicketFile);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(tickets);
            objectOut.close();
            System.out.println("Ticket added to file: " + assistant.TicketFile);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    // Method to add a ticket to the file after verification
    public static void writeTicketsToFile(LinkedList<Ticket> tickets) {

        try {
            FileOutputStream fileOut = new FileOutputStream(assistant.TicketFile);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(tickets);
            objectOut.close();
            System.out.println("Ticket added to file: " + assistant.TicketFile);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    // Method to read tickets from the file
    public static LinkedList<Ticket> readTicketsFromFile() {
        LinkedList<Ticket> tickets = new LinkedList<>();
        try {
            FileInputStream fileIn = new FileInputStream(assistant.TicketFile);
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            tickets = (LinkedList<Ticket>) objectIn.readObject();
            objectIn.close();
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return tickets;
    }

    public static void RemoveTicketById(Ticket t) {
        LinkedList<Ticket> tickets = readTicketsFromFile(); // Read users from file

        // Find the user with the provided ID
        for (Ticket ticket : tickets) {
            if (ticket.getId() == t.getId()) {
                tickets.remove(ticket);

            }
        }

        writeTicketsToFile(tickets); // Write updated user list back to the file
    }

    public static void RemoveTicketByUserId(int userId) {
        LinkedList<Ticket> tickets = readTicketsFromFile(); // Read users from file
        // Find the user with the provided ID
        for (Ticket ticket : tickets) {
            ShowTime sh = ShowTimeController.getShowTimeById(ticket.getShowTimeId());
            boolean isValidTimeShowTime = LocalTime.now().isBefore(sh.getTime());
            boolean isValidDateShowTime = LocalDate.now().isBefore(sh.getDate());
            if (!isValidTimeShowTime || !isValidDateShowTime) {
                throw new IllegalArgumentException("Can't cancel the ticket the movie has alread started");
            }
            if (ticket.getUserId() == userId) {
                tickets.remove(ticket);
            }
        }

        writeTicketsToFile(tickets); // Write updated user list back to the file
    }

    public static void RemoveTicketByMovieId(int MovieId) {
        LinkedList<Ticket> tickets = readTicketsFromFile(); // Read users from file
        LinkedList<ShowTime> showTimes = ShowTimeController.readShowTimesFromFile();
        ShowTime m = null;
        for (ShowTime st : showTimes) {
            if (st.getMovieId() == MovieId) {
                m = st;
            }
        }
        // Find the user with the provided ID
        if (m != null) {
            for (Ticket ticket : tickets) {
                if (ticket.getShowTimeId() == m.getId()) {
                    tickets.remove(ticket);

                }
            }
        }

        writeTicketsToFile(tickets); // Write updated user list back to the file
    }

    public static void RemoveTicketByShowTimeId(int showTimeId) {
        LinkedList<Ticket> tickets = readTicketsFromFile(); // Read users from file

        // Find the user with the provided ID
        for (Ticket ticket : tickets) {
            if (ticket.getShowTimeId() == showTimeId) {
                tickets.remove(ticket);

            }
        }

        writeTicketsToFile(tickets); // Write updated user list back to the file
    }

    public static void RemoveTicketByCinemaId(int CinemaId) {
        LinkedList<Ticket> tickets = readTicketsFromFile(); // Read users from file
        LinkedList<ShowTime> showTimes = ShowTimeController.readShowTimesFromFile();
        ShowTime m = null;
        for (ShowTime st : showTimes) {
            if (st.getCinemaId() == CinemaId) {
                m = st;
            }
        }
        // Find the user with the provided ID
        if (m != null) {
            for (Ticket ticket : tickets) {
                if (ticket.getShowTimeId() == m.getId()) {
                    tickets.remove(ticket);

                }
            }
        }

        writeTicketsToFile(tickets); // Write updated user list back to the file
    }

    public static void editTicketForUser(Ticket updatedTicket) throws IllegalArgumentException {
        LinkedList<Ticket> tickets = readTicketsFromFile(); // Read tickets from file

        // Check for conflicts in the updated ticket
        for (Ticket ticket : tickets) {
            if (ticket.getId() != updatedTicket.getId()
                    && ticket.getCinemaId() == updatedTicket.getCinemaId()
                    && ticket.getShowTimeId() == updatedTicket.getShowTimeId()
                    && ticket.getSeatId() == updatedTicket.getSeatId()) {
                throw new IllegalArgumentException("Ticket conflicts with an existing ticket. Update cannot be performed.");
            }
        }

        // Find the ticket with the provided ID and update details
        for (Ticket ticket : tickets) {
            if (ticket.getId() == updatedTicket.getId()) {
                ticket.setShowTimeId(updatedTicket.getShowTimeId());
                ticket.setCinemaId(updatedTicket.getCinemaId());
                ticket.setSeatId(updatedTicket.getSeatId());
                ticket.setUserId(updatedTicket.getUserId());
                ticket.setTicketPrice(updatedTicket.getTicketPrice());
                writeTicketsToFile(tickets); // Write updated ticket list back to the file
                return; // Stop searching once found and updated
            }
        }

        // Throw exception if ticket not found for update
        throw new IllegalArgumentException("Ticket not found for the provided ID. Update cannot be performed.");
    }

    public static Ticket getTicketById(int TicketId) {
        LinkedList<Ticket> Ticketes = readTicketsFromFile();
        return Ticketes.stream()
                .filter(seat -> seat.getId() == TicketId)
                .findFirst()
                .orElse(null);
    }
}
