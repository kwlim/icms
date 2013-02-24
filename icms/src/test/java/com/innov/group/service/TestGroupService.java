package com.innov.group.service;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.innov.common.AbstractTest;
import com.innov.common.util.DisplayTagUtil;
import com.innov.group.entity.Group;
import com.innov.group.entity.GroupDTO;

public class TestGroupService extends AbstractTest{

	static final Logger LOGGER = LoggerFactory.getLogger(TestGroupService.class);
	
	@Autowired
	GroupService groupService;
	
	@Test
	public void testGetGroupListByName(){
		
		for(int i=0; i<33; i++){
			groupService.addGroup(new GroupDTO("group"+i));
		}
		
		Pageable page = new PageRequest(1, DisplayTagUtil.DEFAULT_PAGE_SIZE, Sort.Direction.ASC, "name");
		String name = "";
		Page<Group> list = groupService.getGroupByName(name, page);
		LOGGER.debug("size: {}", list.getSize());
		LOGGER.debug("totalPages: {}", list.getTotalPages());
		LOGGER.debug("totalElemetns: {}", list.getTotalElements());
		
		Assert.assertNotNull(list);
		
		for(Group group: list){
			LOGGER.debug("{}", group.getName());
		}
	}
	
	@Test
	public void testCountGroupByName(){
		GroupDTO groupDto = new GroupDTO("group_name_1");
		groupService.addGroup(groupDto);
		
		long count = groupService.countGroupByName("group_name");
		Assert.assertEquals(0, count);
		
		groupDto = new GroupDTO("group_name");
		groupService.addGroup(groupDto);
		count = groupService.countGroupByName("group_name");
		Assert.assertEquals(1, count);
	}
	
	@Test
	public void testGetGroupByName(){
		GroupDTO groupDto = new GroupDTO("group_name_1");
		groupService.addGroup(groupDto);
		
		Group group = groupService.getGroupByName("group_name");
		Assert.assertNull(group);
		
		groupDto = new GroupDTO("group_name");
		groupService.addGroup(groupDto);
		group = groupService.getGroupByName("group_name");
		Assert.assertNotNull(group);
	}
	
	@Test
	public void testUpdateGroup(){
		GroupDTO groupDto = new GroupDTO("group_name");
		Group group = groupService.addGroup(groupDto);
		
		groupDto = new GroupDTO("group_name_update");
		groupDto.setId(group.getId());
		
		groupService.updateGroup(groupDto);
		group = groupService.addGroup(groupDto);
		
		Assert.assertNotNull(group);
		Assert.assertEquals("group_name_update", group.getName());
	}
	
	@Test
	public void testAddGroup(){
		
		GroupDTO groupDto = new GroupDTO("group_name");
		Group group = groupService.addGroup(groupDto);
		
		Assert.assertNotNull(group);
		Assert.assertEquals("group_name", group.getName());
	}
	
}
