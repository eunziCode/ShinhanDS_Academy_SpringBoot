package com.shinhan.firstzone;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.shinhan.firstzone.hr.DeptEntity;
import com.shinhan.firstzone.hr.DeptRepository;
import com.shinhan.firstzone.hr.EmpEntity;
import com.shinhan.firstzone.hr.EmpRepository;
import com.shinhan.firstzone.hr.EmpService;
import com.shinhan.firstzone.hr.JobEntity;
import com.shinhan.firstzone.hr.JobRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class HRCrudTest {
	
	@Autowired
	DeptRepository deptRepo;
	
	@Autowired
	EmpRepository empRepo;
	
	@Autowired
	JobRepository jobRepo;
	
	
	@Autowired
	EmpService empService;
	
//	@Transactional
//	@Test
	void selectAllService() {
		empService.selectAllEmp().forEach(emp->{
			System.out.println(emp);
		});
	}
	
	// 조회 - 특정 직원
	// hire_date가 이상한 직원들의 hire_date를 수정
//	@Test
	void selectByHireDate2() {
		Long[] arr= {13L, 14L, 15L, 16L, 17L};
		empRepo.findAllById(Arrays.asList(arr)).forEach(emp->{
			emp.setHire_date(Date.valueOf(LocalDate.of(2024, 12, 30)));
			empRepo.save(emp);
		});
	}
	
	// 조회 - 특정 직원
	// hire_date가 null인 직원들의 hire_date를 수정
//	@Test
	void selectByHireDate() {
		empRepo.findByHireDateIsNull2().forEach(emp->{
			emp.setHire_date(new Date(2024,12,30));
			empRepo.save(emp);
		});
	}
	
	// 조회 - 특정 직원
	// manager_id가 null인 직원들의 manager를 직원번호 10으로 수정
//	@Test
	void selectByManager() {
		EmpEntity mng = EmpEntity.builder().employee_id(10L).build();
		empRepo.findByManagerIsNull().forEach(emp->{
			emp.setManager(mng);
			empRepo.save(emp);
		});
	}
	
	// 조회 - 특정 직원
	// job_id가 "IT_PROG"인 부서 조회, job_id가 null인 data 조회해서 HR_REP로 수정
//	@Test
	void selectById() {
		JobEntity job = JobEntity.builder().job_id("IT_PROG").build();
		empRepo.findByJob(job).forEach(emp->{
			System.out.println("특정 부서:"+emp);
		});
		System.out.println("--------------------------------");
		empRepo.findByJobIsNull().forEach(emp->{
			JobEntity job2 = JobEntity.builder().job_id("HR_REP").build();
			emp.setJob(job2);
			empRepo.save(emp);
		});
	}
	
	// 조회
//	@Transactional
//	@Test
	void selectAllEmp() {
		empRepo.findAll().forEach(emp->{
			System.out.print(emp);
			System.out.print(" job:"+emp.getJob());
			System.out.print(" manager:"+emp.getManager());
			System.out.print(" dept:"+emp.getDept());
			System.out.println();
		});
	}
	
	// 하나의 부서에 여러 직원 입력(20번 부서 3명 insert)
//	@Test
	void insertEmpByDept2() {
		List<EmpEntity> emplist = new ArrayList<>();
		
		DeptEntity dept = DeptEntity.builder()
									.department_id(270L)
									.department_name("총무부")
									.build();
		
		IntStream.rangeClosed(1, 5).forEach(i->{
			EmpEntity emp = EmpEntity.builder()
									 .first_name("Bruce")
									 .last_name("Ernst")
									 .email("BERNST"+i+"@gmail.com")
									 .phone_number("010-5678-123"+i)
//									 .hire_date(dt)
//									 .job(job)
									 .salary(500L)
									 .commission_pct(0.4)
//									 .manager(manager)
									 .dept(dept)
									 .build();
			emplist.add(emp);
		});
		dept.setEmplist(emplist);
		deptRepo.save(dept);
	}
	
	// 하나의 부서에 여러 직원 입력(30번 부서 5명 insert)
//	@Test
	void insertEmpByDept() {				
		deptRepo.findById(30L).ifPresentOrElse(dept->{
			// 존재하면 직원 추가
			List<EmpEntity> emplist = dept.getEmplist();
			Date dt = Date.valueOf(LocalDate.of(2023, 02, 07));
			JobEntity job = JobEntity.builder().job_id("IT_PROG").build();
			EmpEntity manager = EmpEntity.builder().employee_id(2L).build();
			
			IntStream.rangeClosed(1, 5).forEach(i->{
				EmpEntity emp = EmpEntity.builder()
										 .first_name("Lex")
										 .last_name("De Haan")
										 .email("LDEHAAN@gmail.com")
										 .phone_number("590-423-4567")
										 .hire_date(dt)
										 .job(job)
										 .salary(9000L)
										 .commission_pct(0.4)
										 .manager(manager)
										 .dept(dept)
										 .build();
				emplist.add(emp);
			});
			deptRepo.save(dept);
		}, ()->{
			// 존재하지 않으면 부서 insert, 직원 insert
		});
	}

//	@Test
	void insertJob() {
		String[] ids = {"AD_PRES","AD_VP","AD_ASST","FI_MGR","FI_ACCOUNT","AC_MGR","AC_ACCOUNT","SA_MAN","SA_REP","PU_MAN","PU_CLERK","ST_MAN","ST_CLERK","SH_CLERK","IT_PROG","MK_MAN","MK_REP","HR_REP","PR_REP","play"};
		String[] titles = {"President","Administration Vice President","Administration Assistant","Finance Manager","Accountant","Accounting Manager","Public Accountant","Sales Manager","Sales Representative","Purchasing Manager","Purchasing Clerk","Stock Manager","Stock Clerk","Shipping Clerk","Programmer","Marketing Manager","Marketing Representative","Human Resources Representative","Public Relations Representative","열심히놀기"};
		Long[] mins = {20080L,15000L,3000L,8200L,4200L,8200L,4200L,10000L,6000L,8000L,2500L,5500L,2008L,2500L,4000L,9000L,4000L,4000L,4500L,20000L};
		Long[] maxs = {40000L,30000L,6000L,16000L,9000L,16000L,9000L,20080L,12008L,15000L,5500L,8500L,5000L,5500L,10000L,15000L,9000L,9000L,10500L,30000L};
		
		for(int i=0; i<ids.length; i++) {
			JobEntity job = JobEntity.builder()
									 .job_id(ids[i])
									 .job_title(titles[i])
									 .min_salary(mins[i])
									 .max_salary(maxs[i])
									 .build();
			jobRepo.save(job);
		}
	}
	
//	@Test
	void insertDept() {
//			EmpEntity manager = EmpEntity.builder().build();
		Long[] ids = {10L,20L,30L,40L,50L,60L,70L,80L,90L,100L,110L,120L,130L,140L,150L,160L,170L,180L,190L,200L,210L,220L,230L,240L,250L,260L};
		String[] names = {"Administration","Marketing","Purchasing","Human Resources","Shipping","IT","Public Relations","Sales","Executive","Finance","Accounting","Treasury","Corporate Tax","Control And Credit","Shareholder Services","Benefits","Manufacturing","Construction","Contracting","Operations","IT Support","NOC","IT Helpdesk","Government Sales","Retail Sales","Recruiting"};
		
		for(int i=0; i<ids.length; i++) {
			
			DeptEntity dept = DeptEntity.builder()
										.department_id(ids[i])
										.department_name(names[i])
										//.manager(manager)
										.build();
			deptRepo.save(dept);
		}
	}
	
//	@Test
	void insertEmp() {
		Date dt = Date.valueOf(LocalDate.of(2023, 02, 07));
		Date dt2 = Date.valueOf(LocalDate.of(2025, 02, 02));
		
		JobEntity job = JobEntity.builder().job_id("AD_PRES").build();
		DeptEntity dept = DeptEntity.builder().department_id(90L).build();
		EmpEntity emp = EmpEntity.builder()
								 .first_name("Steven")
								 .last_name("King")
								 .email("SKING@gmail.com")
								 .phone_number("515-123-4567")
								 .hire_date(dt)
								 .job(job)
								 .salary(24000L)
								 .commission_pct(null)
								 .dept(dept)
								 .build();
		empRepo.save(emp);

		JobEntity job2 = JobEntity.builder().job_id("AD_VP").build();
		DeptEntity dept2 = DeptEntity.builder().department_id(90L).build();
		EmpEntity emp2 = EmpEntity.builder()
								  .first_name("Neena")
								  .last_name("Kochhar")
								  .email("NKOCHHAR@gmail.com")
								  .phone_number("515-123-4568")
								  .hire_date(dt2)
								  .job(job2)
								  .salary(17000L)
								  .commission_pct(null)
								  .manager(emp)
								  .dept(dept2)
								  .build();
		empRepo.save(emp2);
	}
	
}
