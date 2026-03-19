import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws Exception {

        Scanner sc = new Scanner(System.in);

        // 🔐 LOGIN
        System.out.println("Username:");
        String username = sc.nextLine();

        System.out.println("Password:");
        String password = sc.nextLine();

        String token = AuthService.login(username, password);

        if (token == null) {
            System.out.println("Invalid login");
            return;
        }

        System.out.println("Token:");
        System.out.println(token);

        // 🔌 DB CONNECTION
        Connection conn = DBConnection.getConnection();

        // 💰 FETCH BALANCE
        int balance = 0;

        PreparedStatement stmt = conn.prepareStatement(
                "SELECT balance FROM users WHERE username=?"
        );
        stmt.setString(1, username);

        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            balance = rs.getInt("balance");
        } else {
            System.out.println("User not found in DB");
            return;
        }

        System.out.println("Welcome to the banking program");

        boolean isRunning = true;

        // 🏦 MAIN LOOP
        while (isRunning) {

            System.out.println("******************************");
            System.out.println("1 for balance");
            System.out.println("2 for withdraw");
            System.out.println("3 for deposit");
            System.out.println("4 for exit");
            System.out.println("******************************");

            int choice = sc.nextInt();

            switch (choice) {

                case 1:
                    BankingOperations.showBalance(balance);
                    break;

                case 2:
                    System.out.println("Enter amount to withdraw: ");
                    int withdraw = sc.nextInt();

                    if (withdraw <= 0) {
                        System.out.println("Withdrawal must be positive");
                    } else if (withdraw > balance) {
                        System.out.println("Not enough balance");
                    } else {
                        balance -= withdraw;

                        BankingOperations.doWithdraw(withdraw);

                        // ✅ SAVE TO DB
                        PreparedStatement update1 = conn.prepareStatement(
                                "UPDATE users SET balance=? WHERE username=?"
                        );
                        update1.setInt(1, balance);
                        update1.setString(2, username);
                        update1.executeUpdate();
                    }
                    break;

                case 3:
                    System.out.println("Enter amount to deposit: ");
                    int deposit = sc.nextInt();

                    if (deposit < 0) {
                        System.out.println("Can't be negative");
                    } else {
                        balance += deposit;

                        BankingOperations.putDeposit(deposit);

                        // ✅ SAVE TO DB
                        PreparedStatement update2 = conn.prepareStatement(
                                "UPDATE users SET balance=? WHERE username=?"
                        );
                        update2.setInt(1, balance);
                        update2.setString(2, username);
                        update2.executeUpdate();
                    }
                    break;

                case 4:
                    isRunning = false;
                    System.out.println("Thank you for using the banking system");
                    break;

                default:
                    System.out.println("Invalid choice");
            }
        }

        sc.close();
        conn.close();
    }
}