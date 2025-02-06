package com.shinhan.firstzone.twoway2;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.querydsl.core.types.Predicate;
import com.shinhan.firstzone.entity.MemberEntity;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class WebReplyService {

	@Autowired
	WebReplyRepository replyRepo;
	
	// 조회 - 전체
	public List<WebReplyDTO> selectAll() {
        List<WebReplyEntity> entityList = replyRepo.findAll();
        List<WebReplyDTO> dtoList = entityList.stream().map(this::entityToDTO).toList();
        return dtoList;
	}
	
	// 조회 - 특정 rno의 상세보기
	public WebReplyDTO selectByRno(Long rno) {
        WebReplyEntity webReplyEntity = replyRepo.findById(rno).orElse(null);
        return entityToDTO(webReplyEntity);
	}

	// 조회 - 특정member가 작성한 reply
	public List<WebReplyDTO> selectByMember(MemberEntity member) {
        List<WebReplyEntity> byReplyer = replyRepo.findByReplyer(member);
        List<WebReplyDTO> list = byReplyer.stream().map(this::entityToDTO).toList();
        return list;
	}	
	
	// 조회 - 특정board의 reply
	public List<WebReplyDTO> selectByBoard(WebBoardEntity board) {
        List<WebReplyEntity> byBoard = replyRepo.findByBoard(board);
        List<WebReplyDTO> list = byBoard.stream().map(this::entityToDTO).toList();
        
        return list;
	}	
	
	// 조회 - 동적SQL
	public List<WebReplyDTO> selectByDynamicSQL(String type, String keyword) {
        Predicate predicate = replyRepo.makePredicate(type, keyword);
        List<WebReplyEntity> all = (List<WebReplyEntity>)  replyRepo.findAll(predicate);
        List<WebReplyDTO> list = all.stream().map(this::entityToDTO).toList();
        return list;
	}
	
	// 입력
	public WebReplyDTO insertReply(WebReplyDTO reply) {
		WebReplyEntity entity = dtoToEntity(reply);
		MemberEntity member = MemberEntity.builder().mid(reply.getMid()).build();
		entity.setReplyer(member);
		WebReplyDTO newDto = entityToDTO(replyRepo.save(entity));
		return newDto;
	}
	
	// 수정
	public WebReplyDTO updateReply(WebReplyDTO reply) {
		WebReplyEntity entity = dtoToEntity(reply);
		MemberEntity member = MemberEntity.builder().mid(reply.getMid()).build();
		entity.setReplyer(member);
		entity.setRegdate(replyRepo.findById(reply.getRno()).get().getRegdate());
		WebReplyDTO newDto = entityToDTO(replyRepo.save(entity));		
		return newDto;
	}
	
	// 삭제 - 단일
	public void deleteReply(Long rno) {
		replyRepo.deleteById(rno);
	}

	// 삭제 - 복수
	public void deleteReplies(List<Long> rnoList) {
		replyRepo.deleteAllById(rnoList);
	}
	
	// 삭제 - 전체
	public void deleteAll() {
		replyRepo.deleteAll();
	}
	
    //입력, 수정(dto -> entity)
    public WebReplyEntity dtoToEntity(WebReplyDTO dto){
        ModelMapper mapper = new ModelMapper();
        WebReplyEntity entity = mapper.map(dto, WebReplyEntity.class);
        MemberEntity member = MemberEntity.builder().mid(dto.getMid()).build();
        WebBoardEntity board = WebBoardEntity.builder().bno(dto.getBoardBno()).build();

        entity.setReplyer(member);
        entity.setBoard(board);
        return entity;
    }

    //조회(entity -> dto)
    public WebReplyDTO entityToDTO(WebReplyEntity entity) {
        ModelMapper mapper = new ModelMapper();
        WebReplyDTO dto = mapper.map(entity, WebReplyDTO.class);

        dto.setMid(entity.getReplyer().getMid());
        dto.setMname(entity.getReplyer().getMname());
        dto.setBoardBno(entity.getBoard().getBno());
        return dto;
    }
}
