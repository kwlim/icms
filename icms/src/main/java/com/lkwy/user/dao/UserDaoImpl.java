package com.lkwy.user.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.lkwy.common.dao.AbstractJpaDao;
import com.lkwy.user.entity.User;

@Repository
public class UserDaoImpl extends AbstractJpaDao<User> implements IUserDao {
	
	static final Logger LOGGER = LoggerFactory.getLogger(UserDaoImpl.class);
	
	@Override
	public void initClazz() {
		setClazz(User.class);
	}
	
}
