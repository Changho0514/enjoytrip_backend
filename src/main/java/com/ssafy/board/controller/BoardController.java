package com.ssafy.board.controller;

import java.nio.charset.Charset;
import java.util.List;

import com.ssafy.board.model.BoardListDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.board.model.BoardDto;
import com.ssafy.board.model.BoardParameterDto;
import com.ssafy.board.model.service.BoardService;
import com.ssafy.comment.model.CommentDto;
import com.ssafy.config.Result;
import com.ssafy.user.model.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/board")
@CrossOrigin("*")
@Api(tags = {"게시판  API"})
public class BoardController {
	
	private final Logger logger = LoggerFactory.getLogger(BoardController.class);
	
	private BoardService boardService;
	private UserService userService;

	public BoardController(BoardService boardService, UserService userService) {
		super();
		this.boardService = boardService;
		this.userService = userService;
	}
	
	@ApiOperation(value = "글 가져오기", notes = "게시판에서 번호에 대한 글 가져오기")
	@GetMapping("/detail/{articleno}")
	public ResponseEntity<?> getBoard(@PathVariable("articleno") int articleNo) {
		BoardDto boardDto;
		try {
			boardDto = boardService.getBoard(articleNo);
			if(boardDto != null) {
				boardService.increaseHit(articleNo);
				return new ResponseEntity<BoardDto>(boardDto, HttpStatus.OK);
			} else {
				return new ResponseEntity<Result>(new Result("fail", "해당 글이 없습니다"), HttpStatus.OK);
			}
		} catch (Exception e) {
			return new ResponseEntity<Result>(new Result("fail", "글 가져오기 실패"), HttpStatus.OK);
		}
	}
	
	@ApiOperation(value = "글 목록 가져오기", notes = "게시판에 있는 모든 글의 목록 가져오기")
	@PostMapping("/list")
	public ResponseEntity<?> list(@RequestBody BoardParameterDto boardParameterDto) {
		try {
			System.out.println("너 갈 준비했니?");
			BoardListDto boardListDto = boardService.list(boardParameterDto);
			HttpHeaders header = new HttpHeaders();
			header.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
			return ResponseEntity.ok().headers(header).body(boardListDto);
		} catch (Exception e) {
			return new ResponseEntity<Result>(new Result("fail", "글목록 가져오기 실패"), HttpStatus.OK);
		}
	}
	
	@ApiOperation(value = "글 작성", notes = "게시판에 글을 작성합니다")
	@ApiResponses({ @ApiResponse(code = 200, message = "회원목록 OK!!"), @ApiResponse(code = 404, message = "페이지없어!!"),
		@ApiResponse(code = 500, message = "서버에러!!") })
	@PostMapping("/write")
	public ResponseEntity<?> write(@RequestBody BoardDto boardDto) {
		try {
			if(userService.isAdmin(boardDto.getUserId()) == 1) {
				boardDto.setIsnotice(1);
			}
			boardService.write(boardDto);
			return new ResponseEntity<Result>(new Result("success", "글등록 성공"), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Result>(new Result("fail", "글등록 실패"), HttpStatus.OK);
		}
	}
	
	@ApiOperation(value = "글 수정", notes = "해당 번호의 글을 수정합니다")
	@PutMapping("/modify")
	public ResponseEntity<?> modify(@RequestBody BoardDto boardDto) {
		try {
			boardService.modify(boardDto);
			return new ResponseEntity<Result>(new Result("success", "글수정 성공"), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Result>(new Result("fail", "글수정  실패"), HttpStatus.OK);
		}
	}
	
	@ApiOperation(value = "글 삭제", notes = "해당 번호의 글을 삭제합니다")
	@DeleteMapping("/delete/{articleno}")
	public ResponseEntity<?> delete(@PathVariable("articleno") int articleNo) {
		try {
			boardService.delete(articleNo);
			return new ResponseEntity<Result>(new Result("success", "글삭제 성공"), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Result>(new Result("fail", "글삭제 실패"), HttpStatus.OK);
		}
	}
	
	@ApiOperation(value = "유저가 작성한 게시물 목록 가져오기")
    @GetMapping("/userlist/{userId}")
    public ResponseEntity<?> userlist(@PathVariable("userId") String userId) {
        try {
        	List<BoardDto> list = boardService.userlist(userId);
            HttpHeaders header = new HttpHeaders();
            header.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
            return ResponseEntity.ok().headers(header).body(list);
        } catch (Exception e) {
            return new ResponseEntity<Result>(new Result("fail", "유저가 작성한 게시물 목록 가져오기 실패"), HttpStatus.OK);
        }
    }
	
	@ApiOperation(value="게시글 작성자 가져오기")
	@GetMapping("/check/{articleNo}")
	public ResponseEntity<?> check(@PathVariable("articleNo") int articleNo) {
		String userId = "";
		try {
			userId = boardService.check(articleNo);
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
