import java.sql.*;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter username: ");
        String u = sc.next();
        System.out.println("Enter password: ");
        String p = sc.next();

        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            System.out.println("Connected to the database!");
        } catch (Exception e) {
            e.printStackTrace();
            sc.close();
            return; // stop program if DB fails
        }

        int balance = 0;
        int choice, withdraw, deposit;
        boolean isRunning = true;

        // Login check
        try {
            String sql = "SELECT balance FROM users WHERE username=? AND password=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, u);
            stmt.setString(2, p);
            ResultSet rs = stmt.executeQuery();

            if (!rs.next()) {
                System.out.println("Invalid login");
                sc.close();
                return;
            }
            balance = rs.getInt("balance");
        } catch (Exception e) {
            e.printStackTrace();
            sc.close();
            return;
        }

        System.out.println("Welcome to the banking program");

        // Main menu
        while(isRunning) {
            System.out.println("******************************");
            System.out.println("Make a choice:");
            System.out.println("1 for balance");
            System.out.println("2 for withdraw");
            System.out.println("3 for deposit");
            System.out.println("4 for exit");
            System.out.println("******************************");
            choice = sc.nextInt();

            switch(choice) {
                case 1 -> BankingOperations.showBalance(balance);
                case 2 -> {
                    System.out.println("Enter amount to withdraw: ");
                    withdraw = sc.nextInt();
                    if (withdraw <= 0) System.out.println("Withdrawal must be positive");
                    else if (withdraw > balance) System.out.println("Not enough balance");
                    else {
                        balance -= withdraw;
                        BankingOperations.doWithdraw(withdraw);
                        BankingOperations.saveBalance(conn, u, balance);
                    }
                }
                case 3 -> {
                    System.out.println("Enter amount to deposit: ");
                    deposit = sc.nextInt();
                    if (deposit < 0) System.out.println("Can't be negative");
                    else {
                        balance += deposit;
                        BankingOperations.putDeposit(deposit);
                        BankingOperations.saveBalance(conn, u, balance);
                    }
                }
                case 4 -> isRunning = false;
                default -> System.out.println("Invalid choice");
            }
        }
        sc.close();
    }
}