package com.lkwy.common.security.util;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PasswordGeneratorUtil {
    
    static final Logger LOGGER = LoggerFactory.getLogger(PasswordGeneratorUtil.class);
    
    private static String ALGORITHM = "PBKDF2WithHmacSHA1";
    private static int ITERATION_COUNT = 65536;
    private static int KEY_LENGTH = 128;
    private static int SALT_LENGTH = 16;
    
    public static HashSalt createNewHashWithSalt(String password) throws NoSuchAlgorithmException, InvalidKeySpecException{
        
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[SALT_LENGTH];
        random.nextBytes(salt);
        
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, ITERATION_COUNT, KEY_LENGTH);
        SecretKeyFactory f = SecretKeyFactory.getInstance(ALGORITHM);
        byte[] hash = f.generateSecret(spec).getEncoded();
        
        HashSalt hashSalt = new HashSalt(DatatypeConverter.printHexBinary(salt), DatatypeConverter.printHexBinary(hash));
        
        return hashSalt;
        
    }

    public static String hashPassword(PasswordSalt passwordSalt) throws NoSuchAlgorithmException, InvalidKeySpecException{
        
        KeySpec spec = new PBEKeySpec(passwordSalt.getPassword().toCharArray(), DatatypeConverter.parseHexBinary(passwordSalt.getSalt()), ITERATION_COUNT, KEY_LENGTH);
        SecretKeyFactory f = SecretKeyFactory.getInstance(ALGORITHM);
        byte[] hash = f.generateSecret(spec).getEncoded();
        
        return DatatypeConverter.printHexBinary(hash);
    }

}
