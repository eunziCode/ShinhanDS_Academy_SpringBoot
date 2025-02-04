package com.shinhan.firstzone.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.shinhan.firstzone.SpringComponent1;
import com.shinhan.firstzone.dto.CarVO;
import com.shinhan.firstzone.entity.BoardEntity;
import com.shinhan.firstzone.repository.BoardRepository;

import lombok.extern.slf4j.Slf4j;

//@Controller ==>수행후 page가 forward
@RestController //@Controller + @ResponseBody
@Slf4j
public class SampleController {

	 
	
	
	@GetMapping("/")
	public String f0() {
		return "Welcome!!! 환영합니다.";
	}
	
	@GetMapping("/hello1")
	public String f1() {
		return "Hello1~~~~~";
	}
	
	//Jackson 라이브러리가 JSON으로 변경하여 보내준다.  {키:값,model:""}
	@GetMapping("/hello2")
	public CarVO f2() {
		CarVO c1 = CarVO.builder()
				.model("ABC모델")
				.price(1000)
				.color("Black").build();
		return c1;
	}
	
	@GetMapping("/hello3")  //[{model:"ABC모델1"},{},{},{},{}]
	public List<CarVO> f3() {
		List<CarVO> carlist = new ArrayList<>();
		
		IntStream.rangeClosed(1, 5).forEach(i->{
			CarVO c1 = CarVO.builder()
					.model("ABC모델" + i)
					.price(1000*i)
					.color("Black").build();
			carlist.add(c1);
		});
		return carlist;
	}
	
	@Autowired
	SpringComponent1 comp1 ;
	
	@GetMapping("/hello4")
	public String f4() {
		return comp1.getData();
	}
	
	
}










