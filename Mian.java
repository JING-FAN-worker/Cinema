import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import org.json.JSONArray;
import org.json.JSONObject;

public class Main {
    private static Theater theater;
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        theater = new Theater("LUT Kino", "Yliopistonkatu", 11, 10);
        while (true) {
            System.out.println("Welcome to the " + theater.getName() + " at " + theater.getLocation() + "!");
            System.out.println(
                    "1. Add a movie\n2. Remove a movie\n3. View a movie\n4. View all movies\n5. Add a showtime\n6. Remove a showtime\n7. View a showtime\n8. View all showtimes\n9. Buy a ticket\n10. View seating\n0. Exit");
            System.out.print("Enter your choice: ");
            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1":
                    addMovie();
                    break;
                case "2":
                    removeMovie();
                    break;
                case "3":
                    viewMovie();
                    break;
                case "4":
                    viewAllMovies();
                    break;
                case "5":
                    addShowtime();
                    break;
                case "6":
                    removeShowtime();
                    break;
                case "7":
                    viewShowtime();
                    break;
                case "8":
                    viewAllShowtimes();
                    break;
                case "9":
                    buyTicket();
                    break;
                case "10":
                    viewSeating();
                    break;
                case "0":
                    System.out.println("Goodbye!");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice.");
                    break;
            }
        }
    }

    public static JSONObject searchMovie(String title) throws Exception {
        String encodedQuery = URLEncoder.encode(title, "UTF-8");
        String urlString = "https://api.themoviedb.org/3/search/movie?api_key=20fc84e5979af3db77d713b4359fe1ad&query="
                + encodedQuery;
        URL obj = new URL(urlString);

        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        int responseCode = con.getResponseCode();
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        JSONObject jsonResponse = new JSONObject(response.toString());
        return jsonResponse;
    }

    public static JSONObject getMovieObj(int id) throws Exception {
        String urlString = "https://api.themoviedb.org/3/movie/" + id + "?api_key=20fc84e5979af3db77d713b4359fe1ad";
        URL obj = new URL(urlString);

        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        int responseCode = con.getResponseCode();
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        JSONObject jsonResponse = new JSONObject(response.toString());
        return jsonResponse;
    }

    private static void addMovie() {
        System.out.print("Enter the movie title: ");
        String title = scanner.nextLine().trim();
        System.out.print("Enter the director: ");
        String director = scanner.nextLine().trim();
        System.out.print("Enter the duration: ");
        int duration = scanner.nextInt();
        scanner.nextLine();

        try {
            JSONObject obj = searchMovie(title);
            JSONArray results = obj.getJSONArray("results");
            if (results.length() == 0) {
                System.out.println("No results found.");
                return;
            }
            int id = results.getJSONObject(0).getInt("id");
            JSONObject movieObj = getMovieObj(id);
            String review = movieObj.getString("overview");
            String releaseYear = movieObj.getString("release_date");
            double rating = movieObj.getDouble("vote_average");
            int voteCount = movieObj.getInt("vote_count");
            JSONArray genresObjs = movieObj.getJSONArray("genres");
            ArrayList<String> genres = new ArrayList<String>();
            for (int i = 0; i < genresObjs.length(); i++) {
                genres.add(genresObjs.getJSONObject(i).getString("name"));
            }
            Movie movie = new Movie(title, director, releaseYear, review, genres, duration, rating, voteCount);
            theater.addMovie(movie);
            System.out.println("Movie added successfully!");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    static void removeMovie() {
        System.out.print("Enter the movie title: ");
        String title = scanner.nextLine();
        Movie movie = theater.removeMovie(title);
        if (movie == null) {
            System.out.println("Movie not found!");
        } else {
            System.out.println("Movie removed!");
        }
    }

    static void viewMovie() {
        System.out.println("Enter the movie title: ");
        String title = scanner.nextLine();
        Movie movie = theater.findMovie(title);
        if (movie == null) {
            System.out.println("Movie not found!");
        } else {
            System.out.println(movie);
        }
    }

    static void viewAllMovies() {
        ArrayList<Movie> movies = theater.getMovies();
        for (Movie movie : movies) {
            System.out.println(movie);
            System.out.println();
        }
    }

    static void addShowtime() {
        System.out.print("Enter the movie title: ");
        String title = scanner.nextLine();
        Movie foundMovie = theater.findMovie(title);
        if (foundMovie != null) {
            LocalTime time;
            while (true) {
                System.out.print("Enter the time of the showtime (e.g. 7:00 PM): ");
                String timeStr = scanner.nextLine();
                try {
                    time = LocalTime.parse(timeStr, DateTimeFormatter.ofPattern("h:mm a"));
                    break;
                } catch (DateTimeParseException e) {
                    System.out.println("Invalid time format. Please enter time in the format of 7:00 PM.");
                }
            }

            LocalDate date;
            while (true) {
                System.out.print("Enter the date of the showtime (e.g. 2022-12-15): ");
                String dateStr = scanner.nextLine();
                try {
                    date = LocalDate.parse(dateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    break;
                } catch (DateTimeParseException e) {
                    System.out.println("Invalid date format. Please enter date in yyyy-MM-dd format.");
                }
            }
            double ticketPrice = 0.0;
            while (true) {
                System.out.print("Enter the ticket price for the showtime: ");
                String priceStr = scanner.nextLine();
                try {
                    ticketPrice = Double.parseDouble(priceStr);
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Invalid ticket price. Please enter a valid number.");
                }
            }
            Showtime showtime = new Showtime(foundMovie, date, time, ticketPrice);
            theater.addShowtime(showtime);
            System.out.println("Showtime added successfully.");
        } else {
            System.out.println("Movie not found.");
        }
    }

    private static Showtime selectShowtime() {
        System.out.print("Enter the movie title: ");
        String title = scanner.nextLine().trim();
        Movie movie = theater.findMovie(title);
        if (movie == null) {
            System.out.println("Movie not found.");
            return null;
        }
        System.out.print("Enter the showtime (time): ");
        String timeStr = scanner.nextLine().trim();
        LocalTime time = LocalTime.parse(timeStr, DateTimeFormatter.ofPattern("h:mm a"));
        System.out.print("Enter the showtime (date): ");
        String dateStr = scanner.nextLine().trim();
        LocalDate date = LocalDate.parse(dateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        Showtime showtime = theater.findShowtime(movie, date, time);
        return showtime;
    }

    static void removeShowtime() {
        Showtime showtime = selectShowtime();
        if (showtime != null) {
            theater.removeShowtime(showtime);
            System.out.println("Showtime removed successfully.");
        }
    }

    static void viewShowtime() {
        Showtime showtime = selectShowtime();
        if (showtime != null) {
            System.out.println(showtime);
            ArrayList<Seat> tickets = showtime.getTickets();
            System.out.println("Sold seating:");
            for (Seat ticket : tickets) {
                System.out.println(ticket);
            }
        }
    }

    static void viewAllShowtimes() {
        ArrayList<Showtime> showtimes = theater.getShowtimes();
        for (Showtime showtime : showtimes) {
            System.out.println(showtime);
            System.out.println();
        }
    }

    static void buyTicket() {
        Showtime showtime = selectShowtime();
        if (showtime != null) {
            System.out.print("Enter the seat (row and column): ");
            int row = scanner.nextInt();
            int col = scanner.nextInt();
            scanner.nextLine();
            showtime.buyTicket(row, col, theater);
        }
    }

    static void viewSeating() {
        Showtime showtime = selectShowtime();
        if (showtime != null) {
            showtime.printSeating(theater);
        }
    }
}
