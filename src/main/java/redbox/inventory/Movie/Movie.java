package redbox.inventory.Movie;

/*
Movie object
Hosanna Pyles
hpp220001
 */

public class Movie implements Comparable<Movie> {
    
    private String title;
    private int available;
    private int rented;
    
    public Movie(String title, int available, int rented) {
        this.title = title;
        this.available = available;
        this.rented = rented;
    }

    public String getTitle() {
        return title;
    }
    public int getAvailable() {
        return available;
    }
    public int getRented() {
        return rented;
    }

    // Compares titles, returns 1 if this movie's title is greater than checked against, 0 if equal, -1 if less
    @Override
    public int compareTo(Movie compareMovie) {
        return title.compareToIgnoreCase(compareMovie.title);
    }
}
