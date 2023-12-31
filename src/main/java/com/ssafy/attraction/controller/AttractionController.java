package com.ssafy.attraction.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.attraction.model.AttractionDescriptionDto;
import com.ssafy.attraction.model.AttractionInfoDto;
import com.ssafy.attraction.model.GugunDto;
import com.ssafy.attraction.model.SidoDto;
import com.ssafy.attraction.model.service.AttractionService;
import com.ssafy.config.Result;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/attraction")
@CrossOrigin("*")
@Api(tags = {"관광지  API"})
@Slf4j
public class AttractionController {
	
	private static final Logger logger = LoggerFactory.getLogger(AttractionController.class);
	
	private AttractionService attractionService;

	public AttractionController(AttractionService attractionService) {
		super();
		this.attractionService = attractionService;
	}
	
	@ApiOperation(value = "관광지 목록", notes = "관광지 목록을 반환해줍니다")
	@ApiResponses({ @ApiResponse(code = 200, message = "OK!!"), @ApiResponse(code = 404, message = "페이지없어!!"),
		@ApiResponse(code = 500, message = "서버에러!!") })
	@PostMapping(value="/list")
	public ResponseEntity<?> attractionList(@RequestBody AttractionInfoDto attractionInfoDto) {
		try {
			List<AttractionInfoDto> list = attractionService.attractionList(attractionInfoDto);
			if (list != null && !list.isEmpty()) {
				return new ResponseEntity<List<AttractionInfoDto>>(list, HttpStatus.OK);
			} else {
				return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
			}
		} catch (Exception e) {
			return new ResponseEntity<Result>(new Result("fail", "NO LIST"), HttpStatus.OK);
		}
	}
	
	@ApiOperation(value = "구군 목록", notes = "시군에 따른 구군의 목록을 반환해줍니다")
	@ApiResponses({ @ApiResponse(code = 200, message = "OK!!"), @ApiResponse(code = 404, message = "페이지없어!!"),
		@ApiResponse(code = 500, message = "서버에러!!") })
	@GetMapping(value="/gugun/{sidoCode}")
	public ResponseEntity<?> gugunList(@PathVariable("sidoCode") int sidoCode) {
		try {
			List<GugunDto> list = attractionService.gugunList(sidoCode);
			if (list != null && !list.isEmpty()) {
				return new ResponseEntity<List<GugunDto>>(list, HttpStatus.OK);
			} else {
				return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
			}
		} catch (Exception e) {
			return new ResponseEntity<Result>(new Result("fail", "NO LIST"), HttpStatus.OK);
		}
	}
	
	@ApiOperation(value = "시군목록", notes = "시군의 목록을 반환해줍니다")
	@ApiResponses({ @ApiResponse(code = 200, message = "OK!!"), @ApiResponse(code = 404, message = "페이지없어!!"),
		@ApiResponse(code = 500, message = "서버에러!!") })
	@GetMapping(value="/sido")
	public ResponseEntity<?> sidoList() {
		try {
			List<SidoDto> list = attractionService.sidoList();
			if (list != null && !list.isEmpty()) {
				return new ResponseEntity<List<SidoDto>>(list, HttpStatus.OK);
			} else {
				return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
			}
		} catch (Exception e) {
			return new ResponseEntity<Result>(new Result("fail", "NO LIST"), HttpStatus.OK);
		}
	}
	
	@ApiOperation(value = "관광지 1개", notes = "contentId에 맞는 관광지 1개를 반환합니다")
	@ApiResponses({ @ApiResponse(code = 200, message = "OK!!"), @ApiResponse(code = 404, message = "페이지없어!!"),
		@ApiResponse(code = 500, message = "서버에러!!") })
	@GetMapping(value="/getAttraction/{contentId}")
	public ResponseEntity<?> getAttraction(@PathVariable("contentId") int contentId) {
		try {
			AttractionInfoDto attractionInfoDto = attractionService.getAttraction(contentId);
			if (attractionInfoDto != null) {
				return new ResponseEntity<AttractionInfoDto>(attractionInfoDto, HttpStatus.OK);
			} else {
				return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
			}
		} catch (Exception e) {
			return new ResponseEntity<Result>(new Result("fail", "해당 관광지가 없습니다"), HttpStatus.OK);
		}
	}
	
	@ApiOperation(value="관광지 상세정보 가져오기")
	@GetMapping(value="/getOverview/{contentId}")
	public ResponseEntity<?> getOverview(@PathVariable("contentId") int contentId) {
		AttractionDescriptionDto attractionDescriptionDto;
		try {
			attractionDescriptionDto = attractionService.getOverview(contentId);
			return new ResponseEntity<AttractionDescriptionDto>(attractionDescriptionDto, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Result>(new Result("fail", "상세정보가 없습니다"), HttpStatus.OK);
		}
	}
	
}
