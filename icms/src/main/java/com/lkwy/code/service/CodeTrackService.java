package com.lkwy.code.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lkwy.code.dao.ICodeTrackRepository;
import com.lkwy.code.entity.CodeTrack;
import com.lkwy.common.util.CommonUtil;

@Service
public class CodeTrackService {

	public static String JOB_CODE_KEY = "job_code";
	
	@Autowired
	private ICodeTrackRepository codeTrackRepo;
	
	public void incrementJobCode(){
		CodeTrack jobCodeTrack = getNextJobCodeTrack();
		codeTrackRepo.save(jobCodeTrack);
	}
	
	public String getNextJobCode(){
		
		CodeTrack jobCodeTrack = getNextJobCodeTrack();
		
		int counter = jobCodeTrack.getCounter();
		
		if(counter < 100000){
			return CommonUtil.convertNumberToStringCode(5, counter);
		}else{
			return CommonUtil.convertNumberToStringCode(counter);
		}
	}
	
	public CodeTrack getNextJobCodeTrack(){
		CodeTrack jobCodeTrack = getJobCodeTrackCreateIfNull();
		jobCodeTrack.setCounter(jobCodeTrack.getCounter()+1);
		
		return jobCodeTrack;
	}
	
	protected CodeTrack getJobCodeTrackCreateIfNull(){
		CodeTrack jobCodeTrack =  getCodeTrackByKey(JOB_CODE_KEY);
		if(jobCodeTrack == null){
			jobCodeTrack = codeTrackRepo.save(new CodeTrack(JOB_CODE_KEY, 0));
		}
		return jobCodeTrack;
	}
	
	protected CodeTrack getCodeTrackByKey(String key){
		return codeTrackRepo.findByModuleKey(key);
	}
	
	protected CodeTrack saveCodeTrack(CodeTrack codeTrack){
		return codeTrackRepo.save(codeTrack);
	}
	
}
