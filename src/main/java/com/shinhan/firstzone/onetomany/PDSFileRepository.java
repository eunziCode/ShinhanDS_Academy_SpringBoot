package com.shinhan.firstzone.onetomany;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface PDSFileRepository extends JpaRepository<PDSFileEntity, Long> {

	// @Query : select문만 지원함
	// @Modifying & @Transcational : select문이 아닌 DML문
	@Modifying
	@Query(value="delete from tbl_pdsfile where pdsno is null", nativeQuery = true)
	public void fileDelete();
}
