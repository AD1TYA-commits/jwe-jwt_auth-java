import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BankingOperations {

    public static void showBalance(int balance) {
        System.out.printf("Balance is %d\n", balance);
    }

    public static void doWithdraw(int withdraw) {
        System.out.printf("%d withdrew successfully\n", withdraw);
    }

    public static void putDeposit(int deposit) {
        System.out.printf("%d deposited into the bank\n", deposit);
    }

    public static void saveBalance(Connection conn, String username, int balance) {
        try {
            String update = "UPDATE users SET balance=? WHERE username=?";
            PreparedStatement stmt = conn.prepareStatement(update);
            stmt.setInt(1, balance);
            stmt.setString(2, username);
            stmt.executeUpdate();
            System.out.println("Balance saved to the database!");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error occurred while saving balance.");
        }
    }
}