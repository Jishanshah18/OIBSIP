import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a single banking transaction.
 * Immutable record of what happened, when, and the resulting balance.
 */
public class Transaction {

    private final String type;          // WITHDRAW, DEPOSIT, TRANSFER-OUT, TRANSFER-IN
    private final double amount;
    private final String details;        // extra info, e.g. recipient account id
    private final double balanceAfter;
    private final LocalDateTime timestamp;

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public Transaction(String type, double amount, String details, double balanceAfter) {
        this.type = type;
        this.amount = amount;
        this.details = details;
        this.balanceAfter = balanceAfter;
        this.timestamp = LocalDateTime.now();
    }

    public String getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }

    public String getDetails() {
        return details;
    }

    public double getBalanceAfter() {
        return balanceAfter;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return String.format("[%s] %-13s $%-10.2f %-25s Balance after: $%.2f",
                timestamp.format(FORMATTER), type, amount, details, balanceAfter);
    }
}
