package com.camel.board.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.camel.board.mapper.vo.BoardVO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/resources/spring/context-**.xml"})
public class BoardMapperTest {
	private static final Logger logger = LoggerFactory.getLogger(BoardMapperTest.class);
	
	@Autowired
	private BoardMapper boardMapper;
	
	@Test
	public void boardDetail() throws Exception {
		String no = "1";
		
		BoardVO board =  boardMapper.boardDetail(no);
		
		logger.info(board.toString());
	}

}
