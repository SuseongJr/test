package com.cos.blog.model;

import java.sql.Timestamp;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder // 빌더 패턴
@Entity // User 클래스가 MySQL에 자동으로 테이블 생성
// @DynamicInsert insert시에 null인 필드 제외시
public class User {
	
	@Id //primary key
	@GeneratedValue(strategy = GenerationType.IDENTITY) // 프로젝트에서 연결된 db의 넘버링 전략을 따라
	private int id; // 시퀀스, auto_increment
	
	@Column(nullable = false, length = 30, unique = true)
	private String username;
	
	@Column(nullable = false, length = 100)
	private String password;
	
	@Column(nullable = false, length = 50)
	private String email;
	
	// @ColumnDefault("user")
	// DB는 RoleType이라는 것이 없음. 아래의 어노테이션을 추가해야 함.
	@Enumerated(EnumType.STRING)
	private RoleType role; // Enum을 쓰는 것이 좋음 / admin, user, manager 등의 역할
	
	@CreationTimestamp // 시간 자동 입력
	private Timestamp createDate;

}