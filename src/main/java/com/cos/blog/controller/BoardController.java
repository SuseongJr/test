package com.cos.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.cos.blog.config.auth.PrincipalDetail;
import com.cos.blog.model.Board;
import com.cos.blog.service.BoardService;

@Controller
public class BoardController {
	
	@Autowired
	private BoardService boardService;

	@GetMapping({"", "/"})
	public String index(Model model, @PageableDefault(size=3, sort="id", direction = Sort.Direction.DESC) Pageable pabeable, @RequestParam(value="page", defaultValue = "0") int page) {
		Page<Board> boardList = this.boardService.getList(page);
		
		int currentPage = boardList.getPageable().getPageNumber() + 1; // 현재 페이지(0번 부터 시작해서 +1 해줌.)
		int firstPage = 1;
		int lastPage = 10;
		boolean listPageCheckFlg = false;
		
		// 페이지 번호 리스트틀 10개씩 출력하도록 한다.
		// 마지막 리스트가 10개 미만일 경우는 남은 번호만 출력하도록 한다.
		while(listPageCheckFlg == false) {
			if(boardList.getTotalPages() == 0) {
				lastPage = 1;
				listPageCheckFlg = true;
			}
			if(lastPage > boardList.getTotalPages()) {
				lastPage = boardList.getTotalPages();
			}
			if(currentPage >= firstPage && currentPage <= lastPage) {
				listPageCheckFlg = true;
			} else {
				firstPage += 10;
				lastPage += 10;
			}
		}

		// 현재 페이지 번호
		model.addAttribute("currentPage", currentPage);
		// 총 페이지
		model.addAttribute("totalPage", boardList.getTotalPages());
		//  페이지 번호 리스트 (첫)
		model.addAttribute("firstPage", firstPage);
		// 페이지 번호 리스트 (마지막)
		model.addAttribute("lastPage", lastPage);
		// 페이지, 게시글 정보
		model.addAttribute("boards", boardList);
		
		// /WEB-INF/views/index.jsp
//		model.addAttribute("boards", boardService.글목록(pabeable));
//		System.out.println("로그인 사용자 아이디: " + principal.getUsername());
		
		return "index"; // viewResolver 작동
	}
	
	@GetMapping("/board/{id}")
	public String findById(@PathVariable int id, Model model) {
		model.addAttribute("board", boardService.글상세보기(id));
		
		return "board/detail";
	}
	
	@GetMapping("/board/{id}/updateForm")
	public String updateForm(@PathVariable int id, Model model) {
		model.addAttribute("board", boardService.글상세보기(id));
		return "board/updateForm";
	}
	
	// USER 권한 필요.
	@GetMapping("/board/saveForm")
	public String saveForm() {
		return "board/saveForm";
	}
	
}
