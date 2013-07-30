package com.lkwy.user.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.Index;

import com.lkwy.common.entity.AbstractAuditablePersistable;
import com.lkwy.group.entity.Group;
import com.lkwy.group.entity.PermissionDTO;


@Entity
@Table(name="user")
public class User extends AbstractAuditablePersistable{
    
	public static enum STATUS{
		INACTIVE(0),
		ACTIVE(1),
		;
		
		private int value;
		
		private STATUS(int value) {
			this.value = value;
		}
		
		public int getValue() {
			return value;
		}
	}
	
    private static final long serialVersionUID = 4980168230977242911L;
    
    @Column(unique=true, nullable=false)
    @Index(name="idx_username")
    private String username;
    private String password;
    private String firstname;
    private String lastname;
    private String nickname;
    @Lob
    private String address;
    private String email;
    
    @Temporal(TemporalType.DATE)
    private Date dob;
    
    private String randomSalt;
    
    @OneToOne
    private Group group;
    
    @Index(name="idx_status")
    private Integer status;
    
    @Transient
    private List<PermissionDTO> permissionList;
    
    private Boolean isAdmin;
    
    public String getFullname(){
    	StringBuilder fullname = new StringBuilder(firstname);
    	fullname.append(" ").append(lastname);
    	return fullname.toString();
    }
    
    
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public String getRandomSalt() {
		return randomSalt;
	}

	public void setRandomSalt(String randomSalt) {
		this.randomSalt = randomSalt;
	}

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	public List<PermissionDTO> getPermissionList() {
		return permissionList;
	}

	public void setPermissionList(List<PermissionDTO> permissionList) {
		this.permissionList = permissionList;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	public Boolean getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(Boolean isAdmin) {
		this.isAdmin = isAdmin;
	}
	
}
