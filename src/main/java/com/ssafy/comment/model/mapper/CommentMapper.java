package com.ssafy.comment.model.mapper;

import com.ssafy.comment.model.CommentDto;
import org.apache.ibatis.annotations.Mapper;

import java.sql.SQLException;
import java.util.List;

@Mapper
public interface CommentMapper {
    void write(CommentDto commentDto) throws SQLException;
    List<CommentDto> list(int articleNo) throws SQLException;
    void modify(CommentDto commentDto) throws SQLException;
    void delete(int commentNo) throws SQLException;
    String check(int commentNo) throws SQLException;

    int getTotalCommentCount(int articleNo) throws SQLException;

    int getArticleNo(int commentNo) throws SQLException;
    
    List<CommentDto> userlist(String userId) throws SQLException;
}
