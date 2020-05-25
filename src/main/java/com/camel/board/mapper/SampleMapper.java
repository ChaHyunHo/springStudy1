package com.camel.board.mapper;

import org.apache.ibatis.annotations.Select;

public interface SampleMapper {
	
	@Select("select now()")
	String getTime();
}
