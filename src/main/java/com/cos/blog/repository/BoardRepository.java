package com.cos.blog.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.cos.blog.model.Board;
 
// @Repository 자동으로 bean 등록이 되기에 생략 가능!
public interface BoardRepository extends JpaRepository<Board, Integer>{
	// 페이징
	Page<Board> findAll(Pageable pageable);
}
