package com.ssafy.board.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.board.model.BoardDto;
import com.ssafy.board.model.service.BoardService;
import com.ssafy.config.Result;
import com.ssafy.user.model.UserDto;

@RestController
@RequestMapping("/board")
@CrossOrigin("*")
public class BoardController {
	
	private final Logger logger = LoggerFactory.getLogger(BoardController.class);
	
	private BoardService boardService;

	public BoardController(BoardService boardService) {
		super();
		this.boardService = boardService;
	}
	
	@GetMapping("/detail/{articleno}")
	public ResponseEntity<?> getBoard(@PathVariable("articleno") int articleNo) {
		BoardDto boardDto;
		try {
			boardDto = boardService.getBoard(articleNo);
			if(boardDto == null) {
				return new ResponseEntity<Result>(new Result("fail", "해당 글이 없습니다"), HttpStatus.OK);
			} else {
				return new ResponseEntity<Result>(new Result("success", "글 가져오기  성공"), HttpStatus.OK);
			}
		} catch (Exception e) {
			return new ResponseEntity<Result>(new Result("fail", "글 가져오기 실패"), HttpStatus.OK);
		}
	}
	
	@GetMapping("/list")
	public ResponseEntity<?> list() {
		List<BoardDto> list;
		try {
			list = boardService.list();
			if(list != null && !list.isEmpty()) {
				return new ResponseEntity<List<BoardDto>>(list, HttpStatus.OK);
			} else {
				return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
			}
		} catch (Exception e) {
			return new ResponseEntity<Result>(new Result("fail", "글목록 가져오기 실패"), HttpStatus.OK);
		}
	}
	
	@PostMapping("/write")
	public ResponseEntity<?> write(BoardDto boardDto) {
		try {
			boardService.write(boardDto);
			return new ResponseEntity<Result>(new Result("success", "글등록 성공"), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Result>(new Result("fail", "글등록 실패"), HttpStatus.OK);
		}
	}
	
	@PutMapping("/modify")
	public ResponseEntity<?> modify(BoardDto boardDto) {
		try {
			boardService.modify(boardDto);
			return new ResponseEntity<Result>(new Result("success", "글수정 성공"), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Result>(new Result("fail", "글수정  실패"), HttpStatus.OK);
		}
	}
	
	@DeleteMapping("/delete/{articleno}")
	public ResponseEntity<?> delete(@PathVariable("articleno") int articleNo) {
		try {
			boardService.delete(articleNo);
			return new ResponseEntity<Result>(new Result("success", "글삭제 성공"), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Result>(new Result("fail", "글삭제 실패"), HttpStatus.OK);
		}
	}
	

}
