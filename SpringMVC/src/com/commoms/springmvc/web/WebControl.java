package com.commoms.springmvc.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/web") 
public class WebControl {
	
	@RequestMapping("/index") 
	public String index(){
		return "index";
	}
}
