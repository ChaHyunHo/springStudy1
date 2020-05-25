package com.camel.board.mapper;

import org.apache.ibatis.annotations.Param;

import com.camel.board.mapper.vo.BoardVO;

public interface BoardMapper {
	
	public BoardVO boardDetail(@Param("no") String no) throws Exception; 

}
