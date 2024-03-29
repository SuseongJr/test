package com.cos.blog.config.auth;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.cos.blog.model.User;

import lombok.Getter;

// 스프링 시큐리티가 로그인 요청을 가로채서 로그인을 진행하고 완료되면 UserDetails 타입의 오브젝트를 스프링 시큐리티의 고유한 세션 저장소에 저장해줌.
@Getter
public class PrincipalDetail implements UserDetails {
	
	private User user; // 콤포지션

	public PrincipalDetail(User user) {
		this.user = user;
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getUsername();
	}

	// 계정이 만료되지 않았는지 리턴함(true: 만료 안 됨)
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	// 계정이 잠겨지지 않았는지 리턴함(true: 잠기지 않음)
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	// 비밀번호가 만료되지 않았는지 리턴함(true: 만료 안 됨)
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	// 계정이 활성화(사용 가능) 되었는지 리턴함(true: 활성화)
	@Override
	public boolean isEnabled() {
		return true;
	}

	// 계정이 갖고있는 권한 목록을 리턴함(권한이 여러 개 있을 수 있어서 루프(for문)를 돌아야 하는데 우리는 하나만)
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {

		Collection<GrantedAuthority> collectors = new ArrayList<>();
//		collectors.add(new GrantedAuthority() {
//			
//			@Override
//			public String getAuthority() {
//				return "ROLE_" + user.getRole(); // ROLE_USER OR ROLE_ADMIN 등으로 리턴됨. "ROLE_" <- 필수!!
//			}
//		});
		
		collectors.add(()->{return "ROLE_" + user.getRole();}); // 람다식
		
		return collectors;
	}

}
