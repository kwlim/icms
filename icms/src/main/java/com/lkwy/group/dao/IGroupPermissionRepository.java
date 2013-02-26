package com.lkwy.group.dao;

import org.springframework.data.repository.CrudRepository;

import com.lkwy.group.entity.GroupPermission;

public interface IGroupPermissionRepository extends CrudRepository<GroupPermission, String>{
	
}
