package com.rapidbite.ims.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.rapidbite.ims.entity.Requisition;


public interface RequisitionRepo extends JpaRepository<Requisition, Integer>
{

	@Query("SELECT t FROM Requisition t WHERE " +           
            "t.department = :dept AND t.status = :status ")
    Page<Requisition> findByStatus(@Param("dept") String dept, @Param("status") String status, Pageable pageRequest);
	
	@Query("SELECT t FROM Requisition t WHERE " +
            "LOWER(t.reqId) LIKE LOWER(CONCAT('%',:searchTerm, '%')) OR " +
            "LOWER(t.reqName) LIKE LOWER(CONCAT('%',:searchTerm, '%')) ")
    Page<Requisition> findBySearchTerm(@Param("searchTerm") String searchTerm, Pageable pageRequest);
	
	@Query("SELECT t FROM Requisition t WHERE " +
			 " t.status = :status OR t.status = :status2")
    Page<Requisition> findByStatusTerm(@Param("status") String status, @Param("status2") String status2, Pageable pageRequest);
	
	@Query("SELECT t FROM Requisition t WHERE " +           
            "t.reqId = :reqId ")
    Page<Requisition> findByReqId(@Param("reqId") String reqId, Pageable pageRequest);
	
	@Modifying(clearAutomatically = true)
	@Transactional(timeout = 10)
    @Query("UPDATE Requisition c SET c.status = :status WHERE c.reqId = :reqId")
    int updateReq(@Param("status") String status, @Param("reqId") String reqId);
}
