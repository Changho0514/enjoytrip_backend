package com.ssafy.comment.controller;

import com.ssafy.board.model.service.BoardService;
import com.ssafy.comment.model.CommentDto;
import com.ssafy.comment.model.service.CommentService;
import com.ssafy.config.Result;
import com.ssafy.hotplace.controller.HotPlaceController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.Charset;
import java.util.List;

@RestController
@RequestMapping("/comment")
@CrossOrigin("*")
@Api(tags = {"댓글 API"})
public class CommentController {
	
    private final Logger logger = LoggerFactory.getLogger(HotPlaceController.class);

    private CommentService commentService;
    private BoardService boardService;
    public CommentController(CommentService commentService, BoardService boardService) {
        this.commentService = commentService;
        this.boardService = boardService;
    }

    @ApiOperation(value = "댓글 목록 가져오기")
    @GetMapping("/list/{articleNo}")
    public ResponseEntity<?> list(@PathVariable("articleNo") int articleNo) {
        try {
        	List<CommentDto> list = commentService.list(articleNo);
            HttpHeaders header = new HttpHeaders();
            header.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
            return ResponseEntity.ok().headers(header).body(list);
        } catch (Exception e) {
            return new ResponseEntity<Result>(new Result("fail", "댓글 목록 가져오기 실패"), HttpStatus.OK);
        }
    }

    @ApiOperation(value = "댓글 등록하기")
    @PostMapping("/write")
    public ResponseEntity<?> write(@RequestBody CommentDto commentDto) {
        try {
            commentService.write(commentDto);
            boardService.increaseComment(commentDto.getArticleNo());
            return new ResponseEntity<Result>(new Result("success", "댓글 등록 성공"), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<Result>(new Result("fail", "댓글 등록 실패"), HttpStatus.OK);
        }
    }

    @ApiOperation(value = "댓글 수정하기")
    @PutMapping("/modify")
    public ResponseEntity<?> modify(@RequestBody CommentDto commentDto) {
        try {
            commentService.modify(commentDto);
            return new ResponseEntity<Result>(new Result("success", "댓글 수정 성공"), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<Result>(new Result("fail", "댓글 수정  실패"), HttpStatus.OK);
        }
    }
    
    @ApiOperation(value = "댓글 삭제하기")
    @DeleteMapping("/delete/{commentno}")
    public ResponseEntity<?> delete(@PathVariable("commentno") int commentNo) {
        try {
            int articleNo = commentService.getArticleNo(commentNo);
            boardService.decreaseComment(articleNo);
            commentService.delete(commentNo);
            return new ResponseEntity<Result>(new Result("success", "댓글 삭제 성공"), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<Result>(new Result("fail", "댓글 삭제 실패"), HttpStatus.OK);
        }
    }
    
    @ApiOperation(value = "유저가 작성한 댓글 목록 가져오기")
    @GetMapping("/userlist/{userId}")
    public ResponseEntity<?> userlist(@PathVariable("userId") String userId) {
        try {
        	List<CommentDto> list = commentService.userlist(userId);
            HttpHeaders header = new HttpHeaders();
            header.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
            return ResponseEntity.ok().headers(header).body(list);
        } catch (Exception e) {
            return new ResponseEntity<Result>(new Result("fail", "유저가 작성한 댓글 목록 가져오기 실패"), HttpStatus.OK);
        }
    }
    
    @ApiOperation(value="게시글 작성자 가져오기")
	@GetMapping("/check/{commentNo}")
	public ResponseEntity<?> check(@PathVariable("commentNo") int commentNo) {
		String userId = "";
		try {
			userId = commentService.check(commentNo);
			if(userId.equals("")) {
				return new ResponseEntity<Result>(new Result("fail", "게시글 작성자가 없습니다"), HttpStatus.OK);
			} else {
				return new ResponseEntity<String>(userId, HttpStatus.OK);
			}
		} catch (Exception e) {
			return new ResponseEntity<Result>(new Result("fail", "게시글 작성자 가져오기 실패"), HttpStatus.OK);
		}
	}
    
}
