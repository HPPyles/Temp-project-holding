package redbox.inventory.PersistentStorage;

import java.nio.file.Paths;
import java.nio.file.Files;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import redbox.inventory.Common.*;
import redbox.inventory.Movie.Movie;

/*
Inventory Operations
Hosanna Pyles
hpp220001
Load inventory: Get inventory file contents, convert to movie objects, load into BST
Update inventory: Using current information in BST, use StringBuilder to overwrite the inventory's original contents
with the new contents
 */

public class InventoryOperations {
    String fileName = "inventory.dat";

    // DEBUG FUNCTION
    public void readString(String string) {
        System.out.println(string);
    }
    
    // Load inventory
    public BinarySearchTree<Movie> loadInventory() {
        BinarySearchTree<Movie> tree = new BinarySearchTree<>(); //Create tree instance

        // Get inventory contents
        File file = new File(fileName);
        ScannerFactory.GetScannerInstance(file);

        while (ScannerFactory.GetScannerInstance(file).hasNextLine()) { // For each line in the inventory
            // Get parts of line by breaking at commas
            String parts[] = ScannerFactory.GetScannerInstance(file).nextLine().split(",");
            
            // Convert line to movie object
            String title = parts[0].trim().replace("\"", "");
            int available = Integer.parseInt(parts[1].trim());
            int rented = Integer.parseInt(parts[2].trim());

            Movie movie = new Movie(title, available, rented);

            // Insert movie object into BST
            tree.insert(movie);
        }

        ScannerFactory.CloseScannerInstance();
        return tree; // Return compiled tree
    }

    public void updateInventory(BinarySearchTree<Movie> updatedTree) {
        StringBuilder sb = new StringBuilder();

        // Get current version of BST sorted in order into an arraylist
        ArrayList<Movie> newInventory = updatedTree.inOrderTraversal();

        for (int i = 0; i < newInventory.size(); i++) { // For each movie object
            // Format w string builder
            sb.append("\"").append(newInventory.get(i).getTitle()).append("\",")
            .append(newInventory.get(i).getAvailable()).append(",")
            .append(newInventory.get(i).getRented()).append(System.lineSeparator());

            try {
                Files.write(Paths.get(fileName), sb.toString().getBytes()); // Write formatted string to file
            } catch(IOException e) {
                System.out.println("Error updating inventory: ");
                e.printStackTrace();
            }
        }
    }

    // Info comes pre-formatted, write to file
    public void finalReport(String info) {
        String report = "redbox_kiosk.txt";
        
        try {
            Files.write(Paths.get(report), info.getBytes());
        } catch(IOException e) {
            System.out.println("Error updating inventory: ");
            e.printStackTrace();
        }
    }
}
