package com.ssafy.admin.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

import com.ssafy.config.Result;
import com.ssafy.member.model.MemberDto;
import com.ssafy.member.model.service.MemberService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/admin")
@CrossOrigin("*")
@Api(tags = {"회원 관리"})
public class AdminUserController {

	private static final Logger logger = LoggerFactory.getLogger(AdminUserController.class);

	private MemberService memberService;
	
	@Autowired
	public AdminUserController(MemberService memberService) {
		this.memberService = memberService;
	}

	@ApiOperation(value = "회원목록", notes = "회원의 <big>전체 목록</big>을 반환해 줍니다.")
	@ApiResponses({ @ApiResponse(code = 200, message = "회원목록 OK!!"), @ApiResponse(code = 404, message = "페이지없어!!"),
			@ApiResponse(code = 500, message = "서버에러!!") })
	@GetMapping(value = "/list")
	public ResponseEntity<?> list() {
		try {
			List<MemberDto> list = memberService.listMember(null);
			if(list != null && !list.isEmpty()) {
				return new ResponseEntity<List<MemberDto>>(list, HttpStatus.OK);
			} else {
				return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
			}
		} catch (Exception e) {
			return new ResponseEntity<Result>(new Result("fail", "회원목록 불러오기 실패"), HttpStatus.OK);
		}
		
	}
	
	@ApiOperation(value = "회원등록", notes = "회원의 정보를 받아 처리.")
	@ApiResponses({ @ApiResponse(code = 200, message = "회원목록 OK!!"), @ApiResponse(code = 404, message = "페이지없어!!"),
		@ApiResponse(code = 500, message = "서버에러!!") })
	@PostMapping(value = "/regist")
	public ResponseEntity<?> regist(@RequestBody MemberDto memberDto) {
		try {
			memberService.registMember(memberDto);
			return new ResponseEntity<Result>(new Result("success", "회원등록 성공"), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Result>(new Result("fail", "회원등록 실패"), HttpStatus.OK);
		}
		
	}
	
	@ApiOperation(value = "회원정보", notes = "회원한명에 대한 정보.")
	@ApiResponses({ @ApiResponse(code = 200, message = "회원목록 OK!!"), @ApiResponse(code = 404, message = "페이지없어!!"),
		@ApiResponse(code = 500, message = "서버에러!!") })
	@GetMapping(value = "/list/{userid}")
	public ResponseEntity<?> getMember(@PathVariable("userid") String userId) {
		try {
			MemberDto memberDto = memberService.getMember(userId);
			if(memberDto != null)
				return new ResponseEntity<MemberDto>(memberDto, HttpStatus.OK);
			else
				return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<Result>(new Result("fail", "회원정보 가져오기 실패"), HttpStatus.OK);
		}
	}
	
	// TODO: 로그인한 사용자(같은 세션일 때만) 삭제처리 
	@ApiOperation(value = "회원정보삭제", notes = "회원정보를 삭제합니다.")
	@ApiResponses({ @ApiResponse(code = 200, message = "회원목록 OK!!"), @ApiResponse(code = 404, message = "페이지없어!!"),
		@ApiResponse(code = 500, message = "서버에러!!") })
	@DeleteMapping(value = "/delete/{userid}")
	public ResponseEntity<?> delete(@PathVariable("userid") String userId) {
		try {
			MemberDto memberDto = memberService.getMember(userId);
			if(memberDto != null) {
				memberService.deleteMember(userId);
				return new ResponseEntity<Result>(new Result("success", "회원삭제 성공"), HttpStatus.OK);
			} else {
				return new ResponseEntity<Result>(new Result("fail", "존재하지 않는 사용자입니다."), HttpStatus.OK);
			}
		} catch (Exception e) {
			return new ResponseEntity<Result>(new Result("fail", "회원삭제 실패"), HttpStatus.OK);
		}
	}

}
