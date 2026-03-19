import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AuthService {

    public static String login(String username, String password) throws Exception {

        Connection conn = DBConnection.getConnection();

        String sql = "SELECT * FROM users WHERE username=? AND password=?";
        PreparedStatement stmt = conn.prepareStatement(sql);

        stmt.setString(1, username);
        stmt.setString(2, password);

        ResultSet rs = stmt.executeQuery();

        if (!rs.next()) {
            return null; // ❌ invalid login
        }

        // ✅ valid → generate token
        String jwt = JwtService.generateToken(username);
        String jwe = JweService.encryptToken(jwt);

        return jwe;
    }
}