package com.shinhan.firstzone.twoway2;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shinhan.firstzone.paging.PageRequestDTO;
import com.shinhan.firstzone.paging.PageResultDTO;
import com.shinhan.firstzone.repository.MemberRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/webboard")
public class WebBoardRestController { // react와의 통신을 위한 restcontroller

	@Autowired
	WebBoardService boardService;
	
	@Autowired
	MemberRepository memberRepo;
	
	
	@PostMapping("/register")
	public String insertPost(@RequestBody WebBoardDTO board) {
		WebBoardEntity entity = boardService.dtoToEntity(board);
		boardService.insertBoard(entity);		
		
		return "insert OK";
	}
	
	@PutMapping("/update")
	public String update(@RequestBody WebBoardDTO board) {
		WebBoardEntity entity = boardService.dtoToEntity(board);
		boardService.updateBoard(entity);
		return "update OK";
	}
	
	@DeleteMapping("/delete/{bno}")
	public String delete(@PathVariable Long bno) {		
		boardService.deleteBoard(bno);
		return "delete OK";
	}
	
//	@GetMapping("/detail/{bno}") = @GetMapping(value="/detail/{bno}", produces = "application/json")
	@GetMapping("/detail/{bno}")
	public WebBoardDTO selectById2(@PathVariable Long bno) {
		WebBoardEntity entity = boardService.selectById(bno);
        WebBoardDTO dto = boardService.entityToDTO(entity);
		return dto;
	}
	
	@GetMapping("/list")
	public List<WebBoardDTO> selectAll() {
		return boardService.selectAll();
	}
	
	@GetMapping("/list2")
    public PageResultDTO<WebBoardDTO, WebBoardEntity> selectAllPaging(@RequestBody PageRequestDTO pageRequestDTO) {
        if (pageRequestDTO.getPage() == 0) {
            pageRequestDTO = new PageRequestDTO(1, 10);
        }

        return boardService.selectAllPaging(pageRequestDTO);
    }
	
}
