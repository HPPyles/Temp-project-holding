package redbox.inventory;

import redbox.inventory.Movie.*;
import redbox.inventory.PersistentStorage.*;
import redbox.inventory.Transactions.*;

/*
Main
Hosanna Pyles
hpp220001
*/
public class Main 
{
    public static void main( String[] args )
    {
        //TransactionLogOperations transactionLogOperations = new TransactionLogOperations();
        // Run updates to inventory using transaction processing
        TransProcessing transProcessing = new TransProcessing();
        transProcessing.transUpdates();

        // Get formatted string of updated inventory contents
        MovieInventory movieInventory = new MovieInventory();
        String formatted = movieInventory.formatInventory();

        // Write formatted string to redbox_kiosk
        InventoryOperations inventoryOperations = new InventoryOperations();
        inventoryOperations.finalReport(formatted);
    }
}
