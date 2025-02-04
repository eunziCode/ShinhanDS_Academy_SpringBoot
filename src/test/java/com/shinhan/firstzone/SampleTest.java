package com.shinhan.firstzone;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.shinhan.firstzone.dto.CarVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class SampleTest {

	@Test
	public void f2() {
		List<CarVO> carlist = new ArrayList<>();

		IntStream.rangeClosed(1, 5).forEach(i -> {
			CarVO c1 = CarVO.builder().model("ABC모델" + i).price(1000 * i).color("Black").build();
			carlist.add(c1);
		});
		log.info(carlist.toString());
		assertEquals(carlist.size(), 5);
	}

	//@Test
	public void f1() {
		CarVO c1 = CarVO.builder().model("ABC모델").price(1000).color("Black").build();
		log.info(c1.toString());
	}
}
