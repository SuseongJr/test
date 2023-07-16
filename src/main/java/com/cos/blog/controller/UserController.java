package com.cos.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

	// /auth를 사용하여 인증이 된 사용자만 출입할 수 있도록 함.
	// 주소가 static이하의 것들(/js/**, /css/**, /image/** 등)과 /이기만 한 것까지만 허용.
	
	@GetMapping("/auth/joinForm")
	public String joinForm() {
		return "user/joinForm";
	}
	
	@GetMapping("/auth/loginForm")
	public String loginForm() {
		return "user/loginForm";
	}
	
}
