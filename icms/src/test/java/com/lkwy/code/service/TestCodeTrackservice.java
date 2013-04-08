package com.lkwy.code.service;

import junit.framework.Assert;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.lkwy.code.entity.CodeTrack;
import com.lkwy.common.AbstractTest;

public class TestCodeTrackservice extends AbstractTest{
	
	static final Logger LOGGER = LoggerFactory.getLogger(TestCodeTrackservice.class);
	
	@Autowired
	private CodeTrackService codeTrackService;
	
	@Test
	public void testincrementJobCode_case3(){
		
		int counter = 99999;
		codeTrackService.saveCodeTrack(new CodeTrack(CodeTrackService.JOB_CODE_KEY, counter));
		
		CodeTrack code = codeTrackService.getJobCodeTrackCreateIfNull();
		LOGGER.debug("counter:{}", code.getCounter());
		Assert.assertEquals(new Integer(counter), code.getCounter());
		
		codeTrackService.incrementJobCode();
		
		code = codeTrackService.getJobCodeTrackCreateIfNull();
		LOGGER.debug("counter:{}", code.getCounter());
		Assert.assertEquals(new Integer(counter+1), code.getCounter());
	}
	
	@Test
	public void testincrementJobCode_case2(){
		
		int counter = 9999999;
		codeTrackService.saveCodeTrack(new CodeTrack(CodeTrackService.JOB_CODE_KEY, counter));
		
		CodeTrack code = codeTrackService.getJobCodeTrackCreateIfNull();
		LOGGER.debug("counter:{}", code.getCounter());
		Assert.assertEquals(new Integer(counter), code.getCounter());
		
		codeTrackService.incrementJobCode();
		
		code = codeTrackService.getJobCodeTrackCreateIfNull();
		LOGGER.debug("counter:{}", code.getCounter());
		Assert.assertEquals(new Integer(counter+1), code.getCounter());
	}
	
	@Test
	public void testincrementJobCode(){
		CodeTrack code = codeTrackService.getNextJobCodeTrack();
		LOGGER.debug("counter:{}", code.getCounter());
		Assert.assertEquals(new Integer(1), code.getCounter());
		
		codeTrackService.incrementJobCode();
		
		code = codeTrackService.getJobCodeTrackCreateIfNull();
		LOGGER.debug("counter:{}", code.getCounter());
		Assert.assertEquals(new Integer(2), code.getCounter());
	}
	
	@Test
	public void testgetNextJobCode_before_exceed_third(){
		int counter = 9999998;
		codeTrackService.saveCodeTrack(new CodeTrack(CodeTrackService.JOB_CODE_KEY, counter));
		String code = codeTrackService.getNextJobCode();
		LOGGER.debug("code:{}", code);
		Assert.assertEquals("9999999", code);
	}
	
	@Test
	public void testgetNextJobCode_after_exceed_third(){
		int counter = 9999999;
		codeTrackService.saveCodeTrack(new CodeTrack(CodeTrackService.JOB_CODE_KEY, counter));
		String code = codeTrackService.getNextJobCode();
		LOGGER.debug("code:{}", code);
		Assert.assertEquals("10000000", code);
	}
	
	@Test
	public void testgetNextJobCode_before_exceed_second(){
		int counter = 999998;
		codeTrackService.saveCodeTrack(new CodeTrack(CodeTrackService.JOB_CODE_KEY, counter));
		String code = codeTrackService.getNextJobCode();
		LOGGER.debug("code:{}", code);
		Assert.assertEquals("999999", code);
	}
	
	@Test
	public void testgetNextJobCode_after_exceed_second(){
		int counter = 999999;
		codeTrackService.saveCodeTrack(new CodeTrack(CodeTrackService.JOB_CODE_KEY, counter));
		String code = codeTrackService.getNextJobCode();
		LOGGER.debug("code:{}", code);
		Assert.assertEquals("1000000", code);
	}
	
	@Test
	public void testgetNextJobCode_after_exceed_first(){
		int counter = 99999;
		codeTrackService.saveCodeTrack(new CodeTrack(CodeTrackService.JOB_CODE_KEY, counter));
		String code = codeTrackService.getNextJobCode();
		LOGGER.debug("code:{}", code);
		Assert.assertEquals("100000", code);
	}
	
	@Test
	public void testgetNextJobCode_before_exceed_first(){
		int counter = 99998;
		codeTrackService.saveCodeTrack(new CodeTrack(CodeTrackService.JOB_CODE_KEY, counter));
		String code = codeTrackService.getNextJobCode();
		LOGGER.debug("code:{}", code);
		Assert.assertEquals("99999", code);
	}
	
	@Test
	public void testgetNextJobCode(){
		String code = codeTrackService.getNextJobCode();
		LOGGER.debug("code:{}", code);
		Assert.assertEquals("00001", code);
	}

}
