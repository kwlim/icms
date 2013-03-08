package com.lkwy.user.service;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.lkwy.common.AbstractTest;
import com.lkwy.common.util.DisplayTagUtil;
import com.lkwy.group.entity.Group;
import com.lkwy.group.entity.GroupDTO;
import com.lkwy.group.service.GroupService;
import com.lkwy.user.entity.User;
import com.lkwy.user.entity.UserDTO;

public class TestUserService extends AbstractTest{
	
	static final Logger LOGGER = LoggerFactory.getLogger(TestUserService.class);
	
	@Autowired
	UserService userService;
	
	@Autowired
	GroupService groupService;
	
	@Test
	public void testGetActiveUserByUsername(){
		User user = userService.getActiveUserByUsername("admin3");
		LOGGER.debug("user = " + user);
		Assert.assertNotNull(user); 
	}
	
	@Test
	public void testGetUserByUsernameFirstnameLastnameGroup(){
		
		Pageable page = new PageRequest(0, DisplayTagUtil.DEFAULT_PAGE_SIZE, Sort.Direction.ASC, "username");
		
		Page<User> list = userService.getUserByUsernameFirstnameLastnameGroup("", "", "", "", page);
		
		LOGGER.debug("size: {}", list.getSize());
		LOGGER.debug("totalPages: {}", list.getTotalPages());
		LOGGER.debug("totalElemetns: {}", list.getTotalElements());
		
		Assert.assertNotNull(list);
		
		for(User user: list){
			LOGGER.debug("{}", user.getUsername());
		}
	}
	
	@Test
	public void testAddUser() throws Exception{
		
		GroupDTO groupDto = new GroupDTO("new_group");
		
		Group newGroup = groupService.addGroup(groupDto);
		
		UserDTO user = new UserDTO("username", "firstname", "lastname", newGroup.getId(), "password");
		User newUser = userService.addUser(user);
		
		Assert.assertNotNull(newUser);
		Assert.assertEquals("username", newUser.getUsername());
		
	}
	
	
}
