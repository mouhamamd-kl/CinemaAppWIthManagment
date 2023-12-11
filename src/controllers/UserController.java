package controllers;

import assistants.assistant;
import java.io.*;
import java.util.LinkedList;
import javaapplication1.models.Ticket;
import javaapplication1.models.User;

public class UserController {

    public static boolean VerificationLogIn(User user) {
        boolean isVerified = false;
        LinkedList<User> users = readUsersFromFile();

        return true;
    }

    public static void writeUserToFile(User user) {
        LinkedList<User> users = readUsersFromFile();
        for (User us : users) {
            if (us.getEmail().equals(user.getEmail())) {
                throw new IllegalArgumentException("Email Already been used");
            }
        }
        users.add(user);
        try {
            FileOutputStream fileOut = new FileOutputStream(assistant.userFile);
            try (ObjectOutputStream objectOut = new ObjectOutputStream(fileOut)) {
                objectOut.writeObject(users);
            }
            System.out.println("User data has been written to " + assistant.userFile);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void writeUsersToFile(LinkedList<User> users) {
        try {
            FileOutputStream fileOut = new FileOutputStream(assistant.userFile);
            try (ObjectOutputStream objectOut = new ObjectOutputStream(fileOut)) {
                objectOut.writeObject(users);
            }
            System.out.println("User data has been written to " + assistant.userFile);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    // Method to read User info from a file and return as a LinkedList
    public static LinkedList<User> readUsersFromFile() {
        LinkedList<User> users = new LinkedList<>();
        try {
            FileInputStream fileIn = new FileInputStream(assistant.userFile);
            try (ObjectInputStream objectIn = new ObjectInputStream(fileIn)) {
                users = (LinkedList<User>) objectIn.readObject();
            }
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return users;
    }

    public static LinkedList<Ticket> getTicketsForUser(int userId) {
        LinkedList<Ticket> tickets = TicketController.readTicketsFromFile();
        LinkedList<Ticket> ticketsForUser = new LinkedList<>();
        for (Ticket ticket : tickets) {

            if (ticket.getUserId() == userId) {
                ticketsForUser.add(ticket);
            }

        }
        return ticketsForUser;
    }
}
