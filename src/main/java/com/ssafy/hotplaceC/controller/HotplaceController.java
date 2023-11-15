//package com.ssafy.hotplace.controller;
//
//import java.io.File;
//import java.util.List;
//import java.util.UUID;
//
//import com.ssafy.hotplace.model.HotplaceParamterDto;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.multipart.MultipartFile;
//
//import com.ssafy.config.Result;
//import com.ssafy.hotplace.model.HotplaceDto;
//import com.ssafy.hotplace.model.service.HotplaceService;
//
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//
//@RestController
//@RequestMapping("/hotplace")
//@CrossOrigin("*")
//@Api(tags = {"핫플레이스"})
//public class HotplaceController {
//
//	private final Logger logger = LoggerFactory.getLogger(HotplaceController.class);
//	private HotplaceService hotplaceService;
//
//	public HotplaceController(HotplaceService hotplaceService) {
//		super();
//		this.hotplaceService = hotplaceService;
//	}
//
//	@ApiOperation(value = "핫플 모두 가져오기", notes = "핫플 글 모두 가져오기")
//	@GetMapping("/list")
//	public ResponseEntity<?> list(HotplaceParamterDto hotplaceParamterDto) {
//		List<HotplaceDto> list;
//		try {
//			list = hotplaceService.list(hotplaceParamterDto);
//			if(list != null && !list.isEmpty()) {
//				return new ResponseEntity<List<HotplaceDto>> (list, HttpStatus.OK);
//			} else {
//				return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
//			}
//		} catch (Exception e) {
//			return new ResponseEntity<Result>(new Result("fail", "핫플목록 가져오기 실패"), HttpStatus.OK);
//		}
//	}
//
//	@ApiOperation(value = "핫플 top3 가져오기", notes = "핫플 top3 가져오기")
//	@GetMapping("/top3")
//	public ResponseEntity<?> top3() {
//		List<HotplaceDto> list;
//		try {
//			list = hotplaceService.hotplaceTOP3();
//			if(list != null && !list.isEmpty()) {
//				return new ResponseEntity<List<HotplaceDto>> (list, HttpStatus.OK);
//			} else {
//				return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
//			}
//		} catch (Exception e) {
//			return new ResponseEntity<Result>(new Result("fail", "핫플 top3 가져오기 실패"), HttpStatus.OK);
//		}
//	}
//
////	@ApiOperation(value = "핫플 쓰기", notes = "핫플 글 쓰기")
////	@PostMapping("/write")
////	public ResponseEntity<?> write(@RequestBody HotplaceDto hotplaceDto, @RequestParam("upfile") MultipartFile file) {
////		try {
////			if(!file.isEmpty()) {
////				String hot = "hotplace";
////				String saveFolder = file.getPath+hot;
////				logger.debug("저장 폴더 : {}", saveFolder);
////				File folder = new File(saveFolder);
////				if(!folder.exists()) {
////					folder.mkdir();
////				}
////				String originalFileName = file.getOriginalFilename();
////				if(!originalFileName.isEmpty()) {
////					String saveFileName = UUID.randomUUID().toString()+originalFileName.substring(originalFileName.lastIndexOf('.'));
////					logger.debug("원본 파일 이름 : {}, 실제 저장 파일 이름 : {}", file.getOriginalFilename(), saveFileName);
////					file.transferTo(new File(folder, saveFileName));
////					hotplaceDto.setAddress(hot+"/"+saveFileName);
////				}
////			}
////			hotplaceService.write(hotplaceDto);
////			return new ResponseEntity<Result>(new Result("success", "핫플 쓰기 성공"), HttpStatus.OK);
////		} catch (Exception e) {
////			return new ResponseEntity<Result>(new Result("fail", "핫플 쓰기 실패"), HttpStatus.OK);
////		}
////	}
//
//	@ApiOperation(value = "핫플 가져오기", notes = "번호에 대한 핫플 가져오기")
//	@GetMapping("/detail/{hotplaceno}")
//	public ResponseEntity<?> getHotplace(@PathVariable("hotplaceno") int hotplaceNo) {
//		HotplaceDto hotplaceDto;
//		try {
//			hotplaceDto = hotplaceService.getHotplace(hotplaceNo);
//			if(hotplaceDto != null) {
//				return new ResponseEntity<HotplaceDto>(hotplaceDto, HttpStatus.OK);
//			} else {
//				return new ResponseEntity<Result>(new Result("fail", "해당하는 글이 없습니다"), HttpStatus.OK);
//			}
//		} catch (Exception e) {
//			return new ResponseEntity<Result>(new Result("fail", "핫플 가져오기 실패"), HttpStatus.OK);
//		}
//	}
//
//	@ApiOperation(value = "핫플 수정", notes = "핫플 수정하기")
//	@PutMapping("/modify")
//	public ResponseEntity<?> modify(@RequestBody HotplaceDto hotplaceDto) {
//		try {
//			hotplaceService.modify(hotplaceDto);
//			return new ResponseEntity<Result>(new Result("success", "핫플 수정 성공"), HttpStatus.OK);
//		} catch (Exception e) {
//			return new ResponseEntity<Result>(new Result("fail", "핫플 수정 실패"), HttpStatus.OK);
//		}
//
//	}
//
//	@ApiOperation(value = "핫플 삭제", notes = "번호에 대한 핫플 삭제하기")
//	@DeleteMapping("/delete/{hotplaceno}")
//	public ResponseEntity<?> delete(@PathVariable("hotplaceno") int hotplaceNo) {
//		try {
//			hotplaceService.delete(hotplaceNo);
//			return new ResponseEntity<Result>(new Result("success", "핫플 삭제 성공"), HttpStatus.OK);
//		} catch (Exception e) {
//			return new ResponseEntity<Result>(new Result("fail", "핫플 삭제 실패"), HttpStatus.OK);
//		}
//	}
//
//
//
//
//}