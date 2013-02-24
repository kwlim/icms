package com.innov.group.entity;

import org.hibernate.validator.constraints.NotEmpty;


public class GroupDTO {
    
    private String id;
    
    @NotEmpty
    private String name;
    
    private String[] permissionArray;
    
    private boolean defaultAdminGroup;
    
    public GroupDTO() {
		
	}
    
    public GroupDTO(String name) {
		this.name = name;
	}
    
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String[] getPermissionArray() {
        return permissionArray;
    }
    public void setPermissionArray(String[] permissionArray) {
        this.permissionArray = permissionArray;
    }
    public boolean isDefaultAdminGroup() {
        return defaultAdminGroup;
    }
    public void setDefaultAdminGroup(boolean defaultAdminGroup) {
        this.defaultAdminGroup = defaultAdminGroup;
    }
    
}
