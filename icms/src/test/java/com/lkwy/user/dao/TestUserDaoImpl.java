package com.lkwy.user.dao;

import java.util.List;

import junit.framework.Assert;

import org.h2.tools.DeleteDbFiles;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.lkwy.user.dao.IUserDao;
import com.lkwy.user.entity.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"file:src/main/webapp/WEB-INF/spring-context.xml"
		, "file:src/main/webapp/WEB-INF/dispatcher-servlet.xml"})
public class TestUserDaoImpl {
	
	static final Logger LOGGER = LoggerFactory.getLogger(TestUserDaoImpl.class);
	private static org.h2.tools.Server h2Server;
	
	@Autowired
	IUserDao userDao;
	
	@BeforeClass
	public static void init() throws Exception{
		DeleteDbFiles.execute("", "Drools", true);
        h2Server = org.h2.tools.Server.createTcpServer(new String[0]);
        h2Server.start();
	}
	
	@AfterClass
	public static void destroy() throws Exception{
		h2Server.stop();
        DeleteDbFiles.execute("", "Drools", true);
	}
	
	private User createNewUser(){
		
		User user = new User();
		user.setUsername("test");
		user.setPassword("password");
		userDao.add(user);
		
		LOGGER.debug("create new user[{}]", user.getId());
		
		return user;
	}
	
	@Test
	@Transactional
	public void testDeleteById(){
		
		User newUser = new User();
		newUser.setUsername("usernameOld");
		newUser.setPassword("password");
		
		userDao.add(newUser);
		
		String id = newUser.getId();
		
		User dbUser = userDao.findById(id);
		
		Assert.assertNotNull(dbUser);
		
		userDao.deleteById(id);
		
		dbUser = userDao.findById(id);
		
		Assert.assertNull(dbUser);
	}
	
	@Test
	@Transactional
	public void testDeleteEntity(){
		
		User newUser = new User();
		newUser.setUsername("usernameOld");
		newUser.setPassword("password");
		
		userDao.add(newUser);
		
		String id = newUser.getId();
		
		User dbUser = userDao.findById(id);
		
		Assert.assertNotNull(dbUser);
		
		userDao.delete(dbUser);
		
		dbUser = userDao.findById(id);
		
		Assert.assertNull(dbUser);
	}
	
	@Test
	@Transactional
	public void testFindBy(){
		
		User newUser = new User();
		newUser.setUsername("usernameOld");
		newUser.setPassword("password");
		
		userDao.add(newUser);
		
		String conditions = "username = ? ";
		String[] params = {newUser.getUsername()};
		
		List<User> userList = userDao.findBy(conditions, params, null, null, null, null);
		
		LOGGER.debug("userList[{}]", userList.size());
		
		Assert.assertNotNull(userList);
		Assert.assertEquals(1, userList.size());
	}
	
	@Test
	@Transactional
	public void testFindCountBy(){
		User newUser = new User();
		newUser.setUsername("usernameOld");
		newUser.setPassword("password");
		
		userDao.add(newUser);
		
		String conditions = "username = ? ";
		String[] params = {newUser.getUsername()};
		
		int size = userDao.findCountBy(conditions, params);
		
		LOGGER.debug("userList[{}]", size);
		
		Assert.assertEquals(1, size);
	}
	
	@Test
	@Transactional
	public void testUpdateUser(){
		
		User newUser = new User();
		newUser.setUsername("usernameOld");
		newUser.setPassword("password");
		
		userDao.add(newUser);
		
		User dbUser = userDao.findById(newUser.getId());
		LOGGER.debug("dbUser[{}|{}]", dbUser.getUsername(), dbUser.getPassword());
		
		dbUser.setUsername("usernameNew");
		userDao.update(dbUser);
		LOGGER.debug("dbUser[{}|{}]", dbUser.getUsername(), dbUser.getPassword());
		
		Assert.assertEquals("usernameNew", dbUser.getUsername());
		Assert.assertEquals("password", dbUser.getPassword());
	}
	
	@Test
	@Transactional
	public void testCreateNewUser(){
		User newUser = new User();
		newUser.setUsername("usernameNew");
		newUser.setPassword("password");
		
		userDao.add(newUser);
		
		User dbUser = userDao.findById(newUser.getId());
		
		LOGGER.debug("dbUser[{}|{}]", dbUser.getUsername(), dbUser.getPassword());
		
		Assert.assertEquals(newUser.getUsername(), dbUser.getUsername());
		Assert.assertEquals(newUser.getPassword(), dbUser.getPassword());
	}
	
	@Test
	@Transactional
	public void testFindById_withCorrectId(){
		
		User newUser = createNewUser();
		User dbUser = userDao.findById(newUser.getId());
		
		LOGGER.debug("dbUser[{}|{}]", dbUser.getUsername(), dbUser.getPassword());
		
		Assert.assertEquals(newUser.getUsername(), dbUser.getUsername());
		Assert.assertEquals(newUser.getPassword(), dbUser.getPassword());
	}
	
	@Test
	public void testFindById_withNullId(){
		try{
			userDao.findById(null);
			Assert.assertTrue(false);
		}
		catch(IllegalArgumentException eg){
			Assert.assertTrue(true);
		}
	}

}
