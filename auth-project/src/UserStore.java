import java.util.HashMap;


import java.util.Map;





public class UserStore {





    private static Map<String, String> users = new HashMap<>();





    static {


        users.put("admin", "password123");


        users.put("user", "1234");


    }





    public static boolean validateUser(String username, String password) {


        return users.containsKey(username) && users.get(username).equals(password);


    }





}
