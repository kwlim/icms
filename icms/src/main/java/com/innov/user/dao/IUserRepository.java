package com.innov.user.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

import com.innov.user.entity.User;

@Transactional(readOnly=true)
public interface IUserRepository extends PagingAndSortingRepository<User, String>{
	
	public User findByUsername(String username);
	
	//@Query("SELECT u FROM User u WHERE u.username LIKE ?1 AND u.firstName LIKE ?2 AND u.lastName LIKE ?3 AND u.group.id = ?4 ")
	public Page<User> findAll(Specification<User> spec, Pageable page);
	
	
	
}
