package com.lkwy.group.service;


import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.lkwy.common.util.CommonUtil;
import com.lkwy.group.dao.IGroupPermissionRepository;
import com.lkwy.group.dao.IGroupRepository;
import com.lkwy.group.entity.Group;
import com.lkwy.group.entity.GroupDTO;
import com.lkwy.group.entity.GroupPermission;

@Service
public class GroupService {
	
	public static String ADMIN_GROUP_NAME = "Admin";
	
	@Autowired
	IGroupRepository groupRepo;
	
	@Autowired
	IGroupPermissionRepository groupPermissionRepo;
	
	public Page<Group> getGroupByName(String name, Pageable page){
		String wildCardName = CommonUtil.addWildCard(name);
		return groupRepo.findByLikeName(wildCardName, page);
	}
	
	public void deleteGroupById(String id){
		groupRepo.delete(id);
	}
	
	public List<Group> getAllGroup(){
		Sort sort = new Sort("name");
		return groupRepo.findAll(sort); 
	}
	
	public Group getGroupById(String id){
		return groupRepo.findOne(id);
	}
	
	public Long countGroupByName(String name){
		return groupRepo.countByName(name);
	}
	
	public Group getGroupByName(String name){
		return groupRepo.findOneByName(name);
	}
	
	public void updateGroup(GroupDTO groupDto) throws DataIntegrityViolationException{
        
        Group group = groupRepo.findOne(groupDto.getId());
        
        //remove all existing permission (orphanRemoval=true in OneToMany relationship)
        group.getPermissionList().clear();
        
        group.setName(groupDto.getName());
        
        if(groupDto.getPermissionArray() != null){
            for(String permission: groupDto.getPermissionArray()){
                group.addPermission(new GroupPermission(permission));
            }
        }
        
        groupRepo.save(group);
    }
	
	public Group addGroup(Group group){
		return groupRepo.save(group);
	}
	
	public Group addGroup(GroupDTO groupDto){
		Group group = new Group();
        group.setName(groupDto.getName());
        
        if(groupDto.getPermissionArray() != null){
            for(String permission: groupDto.getPermissionArray()){
                group.addPermission(new GroupPermission(permission));
            }
        }
        
        return groupRepo.save(group);
	}
	
	public List<String> getAllPermissionList(){
        
        List<String> permissionList =  Lists.newArrayList();
        Map<String, List<String>> roleMap = getAllPermissionMap();
        
        for(Entry<String, List<String>> entry: roleMap.entrySet()){
            permissionList.addAll(entry.getValue());
        }
        
        return permissionList;
    }
    
    public Map<String, List<String>> getAllPermissionMap(){
        
        Map<String, List<String>> roleMap = Maps.newLinkedHashMap();
        
        roleMap.put(GroupPermission.MODULE_GROUP, getModulePermissionList(GroupPermission.MODULE_GROUP, true, true, true));
        roleMap.put(GroupPermission.MODULE_USER, getModulePermissionList(GroupPermission.MODULE_USER, true, true, true));
        
        return roleMap;
    }
    
    public List<String> getModulePermissionList(String module, boolean view, boolean addEdit, boolean delete){
        List<String> modulePermissionList = Lists.newArrayList();
        
        if(view){
            StringBuilder permission = new StringBuilder(GroupPermission.PREFIX);
            permission.append(module).append("_").append(GroupPermission.ACTION_VIEW);
            modulePermissionList.add(permission.toString());
        }
        if(addEdit){
            StringBuilder permission = new StringBuilder(GroupPermission.PREFIX);
            permission.append(module).append("_").append(GroupPermission.ACTION_ADD_EDIT);
            modulePermissionList.add(permission.toString());
        }
        if(delete){
            StringBuilder permission = new StringBuilder(GroupPermission.PREFIX);
            permission.append(module).append("_").append(GroupPermission.ACTION_DELETE);
            modulePermissionList.add(permission.toString());
        }
        return modulePermissionList;
    }

}
