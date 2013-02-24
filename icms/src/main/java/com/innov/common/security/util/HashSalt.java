package com.innov.common.security.util;

public class HashSalt {
    
    private String hash;
    private String salt;
    
    public HashSalt(String salt, String hash) {
        this.salt = salt;
        this.hash = hash;
    }

    public String getHash() {
        return hash;
    }

    public String getSalt() {
        return salt;
    }
    
    

}
