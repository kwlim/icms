package com.lkwy.group.entity;

public class PermissionDTO {
    
    private String permissionKey;

    public PermissionDTO(String permissionKey) {
        this.permissionKey = permissionKey;
    }
    
    public String getPermissionKey() {
        return permissionKey;
    }

    public void setPermissionKey(String permissionKey) {
        this.permissionKey = permissionKey;
    }
    
}
