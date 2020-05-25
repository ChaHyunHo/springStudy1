package com.camel.board.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.camel.board.mapper.SampleMapper;
import com.camel.board.service.BoardService;

@Service
public class BoardServiceImpl implements BoardService {
	
	@Autowired
	private SampleMapper sampleMapper;
	
	@Override
	public String getTime() throws Exception {
		return sampleMapper.getTime() ;
	}

}
