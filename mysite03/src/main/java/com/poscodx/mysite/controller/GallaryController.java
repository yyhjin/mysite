package com.poscodx.mysite.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/gallery")
public class GallaryController {
	
	@RequestMapping("")
	public String index() {
		return "gallery/index";
	}
	
}
