package com.lkwy.group.entity;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.lkwy.common.entity.AbstractPersistable;

@Entity
@Table(name="group_permission")
public class GroupPermission extends AbstractPersistable{
    
    private static final long serialVersionUID = 4282069916304889426L;
    
    public static String ADMIN = "ADMIN";
    
    public static String PREFIX = "ROLE_";
    
    public static final String ACTION_ADD_EDIT = "ADD_EDIT";
    public static final String ACTION_DELETE = "DELETE";
    public static final String ACTION_VIEW = "VIEW";
    
    public static final String MODULE_GROUP = "GROUP";
    public static final String MODULE_USER = "USER";
    public static final String MODULE_BILLING = "BILLING";
    
    public static final String MODULE_ITEM_CATEGORY = "ITEM_CATEGORY";
    public static final String MODULE_ITEM = "ITEM";
    public static final String MODULE_COMPANY = "COMPANY";
    public static final String MODULE_CUSTOMER = "CUSTOMER";
    public static final String MODULE_JOB = "JOB";
    public static final String MODULE_PURCHASE = "PURCHASE";
    public static final String MODULE_REPORT = "REPORT";
    public static final String MODULE_VENDOR = "VENDOR";
    
    private String permissionKey;
    
    @OneToOne
    private Group group;
    
    public GroupPermission() {
        
    }
    
    public GroupPermission(String permissionKey) {
        this.permissionKey = permissionKey;
    }
    
    public String getPermissionKey() {
        return permissionKey;
    }
    public void setPermissionKey(String permissionKey) {
        this.permissionKey = permissionKey;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }
    
}

