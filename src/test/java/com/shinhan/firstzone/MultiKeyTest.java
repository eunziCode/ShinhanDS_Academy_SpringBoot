package com.shinhan.firstzone;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.shinhan.firstzone.multikey.MultiKeyAEntity;
import com.shinhan.firstzone.multikey.MultiKeyARepository;
import com.shinhan.firstzone.multikey.MultiKeyB;
import com.shinhan.firstzone.multikey.MultiKeyBEntity;
import com.shinhan.firstzone.multikey.MultiKeyBRepository;

@SpringBootTest
public class MultiKeyTest {

	@Autowired
	MultiKeyARepository aRepo;
	
	@Autowired
	MultiKeyBRepository bRepo;
	
	//@Test
	public void f2() {
		MultiKeyB id = MultiKeyB.builder()
								.orderId(1)
								.productId(200)
								.build();
		MultiKeyBEntity entity = MultiKeyBEntity.builder()
												.id(id)
												.title("EmbeddedId사용함2")
												.count(10)
												.build();
		bRepo.save(entity);
	}
	
	//@Test
	public void f1() {
		MultiKeyAEntity entity = MultiKeyAEntity.builder()
												.orderId(2)
												.productId(100)
												.title("AA")
												.count(3)
												.build();
		MultiKeyAEntity entity2 = MultiKeyAEntity.builder()
												 .orderId(2)
												 .productId(200)
												 .title("BB")
												 .count(4)
												 .build();
		aRepo.save(entity);
		aRepo.save(entity2);
	}
}
