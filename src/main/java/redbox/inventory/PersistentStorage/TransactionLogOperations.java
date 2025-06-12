package redbox.inventory.PersistentStorage;

import redbox.inventory.Transactions.Transaction;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
Transaction Log Operations
Hosanna Pyles
hpp220001
*/

public class TransactionLogOperations {

    private static final Pattern TX_PATTERN = Pattern.compile("^(add|remove|rent|return)\\s*\"([^\"]+)\"(?:\\s*,\\s*(\\d+))?$",Pattern.CASE_INSENSITIVE);
    private final ArrayList<String> errorLines = new ArrayList<>();

    public ArrayList<Transaction> loadTransactions() {
        String fileName = "transaction.log";
        Path filePath = Paths.get(fileName);
        ArrayList<Transaction> transactions = new ArrayList<>();
        
        try {
            if (!Files.exists(filePath)) {
                System.out.println("Error, transactions log not found.");
                return transactions;
            }

            List<String> lines = Files.readAllLines(filePath);
            for (String line : lines) {
                // Check format is valid
                Matcher m = TX_PATTERN.matcher(line.trim());
                if (!m.matches()) {
                    errorLines.add(line);
                    System.out.println(line);
                    continue;
                }

                // Break apart at comma
                String parts[] = line.split(",");

                if (parts.length == 1) {
                    // Break apart again at quotes
                    String split[] = parts[0].split("\"");

                    String command = split[0].toLowerCase().trim(); // Case insensitive
                    String title = split[1].trim();

                    // if there are no commas in an add or remove command line (improper format)
                    if (command.equals("add") || command.equals("remove")) {
                        errorLines.add(line);
                        System.out.println(line);
                        continue;
                    }

                    Transaction transaction = new Transaction(command, title);

                    transactions.add(transaction);
                }
                else if (parts.length == 2) {
                    // Break apart again at quotes
                    String split[] = parts[0].split("\"");

                    String command = split[0].toLowerCase().trim(); // Case insensitive
                    String title = split[1].trim();
                    String qty = parts[1].trim();

                    int quantity = 0;
                    if (qty != null) {
                        try {
                            quantity = Integer.parseInt(qty); // Try converting to int
                        } catch (NumberFormatException e) { // If not valid int, invalid command
                            errorLines.add(line);
                            System.out.println(line);
                            continue;
                        }
                    }
                    // Save information in transaction object
                    Transaction transaction = new Transaction(command, title, quantity);
                    // Add to arraylist
                    transactions.add(transaction);
                }
                else {
                    errorLines.add(line);
                }
            }
        } catch(IOException e) {
            System.out.println("Error loading transactions from file: ");
            e.printStackTrace();
        }
        return transactions;
    }

    public void writeErrorLog() {
        Path filePath = Paths.get("error.log");
        try {
            if (!Files.exists(filePath))
            {
                Files.createFile(filePath);
            }
            // Write compiled arraylist of invalid commands to error log
            Files.write(filePath, errorLines, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            System.err.println("Error writing error.log: " + e.getMessage());
        }
    }
}
