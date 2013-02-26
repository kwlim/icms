package com.lkwy.common.security.util;

public class PasswordSalt {
    
    private String salt;
    private String password;
    
    public PasswordSalt(String salt, String password) {
        this.salt = salt;
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public String getPassword() {
        return password;
    }

}
