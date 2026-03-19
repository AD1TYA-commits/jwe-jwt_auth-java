import java.security.KeyPair;


import java.security.KeyPairGenerator;





public class KeyManager {





    private static KeyPair keyPair;





    static {


        try {


            KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");


            generator.initialize(2048);


            keyPair = generator.generateKeyPair();


        } catch (Exception e) {


            e.printStackTrace();


        }


    }





    public static KeyPair getKeyPair() {


        return keyPair;


    }


}

