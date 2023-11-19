package com.ssafy.comment.model.service;

import com.ssafy.comment.model.*;
import com.ssafy.comment.model.mapper.CommentMapper;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CommentServiceImpl implements CommentService {

    private CommentMapper commentMapper;
    public CommentServiceImpl(CommentMapper commentMapper) {
        super();
        this.commentMapper = commentMapper;
    }
    @Override
    public void write(CommentDto commentDto) throws Exception {
        commentMapper.write(commentDto);
    }

    @Override
    public CommentListDto list(CommentParameterDto commentParameterDto) throws Exception {
        Map<String, Object> param = new HashMap<String, Object>();
        int currentPage = commentParameterDto.getPgno();
        int sizePerPage = commentParameterDto.getSpp();
        int start = currentPage * sizePerPage - sizePerPage;

        param.put("articleNo", commentParameterDto.getArticleNo());
        param.put("start", start);
        param.put("listsize", sizePerPage);

        List<CommentResultDto> list = commentMapper.list(param);

        int totalCommentCount = commentMapper.getTotalCommentCount(commentParameterDto.getArticleNo());
        int totalPageCount = (totalCommentCount - 1) / sizePerPage + 1;

        CommentListDto commentListDto = new CommentListDto();
        commentListDto.setComments(list);
        commentListDto.setCurrentPage(currentPage);
        commentListDto.setTotalPageCount(totalPageCount);
        return commentListDto;
    }

    @Override
    public void modify(CommentUpdateDto commentUpdateDto) throws Exception {
        commentMapper.modify(commentUpdateDto);
    }

    @Override
    public void delete(int commentNo) throws Exception {
        commentMapper.delete(commentNo);
    }

}
