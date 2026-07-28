# Java Console ATM Simulation

A console-based ATM simulation built with Java using OOP principles
(encapsulation, separation of concerns across multiple classes).

## Classes

| Class         | Responsibility                                                        |
|---------------|------------------------------------------------------------------------|
| `Main`        | Entry point; seeds sample accounts and starts the ATM                 |
| `Bank`        | Manages all accounts; performs authentication, withdraw, deposit, transfer |
| `Account`     | Represents a single account (id, owner, PIN, balance, history) — private fields with getters/setters |
| `Transaction` | Immutable record of a single transaction (type, amount, timestamp, balance after) |
| `ATM`         | Console UI: login flow, main menu, input validation                   |

## How to Build & Run

Requires a JDK (Java 8+).

```bash
cd src
javac *.java
java Main
```

## Sample Accounts (for testing)

| User ID | PIN  | Owner        | Starting Balance |
|---------|------|--------------|-------------------|
| 1001    | 1234 | Alice Johnson| $1500.00          |
| 1002    | 5678 | Bob Smith    | $800.00           |
| 1003    | 0000 | Carla Diaz   | $250.50           |

## Features

- Login requires User ID + PIN; access is denied after **3 incorrect attempts**.
- Main Menu:
  1. **Transaction History** — lists every transaction made this session for the logged-in account.
  2. **Withdraw** — validates sufficient balance before deducting; shows `Insufficient Funds` otherwise.
  3. **Deposit** — adds funds and updates balance.
  4. **Transfer** — moves funds to another account by ID; validates balance and recipient existence; updates both accounts and logs a transaction on each side.
  5. **Quit** — prints a goodbye message and exits.
- All transactions are stored in an `ArrayList<Transaction>` per account and displayed in a readable, timestamped format.

## Example Session

```
Enter User ID: 1001
Enter PIN: 1234

Login successful. Welcome, Alice Johnson!

--------- MAIN MENU ---------
Current Balance: $1500.00
1. Transaction History
2. Withdraw
3. Deposit
4. Transfer
5. Quit
Choose an option: 2

Enter amount to withdraw: $200
Withdrawal successful. New balance: $1300.00
```

## Extending the Project

- Swap `Bank`'s in-memory `HashMap` for a database or file-backed store to persist
  accounts/transactions across program runs.
- Add an `Admin` class for creating new accounts or resetting PINs.
- Add unit tests (JUnit) for `Bank`'s withdraw/deposit/transfer logic.
