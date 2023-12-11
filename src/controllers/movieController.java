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
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javaapplication1.models.CinemaHall;
import javaapplication1.models.Movie;
import javaapplication1.models.RateMovie;
import javaapplication1.models.RateMovie.RatingEnum;
import javaapplication1.models.ShowTime;
import javaapplication1.models.Ticket;

/**
 *
 * @author KL
 */
public class movieController {
    // Method to write Movie data to a file

    public static void writeMoviesToFile(LinkedList<Movie> movies) {
        try {
            FileOutputStream fileOut = new FileOutputStream(assistant.movieFile);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(movies);
            objectOut.close();
            System.out.println("Movie data has been written to " + assistant.movieFile);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    // Method to read Movie data from a file and return as an array
    public static LinkedList<Movie> readMoviesFromFile() {
        LinkedList<Movie> movies = new LinkedList<>();
        try {
            FileInputStream fileIn = new FileInputStream(assistant.movieFile);
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            movies = (LinkedList<Movie>) objectIn.readObject();
            objectIn.close();
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return movies;
    }

    public static Movie getMovieById(int MovieId) {
        LinkedList<Movie> RateMovies = readMoviesFromFile();
        return RateMovies.stream()
                .filter(seat -> seat.getId() == MovieId)
                .findFirst()
                .orElse(null);
    }

    public static void deleteMovieById(int id) {
        LinkedList<Movie> Movies = readMoviesFromFile(); // Get existing data from file

        // Check if the new RateMovie object matches any existing object by userId and movieId
        for (Movie movie : Movies) {
            if (movie.getId() == id) {
                Movies.remove(movie);
                ShowTimeController.deleteShowTimeByMovieId(id);
                RateMovieController.deleteRateByMovieId(id);
                break; // Exit the loop after removing the first match
            }
        }

        try {
            FileOutputStream fileOut = new FileOutputStream(assistant.MovieRatingFile);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(Movies); // Write the updated list to the file
            objectOut.close();
            System.out.println("RateMovie data has been written to " + assistant.MovieRatingFile);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    // Method to calculate average ratings for each movie
    public static Map<Integer, Double> calculateAverageRatings(List<RateMovie> rateMovies) {
        Map<Integer, List<RateMovie>> movieRatings = rateMovies.stream()
                .collect(Collectors.groupingBy(RateMovie::getMovieId));

        Map<Integer, Double> averageRatings = new HashMap<>();
        for (Map.Entry<Integer, List<RateMovie>> entry : movieRatings.entrySet()) {
            int movieId = entry.getKey();
            List<RateMovie> ratings = entry.getValue();

            double averageRating = ratings.stream()
                    .mapToInt(rate -> rate.getRating().ordinal() + 1)
                    .average()
                    .orElse(0.0); // Default if no ratings found

            averageRatings.put(movieId, averageRating);
        }
        return averageRatings;
    }

    public static LinkedList<Movie> sortMoviesFromWorstToBest() {
        LinkedList<Movie> Movies = sortMoviesFromBestToWorst();
        Collections.reverse(Movies);
        return Movies;
    }

    public static LinkedList<Movie> sortMoviesFromBestToWorst() {
        // Calculate average ratings for each movie
        LinkedList<RateMovie> rateMovies = RateMovieController.readMovieRatingsFromFile();
        LinkedList<Movie> movies = readMoviesFromFile();
        Map<Integer, Double> averageRatings = calculateAverageRatings(rateMovies);

        // Sort movies based on average ratings
        movies.sort(Comparator.comparingDouble(movie -> averageRatings.getOrDefault(movie.getId(), 0.0)));
        return movies;
    }

    public static LinkedList<RateMovie> getRatingsForMovie(int movieId) {
        LinkedList<RateMovie> rateMovies = RateMovieController.readMovieRatingsFromFile();
        LinkedList<RateMovie> ratingsForMovie = new LinkedList<>();
        for (RateMovie rateMovie : rateMovies) {
            if (rateMovie.getMovieId() == movieId) {
                ratingsForMovie.add(rateMovie);
            }
        }
        return ratingsForMovie;
    }

    public static LinkedList<ShowTime> getMovieShowTimes(int movieId) {
        LinkedList<ShowTime> showTimes = ShowTimeController.readShowTimesFromFile();
        LinkedList<ShowTime> showTimesForMovie = new LinkedList<>();
        for (ShowTime rm : showTimes) {
            if (rm.getMovieId() == movieId) {
                showTimesForMovie.add(rm);
            }
        }
        return showTimesForMovie;
    }

    public static LinkedList<CinemaHall> getMovieCinemaHalls(int movieId) {
        LinkedList<ShowTime> showTimesForMovie = getMovieShowTimes(movieId);
        LinkedList<CinemaHall> cinemaHalls = CinemaHallController.readCinemaHallsFromFile();
        LinkedList<CinemaHall> movieCinemaHalls = new LinkedList<>();
        for (ShowTime sh : showTimesForMovie) {
            for (CinemaHall cinemaHall : cinemaHalls) {
                if (sh.getCinemaId() == cinemaHall.getId()) {
                    movieCinemaHalls.add(cinemaHall);
                }
            }
        }
        return movieCinemaHalls;
    }

    public static LinkedList<Ticket> getTicketsForMovie(int movieId) {
        LinkedList<Ticket> tickets = TicketController.readTicketsFromFile();
        LinkedList<ShowTime> showTimesForMovie = getMovieShowTimes(movieId);
        LinkedList<Ticket> ticketsForMovie = new LinkedList<>();
        for (Ticket ticket : tickets) {
            for (ShowTime rm : showTimesForMovie) {
                if (ticket.getShowTimeId() == rm.getId()) {
                    ticketsForMovie.add(ticket);
                }
            }

        }
        return ticketsForMovie;
    }

    public static LinkedList<Movie> sortMoviesByTicketsFromMostWatchedFirst(LinkedList<Ticket> tickets, LinkedList<Movie> movies) {
        // Step 1: Count tickets sold per movie
        Map<Integer, Long> movieTicketCounts = tickets.stream()
                .collect(Collectors.groupingBy(Ticket::getmovieId, Collectors.counting()));

        // Step 2: Sort movies based on ticket counts (most watched)
        movies.sort(Comparator.comparingLong(movie -> movieTicketCounts.getOrDefault(movie.getId(), 0L)));
        Collections.reverse(movies); // Reverse to get most watched movies first

        return movies;
    }

    public static LinkedList<Movie> sortMoviesByTicketsFromLeastWatchedFirst(LinkedList<Ticket> tickets, LinkedList<Movie> movies) {
        LinkedList<Movie> least = sortMoviesByTicketsFromMostWatchedFirst(tickets, movies);
        Collections.reverse(least);
        return least;
    }
}
