import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.*;

import java.security.interfaces.RSAPublicKey;

public class JweService {

    public static String encryptToken(String jwt) throws Exception {

        JWEObject jwe = new JWEObject(
                new JWEHeader(
                        JWEAlgorithm.RSA_OAEP_256,
                        EncryptionMethod.A256GCM
                ),
                new Payload(jwt)
        );

        RSAEncrypter encrypter = new RSAEncrypter(
                (RSAPublicKey) KeyManager.getKeyPair().getPublic()
        );

        jwe.encrypt(encrypter);

        return jwe.serialize();
    }

}
