package com.innov.group.dao;

import org.springframework.data.repository.CrudRepository;

import com.innov.group.entity.GroupPermission;

public interface IGroupPermissionRepository extends CrudRepository<GroupPermission, String>{
	
}
