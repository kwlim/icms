package com.lkwy.code.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lkwy.code.entity.CodeTrack;

public interface ICodeTrackRepository extends JpaRepository<CodeTrack, String>{

	public CodeTrack findByModuleKey(String trackKey);
	
}
