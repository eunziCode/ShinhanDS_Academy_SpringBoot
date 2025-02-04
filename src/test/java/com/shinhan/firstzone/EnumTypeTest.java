package com.shinhan.firstzone;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.shinhan.firstzone.entity.MemberRole;
import com.shinhan.firstzone.multikey.EnumTypeEntity;
import com.shinhan.firstzone.multikey.EnumTypeRepository;

@SpringBootTest
public class EnumTypeTest {

	@Autowired
	EnumTypeRepository repo;
	
//	@Test
	void select() {
		repo.findAll().forEach(m->{
			System.out.println(m.toString());
		});
	}
	
//	@Test
	void insert() {
		Set<MemberRole> roleSet = new HashSet<>();
		roleSet.add(MemberRole.ADMIN);
		roleSet.add(MemberRole.USER);
		roleSet.add(MemberRole.MANAGER);
		EnumTypeEntity entity = EnumTypeEntity.builder()
											  .mid("shinhan")
											  .mpassword("1234")
											  .mname("신한DS")
											  .mrole(roleSet)
											  .build();
		repo.save(entity);
	}
}
