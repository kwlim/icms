package com.lkwy.security.service;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class LocalUserDetails extends User{
    
    private static final long serialVersionUID = -4252890255334533219L;

    private String fullname;
    private String nickname;
    
    public LocalUserDetails(String fullname, String nickname, String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.fullname = fullname;
        this.nickname = nickname;
    }
    
    public String getFullname() {
        return fullname;
    }

    public String getNickname() {
        return nickname;
    }
}
