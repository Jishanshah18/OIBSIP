import java.util.HashMap;
import java.util.Map;

/**
 * Represents the bank: holds all accounts and performs
 * the core operations (authentication, withdraw, deposit, transfer).
 * Keeping this logic out of ATM keeps concerns separated (OOP best practice).
 */
public class Bank {

    private final Map<String, Account> accounts;

    public Bank() {
        this.accounts = new HashMap<>();
    }

    public void addAccount(Account account) {
        accounts.put(account.getAccountId(), account);
    }

    public Account getAccount(String accountId) {
        return accounts.get(accountId);
    }

    public boolean accountExists(String accountId) {
        return accounts.containsKey(accountId);
    }

    /**
     * Authenticates a user by account id and PIN.
     * Returns the Account if credentials match, otherwise null.
     */
    public Account authenticate(String accountId, String pin) {
        Account account = accounts.get(accountId);
        if (account != null && account.checkPin(pin)) {
            return account;
        }
        return null;
    }

    /**
     * Withdraws money from an account, logging the transaction.
     * Returns true if successful, false if insufficient funds.
     */
    public boolean withdraw(Account account, double amount) {
        if (!account.hasSufficientFunds(amount)) {
            return false;
        }
        account.debit(amount);
        account.addTransaction(new Transaction("WITHDRAW", amount, "-", account.getBalance()));
        return true;
    }

    /**
     * Deposits money into an account, logging the transaction.
     */
    public void deposit(Account account, double amount) {
        account.credit(amount);
        account.addTransaction(new Transaction("DEPOSIT", amount, "-", account.getBalance()));
    }

    /**
     * Transfers money from one account to another, logging the transaction
     * on both sides. Returns true if successful, false if insufficient funds
     * or the recipient account does not exist.
     */
    public boolean transfer(Account sender, String recipientId, double amount) {
        Account recipient = accounts.get(recipientId);
        if (recipient == null) {
            return false;
        }
        if (recipientId.equals(sender.getAccountId())) {
            return false;
        }
        if (!sender.hasSufficientFunds(amount)) {
            return false;
        }

        sender.debit(amount);
        recipient.credit(amount);

        sender.addTransaction(new Transaction("TRANSFER-OUT", amount,
                "To: " + recipient.getAccountId(), sender.getBalance()));
        recipient.addTransaction(new Transaction("TRANSFER-IN", amount,
                "From: " + sender.getAccountId(), recipient.getBalance()));

        return true;
    }
}
