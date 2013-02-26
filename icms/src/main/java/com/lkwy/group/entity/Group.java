package com.lkwy.group.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

import com.google.common.collect.Lists;
import com.lkwy.common.entity.AbstractAuditablePersistable;

@Entity
@Table(name="user_group")
public class Group extends AbstractAuditablePersistable{
     
    private static final long serialVersionUID = -5427863357610441113L;
    
    @Column(unique=true)
    @NotEmpty
    private String name;
    
    @OneToMany(fetch=FetchType.LAZY, cascade = CascadeType.ALL, mappedBy="group", orphanRemoval=true)
    private List<GroupPermission> permissionList;
    
    public Group() {
    	permissionList = Lists.newArrayList();
	}
    
    public Group(String name) {
		this.name = name;
	}
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public void addPermission(GroupPermission permission){
        if(permissionList == null){
            permissionList = Lists.newArrayList();
        }
        permission.setGroup(this);
        
        permissionList.add(permission);
    }
    
    public List<GroupPermission> getPermissionList() {
        return permissionList;
    }

    public void setPermissionList(List<GroupPermission> permissionList) {
        this.permissionList = permissionList;
    }
    
}
