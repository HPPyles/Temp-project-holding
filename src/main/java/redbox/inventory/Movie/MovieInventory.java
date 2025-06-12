package redbox.inventory.Movie;

import java.util.ArrayList;

import redbox.inventory.Common.BinarySearchTree;
import redbox.inventory.PersistentStorage.InventoryOperations;

/*
Inventory Changes
Hosanna Pyles
hpp220001
 */

public class MovieInventory {
    
    BinarySearchTree<Movie> redboxMovies;

    public MovieInventory() {
        InventoryOperations inventoryOperations = new InventoryOperations();
        redboxMovies = inventoryOperations.loadInventory();
    } 

    // To get movie object from String input (title): Run in order traversal to get array list of ordered movies,
    // check title element of each against title string from transaction line
    public Movie getMovie(String title) {
        ArrayList<Movie> orderedMovies = redboxMovies.inOrderTraversal();
        for (int i = 0; i < redboxMovies.getLength(); i++) {
            if (orderedMovies.get(i).getTitle().equals(title)) {
                return orderedMovies.get(i);
            }
        }

        // If movie is not in inventory
        return null;
    }

    public void AddTitle(String title, int quantity) {
        Movie movie = getMovie(title);

        // If the title wasn't found, add new movie
        if (movie == null) {
            // New movie, we have title and number available, none can have been rented yet
            Movie newMovie = new Movie(title, quantity, 0);
            // Add to tree
            redboxMovies.insert(newMovie);
        }
        else {
            // Create new movie object with available adjusted by amount added
            Movie updated = new Movie(movie.getTitle(), (movie.getAvailable() + quantity), movie.getRented());
            // Update tree
            redboxMovies.update(movie, updated);
        }
    }

    public void RemoveTitle(String title, int quantity) {
        Movie movie = getMovie(title);

        // If the title wasn't found, don't execute
        if (movie == null) {
            return;
        }

        // If there are none of that title available or the transaction removes more than there are available, delete the node
        if (movie.getAvailable() == 0 || (movie.getAvailable() - quantity) < 0 ) {
            redboxMovies.delete(movie);
        } else {
            // Create new movie object with available adjusted by amount removed
            Movie updated = new Movie(movie.getTitle(), (movie.getAvailable() - quantity), movie.getRented());
            // Update tree
            redboxMovies.update(movie, updated);
        }
    }

    // Get movie object using title, use components and quantity to created updated movie object, overwrite original movie object
    public void RentTitle(String title) {
        Movie ogMovie = getMovie(title);

        if (ogMovie == null) {
            return;
        }

        if (ogMovie.getAvailable() != 0) {
            // Number available goes down, number rented goes up
            Movie updatedMovie = new Movie(ogMovie.getTitle(), (ogMovie.getAvailable() - 1), (ogMovie.getRented() + 1));
            
            // Replace original movie object w new
            redboxMovies.update(ogMovie, updatedMovie);
        }
        else if (ogMovie.getAvailable() == 0) {
            //Error log
        }
    }

    // Get movie object using title, use components and quantity to created updated movie object, overwrite original movie object
    public void ReturnTitle(String title) {
        Movie ogMovie = getMovie(title);

        if (ogMovie == null) {
            return;
        }
        
        if (ogMovie.getRented() != 0) {
            // Number available increases, number rented decreases
            Movie updatedMovie = new Movie(ogMovie.getTitle(), (ogMovie.getAvailable() + 1), (ogMovie.getRented() - 1));
            
            // Replace original movie object w new
            redboxMovies.update(ogMovie, updatedMovie);
        }
        else if (ogMovie.getRented() == 0) {
            System.out.println("Return failed, no copies rented out.");
        }
    }

    public void sendUpdate() {
        // Update inventory
        InventoryOperations inventoryOperations = new InventoryOperations();
        inventoryOperations.updateInventory(redboxMovies);
    }

    public String formatInventory() {
        // Get currently held inventory info
        ArrayList<Movie> inventory = redboxMovies.inOrderTraversal();
        
        StringBuilder inventoryInfo = new StringBuilder();
        
        // Format title information
        inventoryInfo.append(String.format("%-35s %-15s %-10s\n","Title","Available","Rented"));
        inventoryInfo.append("-----------------------------------------------------------\n");
        for (int i = 0; i < inventory.size(); i++) { // Format info for each movie
            inventoryInfo.append(String.format("%-35s %-15d %-10d\n", inventory.get(i).getTitle(), inventory.get(i).getAvailable(), inventory.get(i).getRented()));
        }

        // Return as string so it can be written to file
        return inventoryInfo.toString();
    }
}
