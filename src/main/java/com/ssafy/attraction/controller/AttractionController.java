package com.ssafy.attraction.controller;

import java.util.List;
import java.util.Map;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.attraction.model.AttractionInfoDto;
import com.ssafy.attraction.model.GugunDto;
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
@Api(tags = {"AttractionController  API "})
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
	
	@ApiOperation(value = "시군구군 목록", notes = "시군에 따른 구군의 목록을 반환해줍니다")
	@ApiResponses({ @ApiResponse(code = 200, message = "OK!!"), @ApiResponse(code = 404, message = "페이지없어!!"),
		@ApiResponse(code = 500, message = "서버에러!!") })
	@GetMapping(value="/sido/{sidoCode}")
	public ResponseEntity<?> sidoList(@PathVariable("sidoCode") int sidoCode) {
		try {
			List<GugunDto> list = attractionService.sidoList(sidoCode);
			if (list != null && !list.isEmpty()) {
				return new ResponseEntity<List<GugunDto>>(list, HttpStatus.OK);
			} else {
				return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
			}
		} catch (Exception e) {
			return new ResponseEntity<Result>(new Result("fail", "NO LIST"), HttpStatus.OK);
		}
	}

}
