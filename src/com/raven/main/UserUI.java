package com.raven.main;

import controllers.UserController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import javaapplication1.models.User;

public class UserUI extends JFrame {
    private DefaultTableModel tableModel;
    private JTable userTable;

    public UserUI() {
        initializeUI();
    }

    private void initializeUI() {
        setTitle("User Information");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);

        JPanel mainPanel = new JPanel(new BorderLayout());

        tableModel = new DefaultTableModel();
        tableModel.addColumn("ID");
        tableModel.addColumn("Name");
        tableModel.addColumn("Email");

        userTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(userTable);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout());

        JButton addButton = new JButton("Add User");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addUser();
            }
        });
        buttonPanel.add(addButton);

        JButton deleteButton = new JButton("Delete User");
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteUser();
            }
        });
        buttonPanel.add(deleteButton);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
        setVisible(true);

        // Populate the table with existing users
        displayAllUsers();
    }

    private void displayAllUsers() {
        LinkedList<User> users = UserController.readUsersFromFile();

        for (User user : users) {
            Object[] rowData = {user.getId(), user.getName(), user.getEmail()};
            tableModel.addRow(rowData);
        }
    }

    private void addUser() {
        // You can implement adding a new user functionality here
        // For example, open a dialog to get user details and add to the table
        // After adding, also add the user to the file using UserController

        // Example:
        String id = JOptionPane.showInputDialog("Enter ID:");
        String name = JOptionPane.showInputDialog("Enter Name:");
        String email = JOptionPane.showInputDialog("Enter Email:");
        String password = JOptionPane.showInputDialog("Enter Password:");

//         Create a User object and add it to the table and save it using UserController
         User user = new User(Integer.parseInt(id), name, email, password);
         tableModel.addRow(new Object[]{user.getId(), user.getName(), user.getEmail()});
         UserController.writeUserToFile(user);
    }

    private void deleteUser() {
        int selectedRow = userTable.getSelectedRow();

        if (selectedRow >= 0) {
            int userId = (int) userTable.getValueAt(selectedRow, 0); // Assuming ID is in the first column
            tableModel.removeRow(selectedRow);

            // You can implement deleting a user from file using UserController here
            // Example: UserController.deleteUserById(userId);
        } else {
            JOptionPane.showMessageDialog(this, "Please select a user to delete.");
        }
    }
}