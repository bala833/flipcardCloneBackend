package com.bala.FlipkartClone.utils.secretkeyGenerator;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.Base64;


public class secretKeyGenerator {

        public static void main(String[] args) {
            SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
            String base64Key = Encoders.BASE64.encode(key.getEncoded());
            System.out.println(base64Key);
        }
}

