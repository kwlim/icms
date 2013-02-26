package com.lkwy.user.service;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.lkwy.common.security.util.HashSalt;
import com.lkwy.common.security.util.PasswordGeneratorUtil;
import com.lkwy.common.security.util.PasswordSalt;
import com.lkwy.common.util.CommonUtil;
import com.lkwy.group.entity.Group;
import com.lkwy.group.service.GroupService;
import com.lkwy.user.dao.IUserRepository;
import com.lkwy.user.dao.UserSpecifications;
import com.lkwy.user.entity.User;
import com.lkwy.user.entity.UserDTO;
import com.lkwy.user.entity.UserProfileDTO;

@Service
public class UserService {
	
	static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);
	
	public static String ADMIN_USER_NAME = "admin";
	
	@Autowired
	private IUserRepository userRepo;
	
	@Autowired
	private GroupService groupService;
	
	public Page<User> getUserByUsernameFirstnameLastnameGroup(String username, String firstname, String lastname, String groupId, Pageable page){
		String usernameWc = CommonUtil.addWildCard(username);
		String firstnameWc = CommonUtil.addWildCard(firstname);
		String lastnameWc = CommonUtil.addWildCard(lastname);
		
		return userRepo.findAll(UserSpecifications.byLikeUsernameFirstnameLastnameGroupId(usernameWc, firstnameWc, lastnameWc, groupId), page);
	}
	
	public User addUser(UserDTO form) throws DataIntegrityViolationException, Exception{
	    
	    User user = new User();
	    PropertyUtils.copyProperties(user, form);
	    user.setId(null);
	    
	    Group group = groupService.getGroupById(form.getGroupId());
        user.setGroup(group);
	    
	    HashSalt hashSalt = PasswordGeneratorUtil.createNewHashWithSalt(form.getPassword());
	    user.setPassword(hashSalt.getHash());
	    user.setRandomSalt(hashSalt.getSalt());
	    
	    return userRepo.save(user);
	}
	
	public void updateUserProfile(UserProfileDTO form) throws DataIntegrityViolationException, Exception{
        
        User user = userRepo.findOne(form.getId());
        
        user.setAddress(form.getAddress());
        user.setDob(form.getDob());
        user.setEmail(form.getEmail());
        user.setFirstname(form.getFirstname());
        user.setLastname(form.getLastname());
        user.setNickname(form.getNickname());
        
        //update password
        if(!StringUtils.isEmpty(form.getPassword())){
            String hash = PasswordGeneratorUtil.hashPassword(new PasswordSalt(user.getRandomSalt(), form.getPassword()));
            user.setPassword(hash);
        }
        
        userRepo.save(user);
    } 
	
	public User updateUser(UserDTO form) throws DataIntegrityViolationException, Exception{
	    
		User user = userRepo.findOne(form.getId());
	    
	    user.setAddress(form.getAddress());
	    user.setDob(form.getDob());
	    user.setEmail(form.getEmail());
	    user.setFirstname(form.getFirstname());
        user.setLastname(form.getLastname());
	    user.setNickname(form.getNickname());
	    
	    Group group = groupService.getGroupById(form.getGroupId());
	    user.setGroup(group);
	    
	    //update password
	    if(!StringUtils.isEmpty(form.getPassword())){
	        String hash = PasswordGeneratorUtil.hashPassword(new PasswordSalt(user.getRandomSalt(), form.getPassword()));
	        user.setPassword(hash);
	    }
	    
	    return userRepo.save(user);
	}
	
	/*public List<User> getUserByUsernameFullnameGroup(String username, String fullname, String groupId, String sort, Boolean desc, Integer start, Integer rows){
	    if(StringUtils.isEmpty(sort)){
	        sort = "username";
	    }
	    if(desc == null){
	        desc = false;
	    }
	    if(start == null){
	        start = 0;
	    }
	    
	    return userDao.findUserByUsernameFullname(username, fullname, groupId, sort, desc, start, rows);
	}
	
	public int getUserByUsernameFullnameGroupCount(String username, String fullname, String groupId){
	    
	    return userDao.findUserCountByUsernameFullnameCount(username, fullname, groupId);
	}*/
	
	public User getUserByUsername(String username){
	    return userRepo.findByUsername(username);
	}
	
	public void deleteUserById(String id){
		userRepo.delete(id);
	}
	
	public void deleteUser(User user){
		userRepo.delete(user);
	}
	
	public User addUser(User user){
		return userRepo.save(user);
	}
	
	public User updateUser(User user){
		return userRepo.save(user);
	}
	
	public User getUserById(String id){
		return userRepo.findOne(id);
	}
	
}
