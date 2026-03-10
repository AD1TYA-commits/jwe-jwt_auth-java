import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws Exception {

        Scanner sc = new Scanner(System.in);

        System.out.println("Username:");
        String username = sc.nextLine();

        System.out.println("Password:");
        String password = sc.nextLine();

        String token = AuthService.login(username, password);

        System.out.println("Token:");
        System.out.println(token);
    }

}
