package com.lkwy.item.entity;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import com.lkwy.category.entity.ItemCategory;

@StaticMetamodel(Item.class)
public class Item_ {
	
	public static volatile SingularAttribute<Item, String> code;
	public static volatile SingularAttribute<Item, String> name;
	public static volatile SingularAttribute<Item, ItemCategory> category;

}
