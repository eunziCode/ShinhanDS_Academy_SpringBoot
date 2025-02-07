package com.shinhan.firstzone.hr;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class EmpService {

	final DeptRepository deptRepo;
	final EmpRepository empRepo;
	final JobRepository jobRepo;
	
	public void deleteEmp(Long empid) {
		empRepo.deleteById(empid);
	}
	
	public EmpDTO updateEmp(EmpDTO emp) {
		EmpEntity entity = dtoToEntity(emp);
		EmpEntity updateEntity = empRepo.save(entity);
		return entityToDTO(updateEntity);
	}
	
	public EmpDTO insertEmp(EmpDTO emp) {
		EmpEntity entity = dtoToEntity(emp);
		EmpEntity newEntity = empRepo.save(entity);
		return entityToDTO(newEntity);
	}
	
	@Transactional
	public EmpDTO selectById(Long id) {
		EmpEntity entity = empRepo.findById(id).orElse(null);
		EmpDTO dto = entityToDTO(entity);
		return dto;
	}
	
	@Transactional
	public List<EmpDTO> selectAllEmp() {
		List<EmpEntity> entityList = empRepo.findAll();
		List<EmpDTO> dtoList = entityList.stream().map(this::entityToDTO).toList();
		return dtoList;
	}
	
	// entity → DTO : DB에서 가져온 data를 client에 보내기 ▶ select
	public EmpDTO entityToDTO(EmpEntity entity) {
		ModelMapper mapper = new ModelMapper();
		EmpDTO dto = mapper.map(entity, EmpDTO.class);
		
		dto.setJob_id(entity.getJob().getJob_id());
		dto.setManager_id(entity.getManager().employee_id);
		dto.setDepartment_id(entity.getDept().getDepartment_id());
		
		return dto;
	}

	// DTO → entity : client에서 들어온 data를 DB에 ▶ insert, update
	public EmpEntity dtoToEntity(EmpDTO dto){
		ModelMapper mapper = new ModelMapper();
		EmpEntity entity = mapper.map(dto, EmpEntity.class);
		
		JobEntity job = JobEntity.builder().job_id(dto.getJob_id()).build();
		EmpEntity manager = EmpEntity.builder().employee_id(dto.getManager_id()).build();
		DeptEntity dept = DeptEntity.builder().department_id(dto.getDepartment_id()).build();
		
		entity.setJob(job);
		entity.setManager(manager);
		entity.setDept(dept);
		
		return entity;
	}

}
