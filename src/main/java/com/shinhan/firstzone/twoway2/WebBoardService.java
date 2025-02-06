package com.shinhan.firstzone.twoway2;

import java.util.List;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.querydsl.core.types.Predicate;
import com.shinhan.firstzone.entity.MemberEntity;
import com.shinhan.firstzone.paging.PageRequestDTO;
import com.shinhan.firstzone.paging.PageResultDTO;

@Service
public class WebBoardService {

	@Autowired
	WebBoardRepository boardRepo;
	
    // 조회 - 전체
    public PageResultDTO<WebBoardDTO, WebBoardEntity> selectAllPaging(PageRequestDTO pageRequestDTO) {
        Predicate predicate = boardRepo.makePredicate(pageRequestDTO.getType(), pageRequestDTO.getKeyword());
        PageRequestDTO pageDto = new PageRequestDTO(pageRequestDTO.getPage(), pageRequestDTO.getSize());
        Pageable pageable = pageDto.getPageable(Sort.by("bno").descending());
        Page<WebBoardEntity> boardResult = boardRepo.findAll(predicate, pageable);
        Function<WebBoardEntity, WebBoardDTO> fn = this::entityToDTO;

        PageResultDTO<WebBoardDTO, WebBoardEntity> result = new PageResultDTO<>(boardResult, fn);
        return result;
    }
	
	// 조회 - 전체
	public List<WebBoardDTO> selectAll() {
        List<WebBoardEntity> boardEntityList = boardRepo.findAll();
        return boardEntityList.stream().map(this::entityToDTO).toList();
	}
	
	// 조회 - 특정 bno의 상세보기
	public WebBoardEntity selectById(Long bno) {
		return boardRepo.findById(bno).orElse(null);
	}
	
	// 조회 - 특정member가 작성한 board
	public List<WebBoardEntity> selectByMember(MemberEntity member) {
		return boardRepo.findByWriter(member);
	}
	
	// 조회 - 동적SQL
	public List<WebBoardEntity> dynamicSQL(String type, String keyword) {
		Predicate pre = boardRepo.makePredicate(type, keyword);
		return (List<WebBoardEntity>)boardRepo.findAll(pre);
	}
	
	// 입력
	public WebBoardEntity insertBoard(WebBoardEntity board) {
		 return boardRepo.save(board);
	}
	
	// 수정
	public WebBoardEntity updateBoard(WebBoardEntity board) {
		return boardRepo.save(board);
	}
	
	// 삭제 - 단일
	public void deleteBoard(Long bno) {
		boardRepo.deleteById(bno);
	}

	// 삭제 - 복수
	public void deleteBoards(List<Long> bnoList) {
		boardRepo.deleteAllById(bnoList);
	}
	
	// 삭제 - 전체
	public void deleteAll() {
		boardRepo.deleteAll();
	}
	
	public WebBoardEntity dtoToEntity(WebBoardDTO dto){
		MemberEntity member = MemberEntity.builder().mid(dto.getMid()).build();
		WebBoardEntity entity = WebBoardEntity.builder()
				.bno(dto.getBno())
				.title(dto.getTitle())
				.content(dto.getContent())
				.regdate(dto.getRegdate())					
				.writer(member)
				.build();
		return entity;
	}
	
	//Entity->DTO  (Data전송을 위함, controller, service, view에서 작업)
	//조회
	public WebBoardDTO entityToDTO(WebBoardEntity entity) {
		WebBoardDTO dto = WebBoardDTO.builder()
									 .bno(entity.getBno())
									 .title(entity.getTitle())
									 .mid(entity.getWriter().getMid())
									 .mname(entity.getWriter().getMname())
									 .content(entity.getContent())
									 .regdate(entity.getRegdate())
									 .updatedate(entity.getUpdatedate())
									 .replyCount(entity.getReplies().size())
									 .build();
		return dto;
	}
}
