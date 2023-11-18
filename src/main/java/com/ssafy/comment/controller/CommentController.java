package com.ssafy.comment.controller;

import com.ssafy.board.model.BoardDto;
import com.ssafy.board.model.BoardListDto;
import com.ssafy.board.model.BoardParameterDto;
import com.ssafy.comment.model.CommentDto;
import com.ssafy.comment.model.CommentListDto;
import com.ssafy.comment.model.CommentParameterDto;
import com.ssafy.comment.model.CommentUpdateDto;
import com.ssafy.comment.model.service.CommentService;
import com.ssafy.config.Result;
import com.ssafy.hotplace.controller.HotPlaceController;
import com.ssafy.hotplace.model.service.HotPlaceService;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.Charset;

@RestController
@RequestMapping("/comment")
@CrossOrigin("*")
@Api(tags = {"댓글 API"})
public class CommentController {
    private final Logger logger = LoggerFactory.getLogger(HotPlaceController.class);
    private static final String SUCCESS = "success";
    private static final String FAIL = "fail";

    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }


    //list
    @PostMapping("/list")
    public ResponseEntity<?> list(@RequestBody CommentParameterDto commentParameterDto) {
        try {
            CommentListDto commentListDto = commentService.list(commentParameterDto);
            logger.debug("commentList -> ", commentListDto);
            HttpHeaders header = new HttpHeaders();
            header.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
            return ResponseEntity.ok().headers(header).body(commentListDto);
        } catch (Exception e) {
            return new ResponseEntity<Result>(new Result("fail", "글목록 가져오기 실패"), HttpStatus.OK);
        }
    }


    //write
    @PostMapping("/write")
    public ResponseEntity<?> write(@RequestBody CommentDto commentDto) {
        try {
            commentService.write(commentDto);
            return new ResponseEntity<Result>(new Result("success", "글등록 성공"), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<Result>(new Result("fail", "글등록 실패"), HttpStatus.OK);
        }
    }

    //modify
    @PutMapping("/modify")
    public ResponseEntity<?> modify(@RequestBody CommentUpdateDto commentUpdateDto) {
        try {
            commentService.modify(commentUpdateDto);
            return new ResponseEntity<Result>(new Result("success", "글수정 성공"), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<Result>(new Result("fail", "글수정  실패"), HttpStatus.OK);
        }
    }
    //delete
    @DeleteMapping("/delete/{commentno}")
    public ResponseEntity<?> delete(@PathVariable("commentno") int commentNo) {
        try {
            commentService.delete(commentNo);
            return new ResponseEntity<Result>(new Result("success", "글삭제 성공"), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<Result>(new Result("fail", "글삭제 실패"), HttpStatus.OK);
        }
    }
}