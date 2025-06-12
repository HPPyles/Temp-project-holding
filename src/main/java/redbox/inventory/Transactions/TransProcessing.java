package redbox.inventory.Transactions;

import java.util.ArrayList;

import redbox.inventory.PersistentStorage.TransactionLogOperations;
import redbox.inventory.Movie.MovieInventory;

/*
Transaction Processing
Hosanna Pyles
hpp220001
Have arraylist that can vary based on file contents, validate file format,
get command as string from previously made variable lenght arraylist, execute command somehow
*/

public class TransProcessing {
    ArrayList<Transaction> transactions;
    MovieInventory movieInventory = new MovieInventory();
    TransactionLogOperations transactionLogOperations = new TransactionLogOperations();

    public TransProcessing() {
        transactions = transactionLogOperations.loadTransactions();
    }

    // Update the inventory using the transaction logs
    public void transUpdates() {
        for (int i = 0; i < transactions.size(); i++) {
            // Command runCommand = transactions.get(i).getCommand();
            // // Don't need input validation here since that's supposed to be when loading file
            // runCommand.Execute();
            Transaction transaction = transactions.get(i);
            
            // Run updates to inventory based on command name
            switch (transaction.getCommand()) {
                case "add":
                    movieInventory.AddTitle(transaction.getTitle(), transaction.getQuantity());
                    break;
                case "remove":
                    movieInventory.RemoveTitle(transaction.getTitle(), transaction.getQuantity());
                    break;
                case "rent":
                    movieInventory.RentTitle(transaction.getTitle());
                    break;
                case "return":
                    movieInventory.ReturnTitle(transaction.getTitle());
                    break;
                default:
                    System.err.println("Error: Tried to process invalid transaction");
                    break;
            }
        }

        // Write error log
        transactionLogOperations.writeErrorLog();
        // Update inventory file using changes to BST
        movieInventory.sendUpdate();
    }
}
