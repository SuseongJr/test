package com.cos.blog.test;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

//@Getter
//@Setter
@Data // lombok에서 getter, setter 동시에 만들고 싶다면
// @AllArgsConstructor // lombok에서 생성자 만들 때 사용
@NoArgsConstructor // lombok에서 빈 생성자 만들 때 사용
// @RequiredArgsConstructor // lombok에서 final이 붙은 것들에 대한 생성자를 만들 때 사용
public class Member {
	
	private int id;
	private String username;
	private String password;
	private String email;
	
	@Builder
	public Member(int id, String username, String password, String email) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.email = email;
	}
	

}
