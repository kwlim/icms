package com.lkwy.user.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.lkwy.user.entity.User;

@Transactional(readOnly=true)
public interface IUserRepository extends JpaRepository<User, String>{
	
	public User findByUsername(String username);
	
	public Page<User> findAll(Specification<User> spec, Pageable page);
	
	@Query("SELECT u FROM User u WHERE username = ?1 AND status = ?2")
	public User findByUserNameAndActive(String username, Integer status);
	
}
