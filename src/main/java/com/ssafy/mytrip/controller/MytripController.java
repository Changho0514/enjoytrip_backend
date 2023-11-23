 package com.ssafy.mytrip.controller;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.config.Result;
import com.ssafy.mytrip.model.MytripDto;
import com.ssafy.mytrip.model.MytripInfoDto;
import com.ssafy.mytrip.model.service.MytripService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/mytrip")
@CrossOrigin("*")
@Api(tags = {"나의 여행 계획  API"})
public class MytripController {
	
	private final Logger logger = LoggerFactory.getLogger(MytripController.class);
	
	private MytripService mytripService;

	public MytripController(MytripService mytripService) {
		super();
		this.mytripService = mytripService;
	}
	
	@ApiOperation(value = "유저가 등록한 해당 계획 가져오기")
	@GetMapping("/getMytrip/{userId}/{userMytripNo}")
	public ResponseEntity<?> getMytrip(@PathVariable("userId") String userId, @PathVariable("userMytripNo") int userMytripNo) {
		try {
			List<MytripDto> list = mytripService.getMytrip(new MytripDto(userMytripNo, 0, 0, userId));
			if (list != null && !list.isEmpty()) {
				return new ResponseEntity<List<MytripDto>>(list, HttpStatus.OK);
			} else {
				return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
			}
		} catch (Exception e) {
			return new ResponseEntity<Result>(new Result("fail", "NO LIST"), HttpStatus.OK);
		}
	}
	
	@ApiOperation(value = "유저가 등록한 여행계획 중 가장 큰 번호")
	@GetMapping("/getMytripMax/{userId}")
	public ResponseEntity<?> getMyTripMax(@PathVariable("userId") String userId) {
		int max;
		try {
			max = mytripService.getMytripMax(userId);
			return new ResponseEntity<Integer>(max, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Result>(new Result("fail", "등록한 계획이 없습니다"), HttpStatus.OK);
		}
		
	}
	
	@ApiOperation(value = "유저가 등록한 여행계획")
	@GetMapping("/getMytripAll/{userId}")
	public ResponseEntity<?> getMyTripAll(@PathVariable("userId") String userId) { 
		try {
			List<Integer> list = mytripService.getMytripAll(userId);
			if (list != null && !list.isEmpty()) {
				return new ResponseEntity<List<Integer>>(list, HttpStatus.OK);
			} else {
				return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
			}
		} catch (Exception e) {
			return new ResponseEntity<Result>(new Result("fail", "NO LIST"), HttpStatus.OK);
		}
	}
	
	@ApiOperation(value = "나의 여행계획 추가 (1개)")
	@PostMapping("/addMytrip")
	public ResponseEntity<?> addMyTrip(@RequestBody MytripDto mytripDto) {
		try {
			mytripService.addMytrip(mytripDto);
			return new ResponseEntity<Result>(new Result("success", "여행 1개 등록 성공"), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Result>(new Result("fail", "여행 1개 등록 실패"), HttpStatus.OK);
		}

	}

	@ApiOperation(value = "나의 여행계획 추가 (전체)")
	@PostMapping("/addMytripAll")
	public ResponseEntity<?> addMyTripAll(@RequestBody MytripDto[] list) {
		try {
			mytripService.addMytripAll(list);
			return new ResponseEntity<Result>(new Result("success", "여행 전체 등록 성공"), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Result>(new Result("fail", "여행 전체 등록 실패"), HttpStatus.OK);
		}

	}

	@ApiOperation(value = "유저가 여행계획 삭제(전체)")
	@DeleteMapping("/deleteMytripAll/{userId}/{userMytripNo}")
	public ResponseEntity<?> deleteMyTripAll(@PathVariable("userId") String userId, @PathVariable("userMytripNo") int userMytripNo) {
		try {
			mytripService.deleteMytripAll(new MytripDto(userMytripNo, 0, 0, userId));
			return new ResponseEntity<Result>(new Result("success", "여행 전체 삭제 성공"), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Result>(new Result("fail", "여행 전체 삭제 실패"), HttpStatus.OK);
		}

	}

	@ApiOperation(value = "유저가 여행계획 삭제(개별)")
	@DeleteMapping("/deleteMytrip/{no}")
	public ResponseEntity<?> deleteMyTrip(@PathVariable("no") int no) {
		try {
			mytripService.deleteMytrip(no);
			return new ResponseEntity<Result>(new Result("success", "여행 1개 삭제 성공"), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Result>(new Result("fail", "여행 1개 삭제 실패"), HttpStatus.OK);
		}
	}
	
	// mytripInfo
	@ApiOperation(value = "여행 정보 추가")
	@PostMapping("/addMytripInfo")
	public ResponseEntity<?> addMytripInfo(@RequestBody MytripInfoDto mytripInfoDto) {
		try {
			mytripService.addMytripInfo(mytripInfoDto);
			return new ResponseEntity<Result>(new Result("success", "여행 정보 추가 성공"), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Result>(new Result("fail", "여행 정보 추가 실패"), HttpStatus.OK);
		}
	}
	
	@ApiOperation(value = "여행 정보 가져오기")
	@GetMapping("/getMytripInfo/{userId}/{userMytripNo}")
	public ResponseEntity<?> getMytripInfo(@PathVariable("userId") String userId, @PathVariable("userMytripNo") int userMytripNo) {
		try {
			MytripInfoDto mytripInfoDto = mytripService.getMytripInfo(new MytripInfoDto(userMytripNo, userId, "", "", ""));
			if(mytripInfoDto != null) {
				return new ResponseEntity<MytripInfoDto>(mytripInfoDto, HttpStatus.OK);
			} else {
				return new ResponseEntity<Result>(new Result("success", "여행 정보가 없습니다"), HttpStatus.OK);
			}
		} catch (Exception e) {
			return new ResponseEntity<Result>(new Result("fail", "여행 정보 가져오기 실패"), HttpStatus.OK);
		}
	}
	
	@ApiOperation(value = "여행 정보 삭제")
	@DeleteMapping("/deleteMytripInfo/{userId}/{userMytripNo}")
	public ResponseEntity<?> deleteMytripInfo(@PathVariable("userId") String userId, @PathVariable("userMytripNo") int userMytripNo) {
		try {
			mytripService.deleteMytripInfo(new MytripInfoDto(userMytripNo, userId, "", "", ""));
			return new ResponseEntity<Result>(new Result("success", "여행 정보 삭제 성공"), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Result>(new Result("fail", "여행 정보 삭제 실패"), HttpStatus.OK);
		}
	}
}
