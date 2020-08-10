package com.rapidbite.ims.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.rapidbite.ims.entity.Item;


public interface ItemRepo extends JpaRepository<Item, Integer>
{
	@Query("SELECT t FROM Item t WHERE " +
            "LOWER(t.name) LIKE LOWER(CONCAT('%',:searchTerm, '%')) OR " +
            "LOWER(t.price) LIKE LOWER(CONCAT('%',:searchTerm, '%')) OR " +
            "t.category = :catId ")
    Page<Item> findBySearchTerm(@Param("searchTerm") String searchTerm,
    							@Param("catId") String catId,
                                Pageable pageRequest);
	
	@Query("SELECT t FROM Item t WHERE " +           
            "t.category = :catId ")
    Page<Item> findByCategory(@Param("catId") String catId, Pageable pageRequest);
	
	@Modifying(clearAutomatically = true)
	@Transactional(timeout = 10)
    @Query("UPDATE Item c SET c.balance = c.balance + :val WHERE c.code = :code")
    int updateItem(@Param("code") String code, @Param("val") int val);
}
