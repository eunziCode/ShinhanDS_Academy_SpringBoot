package com.shinhan.firstzone;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

import com.shinhan.firstzone.onetomany.PDSBoardEntity;
import com.shinhan.firstzone.onetomany.PDSBoardRepository;
import com.shinhan.firstzone.onetomany.PDSFileEntity;
import com.shinhan.firstzone.onetomany.PDSFileRepository;

import jakarta.transaction.Transactional;

@SpringBootTest
public class OneToManyTest {

	@Autowired
	PDSBoardRepository boardRepo;
	
	@Autowired
	PDSFileRepository fileRepo;
	
	// file 38~42번 select
	@Test
	public void fileSelect() {
		Long[] arr = {38L, 39L, 40L, 41L, 42L};
		List<PDSFileEntity> flist = fileRepo.findAllById(Arrays.asList(arr));
		PDSBoardEntity entity6 = boardRepo.findById(6L).get();
		List<PDSFileEntity> files6 = entity6.getFiles();
		flist.forEach(f->{
			files6.add(f);
		});
		boardRepo.save(entity6);
	}
	
	//@Commit // Test시에는 종료 후 자동 rollback되기 때문에 DB에 저장하려면 @Commit 필요
	//@Transactional
	//@Test
	public void fileDelete() {
		fileRepo.fileDelete();
	}
	
	//@Test
	public void selectBoard() {
		boardRepo.findAll().forEach(board->{
			System.out.println("pid:"+board.getPid());
			System.out.println("pname:"+board.getPname());
			System.out.println("pwriter:"+board.getPwriter());
			System.out.println(board); // toString()이 자동 호출됨		
			System.out.println("files:"+board.getFiles());		
		});
	}
	
	// board를 통해서 file수정
	// 1. board3번의 첨부파일들을 지우자(참조안함)
	//@Test
	public void fileReferenceNo() {
		PDSBoardEntity entity3 = boardRepo.findById(3L).get();
		List<PDSFileEntity> files3 = entity3.getFiles();
		files3.clear();
		boardRepo.save(entity3);
	}
	
	// 2. board4번의 첨부파일을 5번으로 바꾸자
	//@Test
	public void change() {
		PDSBoardEntity entity4 = boardRepo.findById(4L).get();
		PDSBoardEntity entity5 = boardRepo.findById(5L).get();
		
		List<PDSFileEntity> files4 = entity4.getFiles();
		List<PDSFileEntity> files5 = entity5.getFiles();
		
		files4.forEach(f->{
			files5.add(f);
		});
		boardRepo.save(entity5);
	}
	
	// 3. board6번에 첨부파일 2개 추가
	//@Test
	public void add() {
		PDSBoardEntity entity6 = boardRepo.findById(6L).get();
		List<PDSFileEntity> files6 = entity6.getFiles();
		files6.add(new PDSFileEntity(null, "2차플젝.ppt")); // insert, update
		files6.add(new PDSFileEntity(null, "2차플젝.pdf")); // insert, update
		boardRepo.save(entity6);
	}
	
	// file insert
	//@Test
	public void fileInsert() {
		
		IntStream.rangeClosed(1, 5).forEach(i->{
			PDSFileEntity entity = PDSFileEntity.builder()
												.fileName("화요일백업-"+i+".zip")
												.build();
			fileRepo.save(entity);
		});
	}
	
	// board insert 10건, 각각의 board에 첨부파일이 3건
	//@Test
	public void boardInsert() {
		IntStream.rangeClosed(1, 10).forEach(i->{
			List<PDSFileEntity> fileList = new ArrayList<>();
			
			IntStream.rangeClosed(1, 3).forEach(j->{
				PDSFileEntity file = PDSFileEntity.builder()
												  .fileName("첨부파일-"+i+"-"+j+".ppt")
												  .build();
				fileList.add(file);
			});
			
			PDSBoardEntity board = PDSBoardEntity.builder()
												 .pname("board"+i)
												 .pwriter("user"+(i%5+1))
												 .files(fileList)
												 .build();
			
			boardRepo.save(board); // 부모 저장시 자식도 영향을 받음(cascade)
		});
	}
}
