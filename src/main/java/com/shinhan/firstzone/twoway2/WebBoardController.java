package com.shinhan.firstzone.twoway2;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.shinhan.firstzone.entity.MemberEntity;
import com.shinhan.firstzone.paging.PageRequestDTO;
import com.shinhan.firstzone.paging.PageResultDTO;
import com.shinhan.firstzone.repository.MemberRepository;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/webboard")
public class WebBoardController {

	@Autowired
	WebBoardService boardService;
	
	@Autowired
	MemberRepository memberRepo;
	
	@GetMapping("/delete")
	public String delete(Long bno  ) {		
		boardService.deleteBoard(bno);
		return "redirect:list2";
	}
	
	@GetMapping("/register")
	public void insert() {
		
	}
	
	@PostMapping("/register")
	public String update(WebBoardDTO board, HttpSession session, 
						 Principal principal, Authentication authentication) {
		// security 추가하여 주석으로 변경함 
//		String mid = "spring1";
		

		// 1. Principal
		String a = principal.getName();
		System.out.println("1. Principal:"+a);
		
		// 2. Authentication
		UserDetails user = (UserDetails) authentication.getPrincipal();
		String b = user.getUsername();
		System.out.println("2. Authentication:"+b);
		
		// 3. SecurityContextHolder
		UserDetails user2 = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String c = user2.getUsername();
		System.out.println("3. SecurityContextHolder:"+c);
		
		// 4. HttpSession
		// session에 저장된 값 받이오기(개발자가 임의로 넣은 코드)
		MemberEntity member = (MemberEntity) session.getAttribute("loginMember");
		String mid = member.getMid();
		System.out.println("4. HttpSession:"+mid);
		
		
		board.setMid(mid);
		WebBoardEntity entity = boardService.dtoToEntity(board);
		boardService.updateBoard(entity);		
		
		return "redirect:list2";
	}
	
	@PostMapping("/update")
	public String update(WebBoardDTO board) {
		WebBoardEntity entity = boardService.dtoToEntity(board);
		boardService.updateBoard(entity);
		return "redirect:list2";
	}
	
	@GetMapping("/detail")
	public void selectById(Long bno, Model model) {
		model.addAttribute("board", getWebBoardDTO(bno));
	}
	
	@GetMapping("/detail2/{bno}")
	public String selectById2(@PathVariable Long bno, Model model) {
		model.addAttribute("board", getWebBoardDTO(bno));
		return "webboard/detail";
	}
	
	@GetMapping("/list")
	public void selectAll(Model model) {
		model.addAttribute("boardlist", boardService.selectAll());
	}
	
	@GetMapping("/list2")
    public String selectAllPaging(Model model, PageRequestDTO pageRequestDTO) {
        if (pageRequestDTO.getPage() == 0) {
            pageRequestDTO = new PageRequestDTO(1, 10);
        }

        PageResultDTO<WebBoardDTO, WebBoardEntity> boardResult = boardService.selectAllPaging(pageRequestDTO);

        model.addAttribute("boardResult", boardResult);
        model.addAttribute("pageRequestDTO", pageRequestDTO);
        return "webboard/list2";
    }
	
    private WebBoardDTO getWebBoardDTO(Long bno) {
        WebBoardEntity entity = boardService.selectById(bno);
        WebBoardDTO dto = boardService.entityToDTO(entity);
        return dto;
    }
}
