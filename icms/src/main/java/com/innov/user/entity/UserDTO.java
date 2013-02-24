package com.innov.user.entity;

import org.hibernate.validator.constraints.NotEmpty;

public class UserDTO extends UserProfileDTO{
    
    private boolean edit;
    
    @NotEmpty
    private String groupId;
    
    public UserDTO() {
		
	}
    
    public UserDTO(String username, String firstName, String lastName, String groupId, String password) {
		setUsername(username);
		setFirstname(firstName);
		setLastname(lastName);
		setGroupId(groupId);
		setPassword(password);
	}
    
    public boolean isEdit() {
        return edit;
    }

    public void setEdit(boolean edit) {
        this.edit = edit;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }
    
}
