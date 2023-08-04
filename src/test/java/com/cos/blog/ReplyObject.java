package com.cos.blog;

import org.junit.Test;

import com.cos.blog.model.Reply;

public class ReplyObject {

	@Test
	public void 투스트링테스트() {
		Reply reply = Reply.builder()
				.id(1)
				.user(null)
				.board(null)
				.content("Hello")
				.build();
		
		System.out.println(reply); // 오브젝트 출력시 toString()이 자동 호출됨.
	}
	
}
