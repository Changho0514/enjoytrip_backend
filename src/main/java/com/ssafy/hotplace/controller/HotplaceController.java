package com.ssafy.hotplace.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hotplace")
@CrossOrigin("*")
public class HotplaceController {
	
	private final Logger logger = LoggerFactory.getLogger(HotplaceController.class);

}
