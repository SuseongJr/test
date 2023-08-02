package com.cos.blog.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.model.Board;
import com.cos.blog.repository.BoardRepository;

@RestController
public class ReplyController {
	
	@Autowired
	private BoardRepository boardRepost;
	
	@GetMapping("/test/board/{id}")
	public Board getBoard(@PathVariable int id) {
		return boardRepost.findById(id).get();
	}

}
