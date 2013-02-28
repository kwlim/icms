package com.lkwy.user.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

import com.lkwy.user.entity.User;

@Transactional(readOnly=true)
public interface IUserRepository extends PagingAndSortingRepository<User, String>{
	
	public User findByUsername(String username);
	
	public Page<User> findAll(Specification<User> spec, Pageable page);
	
	
	
}
