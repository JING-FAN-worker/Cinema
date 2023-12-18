import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class Theater extends Venue {
    private ArrayList<Movie> movies;
    private ArrayList<Showtime> showtimes;
    private Seat[][] seats;

    public Theater(String name, String location, int numRows, int numCols) {
        super(name, location);
        this.movies = new ArrayList<>();
        this.showtimes = new ArrayList<>();
        this.seats = new Seat[numRows][numCols];
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                this.seats[i][j] = new Seat(i, j, true);
            }
        }
    }

    int getRows() {
        return this.seats.length;
    }

    int getCols() {
        return this.seats[0].length;
    }

    public ArrayList<Movie> getMovies() {
        return this.movies;
    }

    public ArrayList<Showtime> getShowtimes() {
        return this.showtimes;
    }

    public Seat[][] getSeats() {
        return this.seats;
    }

    public void addMovie(Movie movie) {
        this.movies.add(movie);
    }

    public Movie removeMovie(String title) {
        Movie movieToRemove = null;
        for (Movie movie : this.movies) {
            if (movie.getTitle().equals(title)) {
                movieToRemove = movie;
                break;
            }
        }
        if (movieToRemove != null) {
            this.movies.remove(movieToRemove);
        }
        return movieToRemove;
    }

    public Movie findMovie(String title) {
        for (Movie movie : this.movies) {
            if (movie.getTitle().equals(title)) {
                return movie;
            }
        }
        return null;
    }

    public void addShowtime(Showtime showtime) {
        this.showtimes.add(showtime);
    }

    public void removeShowtime(Showtime showtime) {
        this.showtimes.remove(showtime);
    }

    public Showtime findShowtime(Movie movie, LocalDate date, LocalTime time) {
        for (Showtime showtime : this.showtimes) {
            if (showtime.getMovie().equals(movie) && showtime.getDate().equals(date)
                    && showtime.getTime().equals(time)) {
                return showtime;
            }
        }
        return null;
    }
}