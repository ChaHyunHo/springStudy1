package com.camel.board.mapper;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mysql.cj.util.StringUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/resources/spring/context-**.xml"})
public class SampleMapperTest {
	private static final Logger logger = LoggerFactory.getLogger(SampleMapperTest.class);
	
	@Autowired
	private SampleMapper sampleMapper; 

	@Test
	public void test() {
		String className = sampleMapper.getClass().getName();
		String now = sampleMapper.getTime();
		logger.info(now);
		logger.info(className);
		
		assertTrue(StringUtils.startsWithIgnoreCase(className,"com.sun.proxy"));
		
	}

}
