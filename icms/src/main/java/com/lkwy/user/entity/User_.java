package com.lkwy.user.entity;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import com.lkwy.group.entity.Group;

@StaticMetamodel(User.class)
public class User_ {
	
	public static volatile SingularAttribute<User, String> username;
	public static volatile SingularAttribute<User, String> firstname;
	public static volatile SingularAttribute<User, String> lastname;
	public static volatile SingularAttribute<User, Group> group;

}
