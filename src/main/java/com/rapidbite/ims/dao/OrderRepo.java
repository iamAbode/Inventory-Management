package com.rapidbite.ims.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.rapidbite.ims.entity.Order;

public interface OrderRepo extends JpaRepository<Order, Integer>
{
	@Query("SELECT t FROM Order t WHERE " +           
            "t.department = :dept AND t.status = :status ")
    Page<Order> findByStatus(@Param("dept") String dept, @Param("status") String status, Pageable pageRequest);
	
	@Query("SELECT t FROM Order t WHERE " +
            "LOWER(t.ordId) LIKE LOWER(CONCAT('%',:searchTerm, '%')) OR " +
            "LOWER(t.purName) LIKE LOWER(CONCAT('%',:searchTerm, '%')) ")
    Page<Order> findBySearchTerm(@Param("searchTerm") String searchTerm, Pageable pageRequest);
	
	@Query("SELECT t FROM Order t WHERE " +
			 " t.status = :status OR t.status = :status2")
    Page<Order> findByStatusTerm(@Param("status") String status, @Param("status2") String status2, Pageable pageRequest);
	
	@Query("SELECT t FROM Order t WHERE " +           
            "t.ordId = :ordId ")
    Page<Order> findByOrdId(@Param("ordId") String ordId, Pageable pageRequest);
	
	@Modifying(clearAutomatically = true)
	@Transactional(timeout = 10)
    @Query("UPDATE Order c SET c.status = :status WHERE c.ordId = :ordId")
    int updateReq(@Param("status") String status, @Param("ordId") String ordId);
	
	@Query("SELECT t FROM Order t WHERE " +           
            " t.ordId = :ordId AND t.status = :status ")
    Page<Order> findByOrderTerm(@Param("ordId") String ordId, @Param("status") String status, Pageable pageRequest);
	
	@Modifying(clearAutomatically = true)
	@Transactional(timeout = 10)
    @Query("UPDATE Order c SET c.status = :status WHERE c.ordId = :ordId AND c.code = :code")
    int updateItem(@Param("ordId") String ordId, @Param("code") String code, @Param("status") String status);
}
