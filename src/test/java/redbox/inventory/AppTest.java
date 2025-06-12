package redbox.inventory;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import java.util.ArrayList;
import redbox.inventory.PersistentStorage.*;
import redbox.inventory.Movie.*;
import redbox.inventory.Common.*;
import redbox.inventory.Transactions.*;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }

    @Test
    public void testAddTitle() {
        String testTitle = "Hi";

        MovieInventory movieInventory = new MovieInventory();
        movieInventory.AddTitle(testTitle,4);
    }

    @Test
    public void testRemoveTitle() {
        MovieInventory movieInventory = new MovieInventory();
        // Subtract 2 from available
        movieInventory.RemoveTitle("Wacky Adventures in Wild Land",2);
        // Remove bc quantity is 0
        movieInventory.RemoveTitle("Dear",2);
        // Remove bc quantity is less than amount removed
        movieInventory.RemoveTitle("Trepidation",2);
    }

    @Test
    public void testReturnTitle() {
        // Return a movie
        MovieInventory movieInventory = new MovieInventory();
        String testTitle = "A Movie";
        movieInventory.ReturnTitle(testTitle);

        // Try returning a movie with no rented copies
        String testTitle2 = "Trepidation";
        movieInventory.ReturnTitle(testTitle2);
    }

    @Test
    public void testRentTitleValid() {
        // Rent a movie
        MovieInventory movieInventory = new MovieInventory();
        String testTitle = "Trepidation";

        movieInventory.RentTitle(testTitle);
    }

    @Test
    public void testRentTitleNoneAvailable() {
        // Try returning a movie with no available copies, should leave inventory unchanged
        MovieInventory movieInventory = new MovieInventory();
        String testTitle = "Dear";

        movieInventory.RentTitle(testTitle);
    }

    @Test
    public void testGetMovie() {
        String testTitle = "Dear";

        MovieInventory movieInventory = new MovieInventory();
        Movie testMovie = movieInventory.getMovie(testTitle);

        assertNotNull(testMovie);
        assertEquals("Dear", testMovie.getTitle());
    }

    @Test
    public void testLoadAndSort() {
        InventoryOperations inventoryOperations = new InventoryOperations();
        BinarySearchTree<Movie> loadFile = inventoryOperations.loadInventory(); //Load file into BST
        ArrayList<Movie> fileOrdered = loadFile.inOrderTraversal(); //Sort contents and return into array list

        // Check each movie against next to confirm alphabetical order
        Movie movie = fileOrdered.get(0);
        assertNotNull(movie);

        for (int i = 1; i < fileOrdered.size(); i++) {
            Movie nextMovie = fileOrdered.get(i);
            assertNotNull(movie);
            inventoryOperations.readString(movie.getTitle());
            inventoryOperations.readString(nextMovie.getTitle());
            assertTrue(movie.compareTo(nextMovie) < 0);
            movie = nextMovie;
        }
    }

    @Test
    public void testFormatInventory() {
        InventoryOperations inventoryOperations = new InventoryOperations();
        BinarySearchTree<Movie> loadFile = inventoryOperations.loadInventory(); //Load file into BST
        ArrayList<Movie> fileOrdered = loadFile.inOrderTraversal(); //Sort contents and return into array list

        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%-35s %-15s %-10s\n","Title","Available","Rented"));
        sb.append("-----------------------------------------------------------\n");
        for (int i = 0; i < fileOrdered.size(); i++) {
            sb.append(String.format("%-35s %-15d %-10d\n", fileOrdered.get(i).getTitle(), fileOrdered.get(i).getAvailable(), fileOrdered.get(i).getRented()));
        }
        String expected = sb.toString();

        MovieInventory movieInventory = new MovieInventory();
        String actual = movieInventory.formatInventory();

        assertEquals(expected, actual);
    }

    @Test
    public void testLoadTransactions() {
        TransactionLogOperations transactionLogOperations = new TransactionLogOperations();
        ArrayList<Transaction> transactions = transactionLogOperations.loadTransactions();

        assertNotNull(transactions.get(0).getTitle());
        assertNotNull(transactions.get(1).getTitle());
    }
}
