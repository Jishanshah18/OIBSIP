import java.util.Scanner;

/**
 * Handles the console user interface: login flow and main menu.
 * Delegates all actual account/money logic to Bank so this class
 * stays focused on presentation and input handling.
 */
public class ATM {

    private static final int MAX_PIN_ATTEMPTS = 3;

    private final Bank bank;
    private final Scanner scanner;

    public ATM(Bank bank, Scanner scanner) {
        this.bank = bank;
        this.scanner = scanner;
    }

    /**
     * Starts the ATM session: login, then main menu loop.
     */
    public void start() {
        System.out.println("=================================");
        System.out.println("   WELCOME TO JAVA CONSOLE ATM");
        System.out.println("=================================");

        Account currentAccount = login();

        if (currentAccount == null) {
            System.out.println("\nToo many incorrect attempts. Access denied.");
            System.out.println("Please contact your bank branch for assistance.");
            return;
        }

        System.out.println("\nLogin successful. Welcome, " + currentAccount.getOwnerName() + "!");
        runMainMenu(currentAccount);
    }

    /**
     * Prompts for User ID and PIN, allowing up to MAX_PIN_ATTEMPTS attempts.
     * Returns the authenticated Account, or null if attempts are exhausted.
     */
    private Account login() {
        int attempts = 0;
        while (attempts < MAX_PIN_ATTEMPTS) {
            System.out.print("\nEnter User ID: ");
            String accountId = scanner.nextLine().trim();
            System.out.print("Enter PIN: ");
            String pin = scanner.nextLine().trim();

            Account account = bank.authenticate(accountId, pin);
            if (account != null) {
                return account;
            }

            attempts++;
            int remaining = MAX_PIN_ATTEMPTS - attempts;
            if (remaining > 0) {
                System.out.println("Incorrect User ID or PIN. Attempts remaining: " + remaining);
            }
        }
        return null;
    }

    /**
     * Displays the main menu in a loop until the user chooses to quit.
     */
    private void runMainMenu(Account account) {
        boolean running = true;

        while (running) {
            printMenu(account);
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    showTransactionHistory(account);
                    break;
                case "2":
                    handleWithdraw(account);
                    break;
                case "3":
                    handleDeposit(account);
                    break;
                case "4":
                    handleTransfer(account);
                    break;
                case "5":
                    System.out.println("\nThank you for using Java Console ATM. Goodbye!");
                    running = false;
                    break;
                default:
                    System.out.println("\nInvalid option. Please choose 1-5.");
            }
        }
    }

    private void printMenu(Account account) {
        System.out.println("\n--------- MAIN MENU ---------");
        System.out.printf("Current Balance: $%.2f%n", account.getBalance());
        System.out.println("1. Transaction History");
        System.out.println("2. Withdraw");
        System.out.println("3. Deposit");
        System.out.println("4. Transfer");
        System.out.println("5. Quit");
        System.out.print("Choose an option: ");
    }

    private void showTransactionHistory(Account account) {
        System.out.println("\n----- TRANSACTION HISTORY -----");
        if (account.getTransactionHistory().isEmpty()) {
            System.out.println("No transactions yet.");
            return;
        }
        int i = 1;
        for (Transaction t : account.getTransactionHistory()) {
            System.out.println(i + ". " + t);
            i++;
        }
    }

    private void handleWithdraw(Account account) {
        System.out.print("\nEnter amount to withdraw: $");
        Double amount = readPositiveAmount();
        if (amount == null)
            return;

        boolean success = bank.withdraw(account, amount);
        if (!success) {
            System.out.println("Insufficient Funds");
        } else {
            System.out.printf("Withdrawal successful. New balance: $%.2f%n", account.getBalance());
        }
    }

    private void handleDeposit(Account account) {
        System.out.print("\nEnter amount to deposit: $");
        Double amount = readPositiveAmount();
        if (amount == null)
            return;

        bank.deposit(account, amount);
        System.out.printf("Deposit successful. New balance: $%.2f%n", account.getBalance());
    }

    private void handleTransfer(Account account) {
        System.out.print("\nEnter recipient account ID: ");
        String recipientId = scanner.nextLine().trim();

        if (!bank.accountExists(recipientId)) {
            System.out.println("Recipient account does not exist.");
            return;
        }

        if (recipientId.equals(account.getAccountId())) {
            System.out.println("Cannot transfer to the same account.");
            return;
        }

        System.out.print("Enter amount to transfer: $");
        Double amount = readPositiveAmount();
        if (amount == null)
            return;

        if (!account.hasSufficientFunds(amount)) {
            System.out.println("Insufficient Funds");
            return;
        }

        boolean success = bank.transfer(account, recipientId, amount);
        if (success) {
            System.out.printf("Transfer successful. New balance: $%.2f%n", account.getBalance());
        } else {
            System.out.println("Transfer failed. Please check the recipient account ID and try again.");
        }
    }

    /**
     * Reads and validates a positive numeric amount from input.
     * Returns null (and prints an error) if the input is invalid.
     */
    private Double readPositiveAmount() {
        String input = scanner.nextLine().trim();
        try {
            double amount = Double.parseDouble(input);
            if (amount <= 0) {
                System.out.println("Amount must be greater than zero.");
                return null;
            }
            return amount;
        } catch (NumberFormatException e) {
            System.out.println("Invalid amount entered.");
            return null;
        }
    }
}
