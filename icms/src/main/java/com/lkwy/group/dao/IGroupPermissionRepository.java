package com.lkwy.group.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lkwy.group.entity.GroupPermission;

public interface IGroupPermissionRepository extends JpaRepository<GroupPermission, String>{
	
}
