import java.util.ArrayList;
import java.util.List;

/**
 * Represents a bank account belonging to a single user.
 * Uses encapsulation: fields are private, accessed only via getters/setters.
 */
public class Account {

    private final String accountId;
    private final String ownerName;
    private String pin;
    private double balance;
    private final List<Transaction> transactionHistory;

    public Account(String accountId, String ownerName, String pin, double initialBalance) {
        this.accountId = accountId;
        this.ownerName = ownerName;
        this.pin = pin;
        this.balance = initialBalance;
        this.transactionHistory = new ArrayList<>();
    }

    public String getAccountId() {
        return accountId;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public boolean checkPin(String enteredPin) {
        return this.pin.equals(enteredPin);
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public double getBalance() {
        return balance;
    }

    /**
     * Deducts an amount from the balance. Caller must have already validated funds.
     */
    public void debit(double amount) {
        this.balance -= amount;
    }

    /**
     * Adds an amount to the balance.
     */
    public void credit(double amount) {
        this.balance += amount;
    }

    public boolean hasSufficientFunds(double amount) {
        return this.balance >= amount;
    }

    public void addTransaction(Transaction transaction) {
        this.transactionHistory.add(transaction);
    }

    public List<Transaction> getTransactionHistory() {
        return transactionHistory;
    }
}