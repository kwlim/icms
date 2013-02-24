package com.innov.group.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.innov.group.entity.Group;

public interface IGroupRepository extends PagingAndSortingRepository<Group, String>{
	
	public Group findOneByName(String name);
	
	@Query("SELECT COUNT(*) FROM Group WHERE name = ?1")
	public Long countByName(String name);
	
	public List<Group> findAll(Sort sort);
	
	@Query("SELECT g FROM Group g WHERE g.name LIKE ?1")
	public Page<Group> findByLikeName(String name, Pageable page);
}
