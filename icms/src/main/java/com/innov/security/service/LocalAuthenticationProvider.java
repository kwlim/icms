package com.innov.security.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.innov.group.entity.PermissionDTO;
import com.innov.home.HomeController;
import com.innov.user.entity.User;

public class LocalAuthenticationProvider implements AuthenticationProvider {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(HomeController.class);
    
    @Autowired
    SecurityManager manager;
    
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        
        String username = authentication.getPrincipal().toString();
        String password = authentication.getCredentials().toString();
        
        checkNotEmptyUsernamePasssword(username, password);
        
        LOGGER.debug("authenticate: {}", authentication.getPrincipal());
        
        User user = manager.authenticateUser(username, password);
        
        //add in permission here 
        List<GrantedAuthority> gaList = convertUserPermissionsToGrantedAuthorities(user.getPermissionList());
        
        LocalUserDetails userDetails = new LocalUserDetails(user.getFullname(), user.getNickname(), username, password, gaList);
        UsernamePasswordAuthenticationToken result = new UsernamePasswordAuthenticationToken(userDetails, password, gaList);
        
        return result;
    }
    
    private List<GrantedAuthority> convertUserPermissionsToGrantedAuthorities(List<PermissionDTO> permissionList){
        List<GrantedAuthority> gaList = new ArrayList<GrantedAuthority>();
        
        for(PermissionDTO permission: permissionList){
            gaList.add(new SimpleGrantedAuthority(permission.getPermissionKey()));
        }
        
        return gaList;
    }
    
    private void checkNotEmptyUsernamePasssword(String username, String password) throws BadCredentialsException{
        if(StringUtils.isEmpty(username)){
            throw new BadCredentialsException("error.login.empty.username");
        }
        else if(StringUtils.isEmpty(password)){
            throw new BadCredentialsException("error.login.empty.password");
        }
    }
    
    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }

}
