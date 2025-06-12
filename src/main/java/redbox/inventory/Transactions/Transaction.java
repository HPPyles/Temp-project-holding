package redbox.inventory.Transactions;

/*
Transaction Object
Hosanna Pyles
hpp220001
 */
public class Transaction {
    private String command;
    private String title;
    private int quantity;

    public Transaction(String command,String title, int quantity) {
        this.command = command;
        this.title = title;
        this.quantity = quantity;
    }

    public Transaction(String command, String title) {
        this.command = command;
        this.title = title;
        quantity = 0;
    }

    public String getCommand() {
        return command;
    }

    public String getTitle() {
        return title;
    }

    public int getQuantity() {
        return quantity;
    }
}
