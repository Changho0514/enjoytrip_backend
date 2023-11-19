package com.ssafy.hotplace.controller;

import java.io.File;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import com.ssafy.board.model.BoardDto;
import com.ssafy.config.Result;
import com.ssafy.hotplace.model.*;
import com.ssafy.hotplace.model.service.HotPlaceService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.UrlResource;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RestController
@CrossOrigin("*")
@RequestMapping("/hotplace")
@Api(tags = {"핫플레이스  API"})
public class HotPlaceController {

	private final Logger logger = LoggerFactory.getLogger(HotPlaceController.class);

	@Value("${file.path}")
	private String uploadPath;

	@Value("${file.path.upload-images}")
	private String uploadImagePath;

	@Value("${file.path.upload-files}")
	private String uploadFilePath;
	private ServletContext servletContext;
	private HotPlaceService hotplaceService;

	public HotPlaceController(ServletContext servletContext, HotPlaceService hotplaceService) {
		super();
        this.servletContext = servletContext;
		this.hotplaceService = hotplaceService;
	}

	@ApiOperation(value = "파일 제외 핫플 쓰기")
	@PostMapping("/write")
    public ResponseEntity<?> write(@RequestBody HotPlaceDto hotplaceDto) {
        logger.debug("write hotplaceDto : {}", hotplaceDto);
        try {
            hotplaceService.write(hotplaceDto);
            return new ResponseEntity<Integer>(hotplaceDto.getHotplaceNo(), HttpStatus.OK);
        } catch (Exception e) {
        	return new ResponseEntity<Result>(new Result("fail", "파일 제외 핫플 write 실패"), HttpStatus.OK);
        }
    }

	@ApiOperation(value = "파일 핫플 쓰기")
    @PostMapping("/write/file")
    public ResponseEntity<?> writeFile(@RequestParam("hotplaceNo") int hotplaceNo,
                                       @RequestParam("upfile") MultipartFile file) {
        logger.debug("write hotplaceDto : {}", file);
        HttpStatus status = null;
        try {//        FileUpload 관련 설정.
            logger.debug("MultipartFile.isEmpty : {}", file.isEmpty());
            if (!file.isEmpty()) {
                String realPath = servletContext.getRealPath("/upload");
//                String realPath = uploadPath;
                String today = new SimpleDateFormat("yyMMdd").format(new Date());
                String saveFolder = realPath + File.separator + today;
                logger.debug("저장 폴더-------------------------- : {}", saveFolder);
                File folder = new File(saveFolder);
                if (!folder.exists())
                    folder.mkdirs();
                String originalFileName = file.getOriginalFilename();
                if (!originalFileName.isEmpty()) {
                    String saveFileName = UUID.randomUUID().toString() + originalFileName.substring(originalFileName.lastIndexOf('.'));
                    
                    Map<String, Object> params = new HashMap<>();
                    params.put("hotplaceNo", hotplaceNo);
                    params.put("saveFolder", today);
                    params.put("saveFile", saveFileName);
                    params.put("originalFile", originalFileName);
                    logger.debug("원본 파일 이름 : {}, 실제 저장 파일 이름 : {}", file.getOriginalFilename(), saveFileName);
                    file.transferTo(new File(folder, saveFileName));
                    hotplaceService.writeFile(params);
                }
                return new ResponseEntity<Result>(new Result("success", "파일만 핫플 write 성공"), HttpStatus.OK);
            } else {
            	return new ResponseEntity<Result>(new Result("fail", "파일이 없습니다"), HttpStatus.OK);
            }
        } catch (Exception e) {
        	return new ResponseEntity<Result>(new Result("fail", "파일만 핫플 write 실패"), HttpStatus.OK);
        }
    }

	@ApiOperation(value = "파일 전체 가져오기")
	@PostMapping("/list")
	public ResponseEntity<?> list(@RequestBody HotPlaceParameterDto hotplaceParameterDto) {
		HotPlaceListDto list;
		try {
			list = hotplaceService.hotplaceList(hotplaceParameterDto);
			HttpHeaders header = new HttpHeaders();
			header.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

			return ResponseEntity.ok().headers(header).body(list);
		} catch (Exception e) {
			return new ResponseEntity<Result>(new Result("fail", "핫플목록 가져오기 실패"), HttpStatus.OK);
		}
	}

	@ApiOperation(value = "핫플 top3 가져오기")
	@GetMapping("/top3")
	public ResponseEntity<?> list() {
		List<HotPlaceDto> list;
		try {
			list = hotplaceService.hotplaceTOP3();
			if(list != null) {
				return new ResponseEntity<List<HotPlaceDto>>(list, HttpStatus.OK);
			} else {
				return new ResponseEntity<Result>(new Result("fail", "top3 핫플 목록이 없습니다"), HttpStatus.OK);
			}
		} catch (Exception e) {
			return new ResponseEntity<Result>(new Result("fail", "핫플목록 가져오기 실패"), HttpStatus.OK);
		}
	}

