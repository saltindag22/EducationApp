package com.example.demo2.services;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class SecretKeyGenerator {


    public SecretKey generateAPISecretKey(){
         try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacSHA256");
            keyGenerator.init(256, new SecureRandom());
            return keyGenerator.generateKey();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error generating secret key", e);
        }
    }
    
}
