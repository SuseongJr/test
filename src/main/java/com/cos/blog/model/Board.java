package com.cos.blog.model;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Board {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment
	private int id;
	
	@Column(nullable = false, length = 100)
	private String title;
	
	@Lob // 대용량 데이터
	private String content; // 섬머노트 사용할 것

	private int count; // 조회수
	
	@ManyToOne(fetch = FetchType.EAGER) // Many = Board, One = User -> 한 명의 유저는 여러 게시글을 쓸 수 있음.
	@JoinColumn(name = "userId")
	private User user; // DB는 오브젝트 저장 불가. fk, java는 오브젝트 저장 가능
	
	@OneToMany(mappedBy = "board", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE) // mappedBy : 연관관계의 주인이 아님(FK가 아님) -> db에 컬럼 만들지 않음
	@JsonIgnoreProperties({"board"}) // 무한 참조 방지.
	// @OrderBy("id desc") 댓글 내림차순 정렬
	private List<Reply> replys;
	
	@CreationTimestamp
	private Timestamp createDate;
	
}
