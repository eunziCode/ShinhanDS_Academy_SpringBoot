package net.shinhan.my;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SampleController2 {

	@GetMapping("/aa")
	public String f1() {
		return "base package이름를 따르지않는 패키지의 class";
	}
}
