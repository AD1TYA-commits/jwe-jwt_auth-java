public class AuthService {

    public static String login(String username, String password) throws Exception {

        if (!UserStore.validateUser(username, password)) {
            return "Invalid login";
        }

        String jwt = JwtService.generateToken(username);

        String jwe = JweService.encryptToken(jwt);

        return jwe;
    }

}
