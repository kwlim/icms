package com.innov.security.service;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.innov.common.security.util.HashSalt;
import com.innov.common.security.util.PasswordGeneratorUtil;
import com.innov.common.security.util.PasswordSalt;
import com.innov.group.entity.Group;
import com.innov.group.entity.GroupPermission;
import com.innov.group.entity.PermissionDTO;
import com.innov.group.service.GroupService;
import com.innov.user.entity.User;
import com.innov.user.service.UserService;

@Service
public class SecurityManager {
    
	static final Logger LOGGER = LoggerFactory.getLogger(SecurityManager.class);
	
	@Autowired
    private UserService userService;
    
    @Autowired
    private GroupService groupService;
    
    @PostConstruct
    public void init(){
//        initAdminGroup();
//        initAdminUser();
    }
    
    @Transactional
    public void initAdminGroup(){
        
        Group adminGroup = groupService.getGroupByName(GroupService.ADMIN_GROUP_NAME);
        
        if(adminGroup == null){
            
            adminGroup = new Group();
            adminGroup.setName(GroupService.ADMIN_GROUP_NAME);
            
            for(String permission: groupService.getAllPermissionList()){
                GroupPermission adminPermission = new GroupPermission(permission);
                adminGroup.addPermission(adminPermission);
            }
            
            groupService.addGroup(adminGroup);
        }
    }
    
    @Transactional
    public void initAdminUser(){
        User adminUser = userService.getUserByUsername(UserService.ADMIN_USER_NAME);
        
        if(adminUser == null){
            //create admin user with password admin
            adminUser = new User();
            adminUser.setUsername(UserService.ADMIN_USER_NAME);
            adminUser.setFirstname(UserService.ADMIN_USER_NAME);
            adminUser.setLastname(UserService.ADMIN_USER_NAME);
            adminUser.setNickname(UserService.ADMIN_USER_NAME);
            
            Group adminGroup = groupService.getGroupByName(GroupService.ADMIN_GROUP_NAME);
            adminUser.setGroup(adminGroup);
            
            try{
                HashSalt hashSalt = PasswordGeneratorUtil.createNewHashWithSalt("admin");
                adminUser.setPassword(hashSalt.getHash());
                adminUser.setRandomSalt(hashSalt.getSalt());
            }
            catch(Exception e){
                LOGGER.error("Error in generating hash ", e);
            }
            
            userService.addUser(adminUser);
        }
        
    }
	
    @Transactional
    public User authenticateUser(String username, String password) throws BadCredentialsException{
        
    	User user = userService.getUserByUsername(username);
        
        if(user == null){
            throw new BadCredentialsException("error.login.invalid");
        }
        
        try{
            String hash  = PasswordGeneratorUtil.hashPassword(new PasswordSalt(user.getRandomSalt(), password));
            
            if(!StringUtils.equals(hash, user.getPassword())){
                throw new BadCredentialsException("error.login.invalid");
            }
        }
        catch(NoSuchAlgorithmException e){
            LOGGER.error("Error in comparing user password", e);
            throw new BadCredentialsException("error.login.error");
        }
        catch(InvalidKeySpecException e){
            LOGGER.error("Error in comparing user password", e);
            throw new BadCredentialsException("error.login.error");
        }
        
        user.setPermissionList(convertPermissionListToDTO(user.getGroup().getPermissionList()));
        
        return user;
        
    }
    
    protected List<PermissionDTO> convertPermissionListToDTO(List<GroupPermission> permissionList){
        
        List<PermissionDTO> permissionDtoList = Lists.newArrayList();
        for(GroupPermission permission: permissionList){
            permissionDtoList.add(new PermissionDTO(permission.getPermissionKey()));
        }
        
        return permissionDtoList;
    }
    
}