	@ApiOperation(value = "핫플 상세보기")
	@GetMapping("/detail/{hotplaceno}")
	public ResponseEntity<?> detail(@PathVariable("hotplaceno") int hotplaceNo) {
		logger.info("detail hotplaceNo : {}", hotplaceNo);
		HotPlaceDto hotplaceDto = null;
		try {
			hotplaceDto = hotplaceService.detail(hotplaceNo);
			return new ResponseEntity<HotPlaceDto>(hotplaceDto, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Result>(new Result("fail", "핫플 가져오기 실패"), HttpStatus.OK);
		}
	}

	@ApiOperation(value = "핫플 수정")
	@PutMapping("/modify")
	public ResponseEntity<?> modify(@RequestBody HotPlaceDto hotplaceDto){
		logger.debug("modify hotplaceDto : {}", hotplaceDto);
		try {
			hotplaceService.modify(hotplaceDto);
			return new ResponseEntity<Result>(new Result("success", "핫플 수정 성공"), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Result>(new Result("fail", "핫플 수정 실패"), HttpStatus.OK);
		}
	}

	@ApiOperation(value = "핫플 삭제")
	@DeleteMapping("/delete/{hotplaceno}")
	public ResponseEntity<?> delete(@PathVariable("hotplaceno") int hotplaceNo) {
		logger.debug("delete hotplaceNo : {}", hotplaceNo);
		try {
			hotplaceService.delete(hotplaceNo);
			return new ResponseEntity<Result>(new Result("success", "핫플 삭제 성공"), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Result>(new Result("fail", "핫플 삭제 실패"), HttpStatus.OK);
		}
	}
	
	@ApiOperation(value = "게시물 추천수 가져오기")
	@GetMapping("/getRecommend/{hotplaceNo}")
	public ResponseEntity<?> getRecommend(@PathVariable("hotplaceNo") int hotplaceNo){
		logger.debug("recommend hotplace : {}", hotplaceNo);
		try {
			int cnt = hotplaceService.getRecommendCount(hotplaceNo);
			return new ResponseEntity<Integer>(cnt, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Result>(new Result("fail", "추천수 가져오기 실패"), HttpStatus.OK);
		}
	}
	
	@ApiOperation(value = "추천하기")
	@GetMapping("/recommend/{hotplaceNo}")
	public ResponseEntity<?> recommend(@PathVariable("hotplaceNo") int hotplaceNo, String userId) throws Exception {
		try {
			hotplaceService.changeRecommendState(hotplaceNo, userId);
			return new ResponseEntity<Integer>(hotplaceNo, HttpStatus.OK);
		} catch (Exception e) {
			logger.error("핫플레이스  추천 실패 : {}", e);
			return new ResponseEntity<Result>(new Result("fail", "추천 하기 실패"), HttpStatus.OK);
		}
	}

	@ApiOperation(value = "나의 추천 목록 가져오기")
	@GetMapping("/MyRecommend/{userId}")
	public ResponseEntity<?> getMyRecommendList(@PathVariable("userId") String userId) throws Exception {
		try {
			List<HotPlaceDto> myRecommendList = hotplaceService.getMyRecommendList(userId);
			return new ResponseEntity<List<HotPlaceDto>>(myRecommendList, HttpStatus.OK);
		} catch (Exception e) {
			logger.error("나의 추천 목록 가져오기 실패 : {}", e);
			return new ResponseEntity<Result>(new Result("fail", "나의 추천 목록 가져오기 실패"), HttpStatus.OK);
		}
	}
	
//	@ApiOperation(value = "유저 아이디에 맞는 추천 목록 가져오기")
//	@GetMapping("/list/recommend/{userId}")
//	public ResponseEntity<?> recommendList(@PathVariable("userId") String userId) {
//		logger.debug("recommend hotplace list parameter id : {}", userId);
//		Map<String, Object> resultMap = new HashMap<>();
//		HttpStatus status = null;
//		List<HotPlaceDto> list = null;
//		try {
//			list = hotplaceService.getRecommendList(userId);
//			// PageNavigation pageNavigation = hotplaceService.makePageNavigation(map);
//			resultMap.put("list",list);
//			// resultMap.put("navigation", pageNavigation);
//			// resultMap.put("sort", map.get("sort"));
//			// resultMap.put("pgno", map.get("pgno"));
//			// resultMap.put("key", map.get("key"));
//			// resultMap.put("word", map.get("word"));
//			resultMap.put("message", SUCCESS);
//			status = HttpStatus.OK;
//		} catch (Exception e) {
//			logger.error("핫플레이스 추천한 목록 불러오기 실패 : {}", e);
//			resultMap.put("message", e.getMessage());
//			status = HttpStatus.INTERNAL_SERVER_ERROR;
//		}
//		return new ResponseEntity<>(resultMap, status);
//	}
	
//	 @GetMapping(value="/image", produces= MediaType.IMAGE_PNG_VALUE)
//  public @ResponseBody byte[] getImage(@RequestParam("filePath") String filePath) // A
//          throws IOException {
//      FileInputStream fis = null;
//      ByteArrayOutputStream baos = new ByteArrayOutputStream();
//
//      // String[] fileAr = fileTime.split("_");
//      // String filePath = fileAr[0];
//
//      // String fileDir = "D:\\Han\\sample\\" + filePath + "\\" + fileTime + "_" + value + ".png"; // 파일경로
//	 	logger.debug("이미지 출력 시도", filePath);
//      try{
//          fis = new FileInputStream(filePath);
//      } catch(FileNotFoundException e){
//          e.printStackTrace();
//      }
//
//      int readCount = 0;
//      byte[] buffer = new byte[1024];
//      byte[] fileArray = null;
//
//      try{
//          while((readCount = fis.read(buffer)) != -1){
//              baos.write(buffer, 0, readCount);
//          }
//          fileArray = baos.toByteArray();
//          fis.close();
//          baos.close();
//      } catch(IOException e){
//          throw new RuntimeException("File Error");
//      }
//      return fileArray;
//  }

}
