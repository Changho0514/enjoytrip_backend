package com.ssafy.member.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.config.Result;
import com.ssafy.member.model.MemberDto;
import com.ssafy.member.model.service.MemberService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/user")
@CrossOrigin("*")
@Api(tags = {"일반 회원"})
public class MemberController {
	
	private final Logger logger = LoggerFactory.getLogger(MemberController.class);
	
	private MemberService memberService;
	
	public MemberController(MemberService memberService) {
		super();
		this.memberService = memberService;
	}
	
	@ApiOperation(value = "아이디 체크", notes = "아이디 중복 확인.")
	@GetMapping("/{userid}")
	public ResponseEntity<?> idCheck(@PathVariable("userid") String userId) throws Exception {
		logger.debug("idCheck userid : {}", userId);
		try{
			int cnt = memberService.idCheck(userId);
			return new ResponseEntity<Integer>(cnt, HttpStatus.OK);
		} catch(Exception e) {
			return exceptionHandling(e);
		}
	}
	
	@ApiOperation(value = "회원등록", notes = "회원의 정보를 받아 처리.")
	@PostMapping(value = "/join")
	public ResponseEntity<?> userRegister(@RequestBody MemberDto memberDto) {
		logger.debug("userRegister memberDto : {}", memberDto);
		try {
			memberService.joinMember(memberDto);
			List<MemberDto> list = memberService.listMember(null);
			if(list != null && !list.isEmpty()) {
				return new ResponseEntity<Result>(new Result("success", "Enrolled"), HttpStatus.CREATED);
			} else {
				return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
			}
		} catch (Exception e) {
			return new ResponseEntity<Result>(new Result("fail", "NO LIST"), HttpStatus.OK);
		}
	}
	// TODO : session 사용 가능하면 로그인 바꾸기 
//	@ApiOperation(value = "로그인", notes = "로그인 정보를 받아 처리.")
//	@PostMapping("/login")
//	public ResponseEntity<?> login(@RequestParam Map<String, String> map, @RequestParam(name = "saveid", required = false) String saveid, Model model, HttpSession session, HttpServletResponse response) {
//		System.out.println("login gogogogo");
//		logger.debug("login map : {}", map);
//		try {
//			MemberDto memberDto = memberService.loginMember(map);
//			if(memberDto != null) {
//				session.setAttribute("userinfo", memberDto);
//				
//				Cookie cookie = new Cookie("ssafy_id", map.get("userid"));
//				cookie.setPath("/board");
//				if("ok".equals(saveid)) {
//					cookie.setMaxAge(60*60*24*365*40);
//				} else {
//					cookie.setMaxAge(0);
//				}
//				response.addCookie(cookie);
//				return new ResponseEntity<Result>(new Result("success", "login success"), HttpStatus.OK);
//			} else {
//				String msg = "아이디 또는 비밀번호 확인 후 다시 로그인하세요!";
//				return new ResponseEntity<Result>(new Result("fail", msg), HttpStatus.NOT_FOUND);
//			}
//		} catch (Exception e) {
//			return exceptionHandling(e);
//		}
//	}
	@ApiOperation(value = "로그인", notes = "로그인 정보를 받아 처리.")
	@PostMapping("/login")
	public ResponseEntity<?> login(Map<String, String> map) {
		
		try {
			MemberDto memberDto = memberService.loginMember(map);
			
			if(memberDto != null) {
				return new ResponseEntity<Result>(new Result("success", "login success"), HttpStatus.OK);
			} else {
				String msg = "아이디 또는 비밀번호 확인 후 다시 로그인하세요!";
				return new ResponseEntity<Result>(new Result("fail", msg), HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	@ApiOperation(value = "로그아웃", notes = "로그아웃 요청을 받아 처리.")
	@GetMapping("/logout")
	public ResponseEntity<?> logout(HttpSession session) {
		try{
			session.invalidate();
			return new ResponseEntity<Result>(new Result("success", "logout success"), HttpStatus.OK);
		}
		catch(Exception e) {
			return exceptionHandling(e);
		}
	}
	
	
	@ApiOperation(value = "회원탈퇴", notes = "회원 탈퇴를 하여 회원 정보를 삭제합니다.")
	@ApiResponses({ @ApiResponse(code = 200, message = "회원목록 OK!!"), @ApiResponse(code = 404, message = "페이지없어!!"),
			@ApiResponse(code = 500, message = "서버에러!!") })
	@DeleteMapping(value = "/{userid}")
	public ResponseEntity<?> userDelete(@PathVariable("userid") String userId) {
		logger.debug("userDelete userid : {}", userId);
		try {
			MemberDto memberDto = memberService.getMember(userId);
			if(memberDto != null) {
				memberService.deleteMember(userId);
				return new ResponseEntity<Result>(new Result("success", "Deleted"), HttpStatus.OK);
			} else {
				return new ResponseEntity<Result>(new Result("fail", "존재하지 않는 사용자입니다."), HttpStatus.OK);
			}
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
		
	
	private ResponseEntity<String> exceptionHandling(Exception e) {
		e.printStackTrace();
		return new ResponseEntity<String>("Error : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
}
