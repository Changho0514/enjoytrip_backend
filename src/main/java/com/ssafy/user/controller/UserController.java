package com.ssafy.user.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.config.Result;
import com.ssafy.user.model.UserDto;
import com.ssafy.user.model.service.UserService;
import com.ssafy.user.util.JWTUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/user")
@CrossOrigin("*")
@Api(tags = {"일반 회원 API"})
public class UserController {
	
	private final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	private UserService userService;
	private JWTUtil jwtUtil;
	private ServletContext servletContext;
	
	public UserController(UserService userService, JWTUtil jwtUtil, ServletContext servletContext) {
		super();
		this.userService = userService;
		this.jwtUtil = jwtUtil;
		this.servletContext = servletContext;
	}
	
	@ApiOperation(value = "로그인", notes = "아이디와 비밀번호를 이용하여 로그인 처리.")
	@PostMapping("/login")
	public ResponseEntity<Map<String, Object>> login(
			@RequestBody @ApiParam(value = "로그인 시 필요한 회원정보(아이디, 비밀번호).", required = true) UserDto userDto) {
		logger.debug("login user : {}", userDto);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		HttpStatus status = HttpStatus.ACCEPTED;
		try {
			UserDto loginUser = userService.login(userDto);
			if(loginUser != null) {
				String accessToken = jwtUtil.createAccessToken(loginUser.getUserId());
				String refreshToken = jwtUtil.createRefreshToken(loginUser.getUserId());
				logger.debug("access token : {}", accessToken);
				logger.debug("refresh token : {}", refreshToken);
				
//				발급받은 refresh token을 DB에 저장.
				userService.saveRefreshToken(loginUser.getUserId(), refreshToken);
				
//				JSON으로 token 전달.
				resultMap.put("access-token", accessToken);
				// Cookie로 변경
				resultMap.put("refresh-token", refreshToken);
				
				status = HttpStatus.CREATED;
			} else {
				resultMap.put("message", "아이디 또는 패스워드를 확인해주세요.");
				status = HttpStatus.UNAUTHORIZED;
			} 
			
		} catch (Exception e) {
			logger.debug("로그인 에러 발생 : {}", e);
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	
	@ApiOperation(value = "회원인증", notes = "회원 정보를 담은 Token을 반환한다.", response = Map.class)
	@GetMapping("/info/{userId}")
	public ResponseEntity<Map<String, Object>> getInfo(
			@PathVariable("userId") @ApiParam(value = "인증할 회원의 아이디.", required = true) String userId,
			HttpServletRequest request) {
//		logger.debug("userId : {} ", userId);
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.ACCEPTED;
		if (jwtUtil.checkToken(request.getHeader("Authorization"))) {
			logger.info("사용 가능한 토큰!!!");
			try {
//				로그인 사용자 정보.
				UserDto userDto = userService.userInfo(userId);
				resultMap.put("userInfo", userDto);
				status = HttpStatus.OK;
			} catch (Exception e) {
				logger.error("정보조회 실패 : {}", e);
				resultMap.put("message", e.getMessage());
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		} else {
			logger.error("사용 불가능 토큰!!!");
			status = HttpStatus.UNAUTHORIZED;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	
	@ApiOperation(value = "로그아웃", notes = "회원 정보를 담은 Token을 제거한다.", response = Map.class)
	@GetMapping("/logout/{userId}")
	public ResponseEntity<?> removeToken(@PathVariable ("userId") @ApiParam(value = "로그아웃할 회원의 아이디.", required = true) String userId) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.ACCEPTED;
		try {
			userService.deleteRefreshToken(userId);
			status = HttpStatus.OK;
		} catch (Exception e) {
			logger.error("로그아웃 실패 : {}", e);
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);

	}
	
	@ApiOperation(value = "Access Token 재발급", notes = "만료된 access token을 재발급받는다.", response = Map.class)
	@PostMapping("/refresh")
	public ResponseEntity<?> refreshToken(@RequestBody UserDto userDto, HttpServletRequest request)
			throws Exception {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.ACCEPTED;
		String token = request.getHeader("refreshToken");
		logger.debug("token : {}, memberDto : {}", token, userDto);
		if (jwtUtil.checkToken(token)) {
			if (token.equals(userService.getRefreshToken(userDto.getUserId()))) {
				String accessToken = jwtUtil.createAccessToken(userDto.getUserId());
				logger.debug("token : {}", accessToken);
				logger.debug("정상적으로 액세스토큰 재발급!!!");
				resultMap.put("access-token", accessToken);
				status = HttpStatus.CREATED;
			}
		} else {
			logger.debug("리프레쉬토큰도 사용불가!!!!!!!");
			status = HttpStatus.UNAUTHORIZED;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	
	/////////////////////////////JWT 토큰/////////////////////////////////
	
	@ApiOperation(value = "아이디 체크", notes = "아이디 중복 확인.")
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
	@PostMapping(value = "/regist")
	public ResponseEntity<?> regist(@RequestBody UserDto userDto) {
		try {
			logger.error("회원가입 사용자 : {}", userDto);
			userService.regist(userDto);
			return new ResponseEntity<Result>(new Result("success", "회원가입 성공"), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Result>(new Result("fail", "회원가입 실패"), HttpStatus.OK);
		}
	}
	
	@ApiOperation(value = "회원정보수정", notes = "회원정보를 수정합니다.")
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
	
	@ApiOperation(value = "비밀번호 변경")
	@PutMapping(value = "/change")
	public ResponseEntity<?> changePwd(@RequestBody UserDto userDto) {
		try {
			userService.changePwd(userDto);
			return new ResponseEntity<Result>(new Result("success", "비밀번호 변경 성공"), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Result>(new Result("fail", "비밀번호 변경 실패"), HttpStatus.OK);
		}
	}
	
	@ApiOperation(value = "비밀번호 찾기")
	@GetMapping("/find/{userid}")
	public ResponseEntity<?> findPwd(@PathVariable("userid") String userId) throws Exception {
		try{
			String password = userService.findPwd(userId);
			return new ResponseEntity<String>(password, HttpStatus.OK);
		} catch(Exception e) {
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}
	}
	
	@ApiOperation(value = "프로필 사진 바꾸기")
    @PostMapping("/profile")
    public ResponseEntity<?> writeFile(@RequestParam("userId") String userId,
                                       @RequestParam("upfile") MultipartFile file) {
        logger.debug("upload file : {}", file);
        try {//        FileUpload 관련 설정.
            logger.debug("MultipartFile.isEmpty : {}", file.isEmpty());
            if (!file.isEmpty()) {
                String realPath = servletContext.getRealPath("/profile");
//                String today = new SimpleDateFormat("yyMMdd").format(new Date());
                String saveFolder = realPath + File.separator + userId;
                logger.debug("저장 폴더-------------------------- : {}", saveFolder);
                File folder = new File(saveFolder);
                if (!folder.exists())
                    folder.mkdirs();
                String originalFileName = file.getOriginalFilename();
                if (!originalFileName.isEmpty()) {
                    String saveFileName = UUID.randomUUID().toString() + originalFileName.substring(originalFileName.lastIndexOf('.'));
                    
                    Map<String, Object> params = new HashMap<>();
                    params.put("userId", userId);
                    params.put("saveFolder", userId);
                    params.put("saveFile", saveFileName);
                    params.put("originalFile", originalFileName);
                    logger.debug("원본 파일 이름 : {}, 실제 저장 파일 이름 : {}", file.getOriginalFilename(), saveFileName);
                    file.transferTo(new File(folder, saveFileName));
                    userService.writeFile(params);
                }
                return new ResponseEntity<Result>(new Result("success", "파일만 프로필 write 성공"), HttpStatus.OK);
            } else {
            	return new ResponseEntity<Result>(new Result("fail", "파일이 없습니다"), HttpStatus.OK);
            }
        } catch (Exception e) {
        	return new ResponseEntity<Result>(new Result("fail", "파일만 프로필 write 실패"), HttpStatus.OK);
        }
    }
	
	
}
