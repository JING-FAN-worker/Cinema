import java.lang.reflect.Array;
import java.util.ArrayList;

public class Movie {
    private String title;
    private String director;
    private String releaseYear;
    private String review;
    private ArrayList<String> genres;
    private int duration;
    private double rating;
    private int numberOfRatings;

    public Movie(String title, String director, String releaseYear, String review, ArrayList<String> genres,
            int duration, double rating, int numberOfRatings) {
        this.title = title;
        this.director = director;
        this.releaseYear = releaseYear;
        this.review = review;
        this.genres = genres;
        this.duration = duration;
        this.rating = rating;
        this.numberOfRatings = numberOfRatings;
    }

    String getTitle() {
        return this.title;
    }

    String getDirector() {
        return this.director;
    }

    String getReleaseYear() {
        return this.releaseYear;
    }

    String getReview() {
        return this.review;
    }

    ArrayList<String> getGenres() {
        return this.genres;
    }

    int getDuration() {
        return this.duration;
    }

    double getRating() {
        return this.rating;
    }

    int getNumberOfRatings() {
        return this.numberOfRatings;
    }

    void setTitle(String title) {
        this.title = title;
    }

    void setDirector(String director) {
        this.director = director;
    }

    void setReleaseYear(String releaseYear) {
        this.releaseYear = releaseYear;
    }

    void setReview(String review) {
        this.review = review;
    }

    void setGenres(ArrayList<String> genres) {
        this.genres = genres;
    }

    void setDuration(int duration) {
        this.duration = duration;
    }

    void setRating(double rating) {
        this.rating = rating;
    }

    void setNumberOfRatings(int numberOfRatings) {
        this.numberOfRatings = numberOfRatings;
    }

    private String genreString() {
        return "[" + String.join(", ", this.genres) + "]";
    }

    @Override
    public String toString() {
        return "Title: " + this.title + "\nDirector: " + this.director + "\nRelease Year: " + this.releaseYear
                + "\nReview: " + this.review + "\nGenre: " + genreString() + "\nDuration: " + this.duration
                + "\nRating: " + this.rating + "\nNumber of Ratings: " + this.numberOfRatings;
    }
}
