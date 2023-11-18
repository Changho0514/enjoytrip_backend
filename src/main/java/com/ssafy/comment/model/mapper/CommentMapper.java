package com.ssafy.comment.model.mapper;

import com.ssafy.board.model.BoardDto;
import com.ssafy.comment.model.CommentDto;
import com.ssafy.comment.model.CommentListDto;
import com.ssafy.comment.model.CommentResultDto;
import com.ssafy.comment.model.CommentUpdateDto;
import org.apache.ibatis.annotations.Mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
@Mapper
public interface CommentMapper {
    void write(CommentDto commentDto) throws SQLException;
    List<CommentResultDto> list(Map<String, Object> param) throws SQLException;
    void modify(CommentUpdateDto commentUpdateDto) throws SQLException;
    void delete(int commentNo) throws SQLException;

    int getTotalCommentCount(int articleNo) throws SQLException;
}
