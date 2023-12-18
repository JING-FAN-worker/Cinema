import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class Showtime {
    private Movie movie;
    private LocalDate date;
    private LocalTime time;
    private double price;
    private ArrayList<Seat> tickets;

    public Showtime(Movie movie, LocalDate date, LocalTime time, double price) {
        this.movie = movie;
        this.date = date;
        this.time = time;
        this.price = price;
        this.tickets = new ArrayList<>();
    }

    public Movie getMovie() {
        return this.movie;
    }

    public LocalDate getDate() {
        return this.date;
    }

    public LocalTime getTime() {
        return this.time;
    }

    public double getPrice() {
        return this.price;
    }

    public ArrayList<Seat> getTickets() {
        return this.tickets;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void addTicket(Seat ticket) {
        this.tickets.add(ticket);
    }

    public void removeTicket(Seat ticket) {
        this.tickets.remove(ticket);
    }

    public void buyTicket(int row, int col, Theater theater) {
        // check for size
        if (row < 0 || row >= theater.getRows() || col < 0 || col >= theater.getCols()) {
            System.out.println("Invalid seat");
            return;
        }
        // check if seat is available
        Seat seat = theater.getSeats()[row][col];
        if (!seat.isAvailable()) {
            System.out.println("Seat not available");
            return;
        }
        // check if seat is already bought
        for (Seat ticket : this.tickets) {
            if (ticket.getRow() == row && ticket.getCol() == col) {
                System.out.println("Seat already bought");
                return;
            }
        }
        seat = new Seat(row, col, false);
        this.tickets.add(seat);
        System.out.println("Ticket bought!");
    }

    public void printSeating(Theater theater) {
        Seat[][] seats = theater.getSeats();
        for (int row = 0; row < theater.getRows(); ++row) {
            for (int col = 0; col < theater.getCols(); ++col) {
                boolean available = true;
                for (Seat ticket : this.tickets) {
                    if (ticket.getRow() == row && ticket.getCol() == col) {
                        available = false;
                        break;
                    }
                }
                if (available) {
                    System.out.print("[O]");
                } else {
                    System.out.print("[X]");
                }
                if (col < theater.getCols() - 1) {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
    }

    @Override
    public String toString() {
        return "Movie: " + this.movie.getTitle() + "\n" +
                "Time: " + this.time + "\n" +
                "Date: " + this.date + "\n" +
                "Price: " + this.price;
    }
}