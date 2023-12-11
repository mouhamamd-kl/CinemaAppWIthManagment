/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package javaapplication1;

import com.formdev.flatlaf.FlatLightLaf;
import controllers.UserController;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;
import java.util.LinkedList;
import javaapplication1.models.User;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 *
 * @author KL
 */
public class JavaApplication1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
//          FlatLightLaf.setup(); // Set the look and feel to FlatLight.
//
//          // Create the main dashboard window.
//          JFrame frame = new JFrame("FlatLaf Dashboard");
//          frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//          frame.setSize(800, 600);
//          frame.setLocationRelativeTo(null);
//
//          // Create the menu.
//          JMenuBar menuBar = new JMenuBar();
//          JMenu menu = new JMenu("Menu");
//          menu.add(new JMenuItem("Item 1"));
//          menu.add(new JMenuItem("Item 2"));
//          menuBar.add(menu);
//          frame.setJMenuBar(menuBar);
//
//          // Create the main panel.
//          JPanel mainPanel = new JPanel();
//          mainPanel.add(new JLabel("Welcome to the FlatLaf Dashboard!"));
//          frame.getContentPane().add(mainPanel);
//
//          frame.setVisible(true);
        JFrame frame = new JFrame();
        Toolkit toolkit = Toolkit.getDefaultToolkit();

        // Get the screen size
        Dimension screenSize = toolkit.getScreenSize();

        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;
        frame.setUndecorated(true);
        frame.setShape(new RoundRectangle2D.Double(10, 10, 100, 100, 50, 50));
        frame.setSize(screenWidth, screenHeight);
        frame.setVisible(true);
    }
}
