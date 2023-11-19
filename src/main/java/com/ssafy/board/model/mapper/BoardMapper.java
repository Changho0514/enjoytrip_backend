package com.ssafy.board.model.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.ssafy.board.model.BoardDto;
import com.ssafy.board.model.BoardParameterDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BoardMapper {
	
	BoardDto getBoard(int articleNo) throws SQLException;
	List<BoardDto> list(Map<String, Object> param) throws SQLException;
	int getTotalArticleCount(Map<String, Object> param) throws SQLException;
	void write(BoardDto boardDto) throws SQLException;
	void modify(BoardDto boardDto) throws SQLException;
	void delete(int articleNo) throws SQLException;

	void increaseHit(int articleNo) throws SQLException;
	void decreaseComment(int articleNo) throws SQLException;

	void increaseComment(int articleNo) throws SQLException;
}
