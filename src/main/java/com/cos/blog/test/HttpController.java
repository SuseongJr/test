package com.cos.blog.test;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HttpController {
	
	private static final String TAG = "HttpController: ";
	
	// localhost:8000/blog/http/lombok
	@GetMapping("/http/lombok")
	public String lombokTest() {
		// Member m = new Member(1, "ssar", "1234", "ssar@gmail.com");
		Member m = Member.builder().username("ssar").password("1234").email("ssar@gmail.com").build();
		// System.out.println(TAG + "getter: " + m.getId());
		System.out.println(TAG + "getter: " + m.getUsername());
		m.setId(5000);
		m.setUsername("cos");
		
		// System.out.println(TAG + "setter: " + m.getId());
		System.out.println(TAG + "setter: " + m.getUsername());
		
		return "lombokTest complete";
	}

	// http://localhost:8080/http/get (select)
	// 인터넷 브라우저 요청은 무조건 get요청만 할 수 있음
	// get 외의 것은 405 에러가 뜸
	@GetMapping("/http/get")
	public String getTest(Member m) {
		
		
		
		return "get 요청: " + m.getId() + ", " + m.getUsername() + ", " + m.getPassword()
				+ ", " + m.getEmail();
	}
	
	// http://localhost:8080/http/post (insert)
	@PostMapping("/http/post")
	public String postTest(@RequestBody Member m) { // RequestBody는 JSON을 사용
		return "post 요청: " + m.getId() + ", " + m.getUsername() + ", " + m.getPassword()
		+ ", " + m.getEmail();
	}
	
	// http://localhost:8080/http/put (update)
	@PutMapping("/http/put")
	public String putTest(@RequestBody Member m) {
		return "put 요청: " + m.getId() + ", " + m.getUsername() + ", " + m.getPassword()
		+ ", " + m.getEmail();
	}
	
	// http://localhost:8080/http/delet (delete)
	@DeleteMapping("/http/delete")
	public String deletTest() {
		return "delete 요청";
	}
	
}
