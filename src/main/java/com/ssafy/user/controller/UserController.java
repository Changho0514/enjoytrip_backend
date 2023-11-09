package com.ssafy.user.controller;

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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.attraction.model.AttractionInfoDto;
import com.ssafy.config.Result;
import com.ssafy.user.model.UserDto;
import com.ssafy.user.model.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/member")
@CrossOrigin("*")
@Api(tags = {"일반 회원"})
public class UserController {
	
	private final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	private UserService userService;
	
	public UserController(UserService userService) {
		super();
		this.userService = userService;
	}
	
	@ApiOperation(value = "아이디 체크", notes = "아이디 중복 확인.")
	@ApiResponses({ @ApiResponse(code = 200, message = "회원목록 OK!!"), @ApiResponse(code = 404, message = "페이지없어!!"),
		@ApiResponse(code = 500, message = "서버에러!!") })
	@GetMapping("/idcheck/{userid}")
	public ResponseEntity<?> idCheck(@PathVariable("userid") String userId) throws Exception {
		try{
			int cnt = userService.idCheck(userId);
			return new ResponseEntity<Integer>(cnt, HttpStatus.OK);
		} catch(Exception e) {
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}
	}
	
	@ApiOperation(value = "회원가입", notes = "회원의 정보를 받아 처리.")
	@ApiResponses({ @ApiResponse(code = 200, message = "회원목록 OK!!"), @ApiResponse(code = 404, message = "페이지없어!!"),
		@ApiResponse(code = 500, message = "서버에러!!") })
	@PostMapping(value = "/regist")
	public ResponseEntity<?> regist(@RequestBody UserDto userDto) {
		try {
			userService.regist(userDto);
			return new ResponseEntity<Result>(new Result("success", "회원가입 성공"), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Result>(new Result("fail", "회원가입 실패"), HttpStatus.OK);
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
	@ApiResponses({ @ApiResponse(code = 200, message = "회원목록 OK!!"), @ApiResponse(code = 404, message = "페이지없어!!"),
		@ApiResponse(code = 500, message = "서버에러!!") })
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody UserDto userDto) {
		try {
			UserDto login = userService.login(userDto);
			if(userDto != null) {
				return new ResponseEntity<Result>(new Result("success", "로그인 성공"), HttpStatus.OK);
			} else {
				return new ResponseEntity<Result>(new Result("fail", "아이디나 비밀번호가 다릅니다"), HttpStatus.OK);
			}
		} catch (Exception e) {
			return new ResponseEntity<Result>(new Result("fail", "로그인 실패"), HttpStatus.OK);
		}
	}
	
	@ApiOperation(value = "로그아웃", notes = "로그아웃 요청을 받아 처리.")
	@ApiResponses({ @ApiResponse(code = 200, message = "회원목록 OK!!"), @ApiResponse(code = 404, message = "페이지없어!!"),
		@ApiResponse(code = 500, message = "서버에러!!") })
	@GetMapping("/logout")
	public ResponseEntity<?> logout(HttpSession session) {
		try{
			session.invalidate();
			return new ResponseEntity<Result>(new Result("success", "로그아웃 성공"), HttpStatus.OK);
		}
		catch(Exception e) {
			return new ResponseEntity<Result>(new Result("fail", "로그아웃 실패"), HttpStatus.OK);
		}
	}
	
	@ApiOperation(value = "회원정보수정", notes = "회원정보를 수정합니다.")
	@ApiResponses({ @ApiResponse(code = 200, message = "회원목록 OK!!"), @ApiResponse(code = 404, message = "페이지없어!!"),
		@ApiResponse(code = 500, message = "서버에러!!") })
	@PutMapping(value = "/modify")
	public ResponseEntity<?> modify(@RequestBody UserDto userDto) {
		try {
			userService.modify(userDto);
			return new ResponseEntity<Result>(new Result("success", "회원정보수정 성공"), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Result>(new Result("fail", "회원정보수정 실패"), HttpStatus.OK);
		}
	}
	
	@ApiOperation(value = "회원탈퇴", notes = "회원 탈퇴를 하여 회원 정보를 삭제합니다.")
	@ApiResponses({ @ApiResponse(code = 200, message = "회원목록 OK!!"), @ApiResponse(code = 404, message = "페이지없어!!"),
			@ApiResponse(code = 500, message = "서버에러!!") })
	@DeleteMapping(value = "/delete/{userid}")
	public ResponseEntity<?> delete(@PathVariable("userid") String userId) {
		try {
			UserDto userDto = userService.getUser(userId);
			if(userDto != null) {
				userService.delete(userId);
				return new ResponseEntity<Result>(new Result("success", "회원탈퇴 성공"), HttpStatus.OK);
			} else {
				return new ResponseEntity<Result>(new Result("fail", "존재하지 않는 사용자입니다."), HttpStatus.OK);
			}
		} catch (Exception e) {
			return new ResponseEntity<Result>(new Result("fail", "회원탈퇴 실패"), HttpStatus.OK);
		}
	}
	
}