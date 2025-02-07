package com.shinhan.firstzone.hr;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/emp")
public class EmpController {

	@Autowired
	EmpService empService;
	
	// 목록 조회
	@GetMapping("/list")
	public List<EmpDTO> selectAllService(Model model) {
		return empService.selectAllEmp();
	}
	
	// 상세 조회
	@GetMapping("/detail/{empid}")
	public EmpDTO selectById(@PathVariable Long empid, Model model) {
		return empService.selectById(empid);
	}
	
	// 입력
	@PostMapping("/insert")
	public EmpDTO insert(@RequestBody EmpDTO emp) {
		return empService.insertEmp(emp);
	}
	
	// 수정
	@PutMapping("/update")
	public EmpDTO update(@RequestBody EmpDTO emp) {
		return empService.updateEmp(emp);
	}
	
	// 삭제
	@DeleteMapping(value = "/delete/{empid}", produces = "text/plain;charset=utf-8")
	public String delete(@PathVariable Long empid) {
		empService.deleteEmp(empid);
		return "삭제완료";
	}
}
