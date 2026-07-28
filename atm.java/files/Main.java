import java.util.Scanner;

/**
 * Entry point for the ATM console simulation.
 * Sets up a Bank with some sample accounts, then launches the ATM UI.
 */
public class Main {

    public static void main(String[] args) {
        Bank bank = new Bank();

        // Sample seed accounts (accountId, ownerName, pin, initialBalance)
        bank.addAccount(new Account("1001", "Alice Johnson", "1234", 1500.00));
        bank.addAccount(new Account("1002", "Bob Smith", "5678", 800.00));
        bank.addAccount(new Account("1003", "Carla Diaz", "0000", 250.50));

        System.out.println("Sample accounts for testing:");
        System.out.println("  User ID: 1001  PIN: 1234  (Alice Johnson, Balance: $1500.00)");
        System.out.println("  User ID: 1002  PIN: 5678  (Bob Smith, Balance: $800.00)");
        System.out.println("  User ID: 1003  PIN: 0000  (Carla Diaz, Balance: $250.50)");

        try (Scanner scanner = new Scanner(System.in)) {
            ATM atm = new ATM(bank, scanner);
            atm.start();
        }
    }
}
