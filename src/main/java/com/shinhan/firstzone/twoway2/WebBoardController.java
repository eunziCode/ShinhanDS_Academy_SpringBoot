package com.shinhan.firstzone.twoway2;

import org.springframework.beans.factory.annotation.Autowired;
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
	public String update(WebBoardDTO board, HttpSession session) {
		String mid = "spring1";
		
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
        return "/webboard/list2";
    }
	
    private WebBoardDTO getWebBoardDTO(Long bno) {
        WebBoardEntity entity = boardService.selectById(bno);
        WebBoardDTO dto = boardService.entityToDTO(entity);
        return dto;
    }
}
