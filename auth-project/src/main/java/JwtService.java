import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.*;
import com.nimbusds.jwt.*;

import java.security.interfaces.RSAPrivateKey;
import java.util.Date;

public class JwtService {

    public static String generateToken(String username) throws Exception {

        JWSSigner signer = new RSASSASigner(
                (RSAPrivateKey) KeyManager.getKeyPair().getPrivate()
        );

        JWTClaimsSet claims = new JWTClaimsSet.Builder()
                .subject(username)
                .issuer("auth-project")
                .expirationTime(new Date(new Date().getTime() + 60000))
                .build();

        SignedJWT signedJWT = new SignedJWT(
                new JWSHeader(JWSAlgorithm.RS256),
                claims
        );

        signedJWT.sign(signer);

        return signedJWT.serialize();
    }

}
