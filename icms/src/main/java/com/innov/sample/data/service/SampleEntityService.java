package com.innov.sample.data.service;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.innov.common.util.CommonUtil;
import com.innov.sample.data.dao.ISampleEntityObjectRepository;
import com.innov.sample.data.entity.SampleEntityObject;

@Service
public class SampleEntityService {
	
	@Autowired
	ISampleEntityObjectRepository repo;
	
	@PostConstruct
	public void initSampleEntityService(){
		//create30RecordsIfNotExist();
	}
	
	protected void create30RecordsIfNotExist(){
		
		long totalRecords = repo.count();
		
		if(totalRecords < 30){
			
			for(int i=0; i<(30-totalRecords); i++){
				repo.save(new SampleEntityObject("testing_"+i));
			}
			
		}
		
	}
	
	public List<SampleEntityObject> findEntityByName(String name){
		String wildCard = CommonUtil.addWildCard(name);
		return repo.findByLikeName(wildCard);
	}
	
	public SampleEntityObject saveEntity(SampleEntityObject entity){
		return repo.save(entity);
	}
	
	public SampleEntityObject getEntityById(String id){
		return repo.findOne(id);
	}
	

}
