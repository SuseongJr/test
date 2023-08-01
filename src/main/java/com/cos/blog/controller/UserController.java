package com.cos.blog.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.cos.blog.model.KakaoProfile;
import com.cos.blog.model.OAuthToken;
import com.cos.blog.model.User;
import com.cos.blog.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class UserController {
	
	@Value("${cos.key}")
	private String cosKey;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserService userService;

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
	
	@GetMapping("/auth/kakao/callback")
	public String kakaoCallback(String code) { // Data를 리턴해주는 컨트롤러 함수가 됨.
		// POST 방식을 사용하여 카카오 쪽으로 key=value 데이터를 요청함.
		RestTemplate rt = new RestTemplate();
		
		// HttpHeader 오브젝트 생성.
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
		
		// HttpBody 오브젝트 생성.
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("grant_type", "authorization_code");
		params.add("client_id", "65b63bd7481b3493d6d2e195ae1c56c3");
		params.add("redirect_uri", "http://localhost:8000/auth/kakao/callback");
		params.add("code", code);
		
		// HttpHeader와 HttpBody를 하나의 오브젝트에 담기(하단의 exchange라는 함수가 HttpEntity를 받기 때문)
		HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(params, headers);
		
		// Http Post방식으로 요청하기. responsed의 응답을 받음.
		ResponseEntity<String> response = 
				rt.exchange("https://kauth.kakao.com/oauth/token", HttpMethod.POST, kakaoTokenRequest, String.class);
		
		ObjectMapper objectMapper = new ObjectMapper();
		OAuthToken oauthToken = null;
		
		try {
			oauthToken = objectMapper.readValue(response.getBody(), OAuthToken.class);
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		System.out.println("kakao access token: " + oauthToken.getAccess_token());
		
		
		RestTemplate rt2 = new RestTemplate();
		
		// HttpHeader 오브젝트 생성.
		HttpHeaders headers2 = new HttpHeaders();
		headers2.add("Authorization", "Bearer " + oauthToken.getAccess_token());
		headers2.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
		
		// HttpHeader와 HttpBody를 하나의 오브젝트에 담기(하단의 exchange라는 함수가 HttpEntity를 받기 때문)
		HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest2 = new HttpEntity<>(headers2);
		
		// Http Post방식으로 요청하기. responsed의 응답을 받음.
		ResponseEntity<String> response2 = 
				rt2.exchange("https://kapi.kakao.com/v2/user/me", HttpMethod.POST, kakaoProfileRequest2, String.class);
		
		ObjectMapper objectMapper2 = new ObjectMapper();
		KakaoProfile kakaoProfile = null;
		
		try {
			kakaoProfile = objectMapper2.readValue(response2.getBody(), KakaoProfile.class);
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		System.out.println("카카오 아이디(번호): " + kakaoProfile.getId());
		System.out.println("카카오 이메일(번호): " + kakaoProfile.getKakao_account().getEmail());
		System.out.println("블로그 서버 유저 네임: " + kakaoProfile.getKakao_account().getEmail() + "_" + kakaoProfile.getId());
		System.out.println("블로그 서버 이메일: " + kakaoProfile.getKakao_account().getEmail());
		
		// UUID: 중복되지 않는 어떤 특정 값을 만들어내는 알고리즘.
		// UUID garbagePassword = UUID.randomUUID();
		System.out.println("블로그 서버 비밀번호: " + cosKey);
		
		User kakaoUser = User.builder()
				.username(kakaoProfile.getKakao_account().getEmail() + "_" + kakaoProfile.getId())
				.password(cosKey)
				.email(kakaoProfile.getKakao_account().getEmail())
				.oauth("kakao")
				.build();
		
		// 이미 가입된 사람인지 확인 후 처리
		User originUser = userService.회원찾기(kakaoUser.getUsername());
		
		if(originUser.getUsername() == null) {
			System.out.println("기존 회원이 아닙니다.");
			userService.회원가입(kakaoUser);
		}
		System.out.println("자동 로그인 진행합니다.");

		// null이 아니면 바로 로그인 처리.
		Authentication authentication 
			= authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(kakaoUser.getUsername(), cosKey));
		SecurityContextHolder.getContext().setAuthentication(authentication);

		return "redirect:/";
	}
	
	@GetMapping("/user/updateForm")
	public String updateForm() {
		return "user/updateForm";
	}
}
