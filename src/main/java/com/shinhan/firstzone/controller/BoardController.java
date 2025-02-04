package com.shinhan.firstzone.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shinhan.firstzone.entity.BoardEntity;
import com.shinhan.firstzone.repository.BoardRepository;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/board")
@Slf4j
public class BoardController {

	@Autowired
	BoardRepository boardRepo; // BoardRepository인터페이스 구현 class가 Injection된다.

	@DeleteMapping("/delete/{bno}")
	public String delete(@PathVariable("bno") Long bno) {
		boardRepo.deleteById(bno);
		return "삭제함";
	}

	// {bno:8, title:"수정할 내용"}
	@PutMapping("/update")
	public String update(@RequestBody BoardEntity board) {
		boardRepo.findById(board.getBno()).ifPresent(searchBoard -> {
			searchBoard.setContent(board.getContent());
			searchBoard.setTitle(board.getTitle());
			searchBoard.setWriter(board.getWriter());
			boardRepo.save(searchBoard);
		});
		return "수정";
	}

	@PostMapping("/insert")
	public BoardEntity insert(@RequestBody BoardEntity board) {
		BoardEntity result = boardRepo.save(board);
		return result;
	}

	// 특정board조회
	@GetMapping("/{bno}")
	public BoardEntity selectById(@PathVariable("bno") Long bno) {
		return boardRepo.findById(bno).orElse(null);
	}

	// 모두조회
	@GetMapping("/all")
	public List<BoardEntity> selectAll() {
		return (List<BoardEntity>) boardRepo.findAll();
	}

	@GetMapping("/page/{pagenum}")
	public List<BoardEntity> retrieve1(@PathVariable("pagenum") int page) {
		// 페이지번호는 0부터 시작
		// Pageable pageable = PageRequest.of(0, 5);
		// Pageable pageable = PageRequest.of(0, 5, Sort.by("bno").ascending());

		String[] columns = { "writer", "bno" };
		Pageable pageable = PageRequest.of(page - 1, 7, Sort.Direction.DESC, columns);
		Page<BoardEntity> result = boardRepo.findAll(pageable);

		log.info("getSize:" + result.getSize());
		log.info("getNumber:" + result.getNumber());
		log.info("getTotalPages:" + result.getTotalPages());
		log.info("getTotalElements:" + result.getTotalElements());
		List<BoardEntity> blist = result.getContent();
		blist.forEach(board -> {
			log.info(board.toString());
		});
		return blist;
	}
}
