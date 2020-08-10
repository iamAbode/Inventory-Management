package com.rapidbite.ims.dao;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.rapidbite.ims.entity.User;

public interface UserRepo extends JpaRepository<User, Integer>
{	
	@Query("SELECT t FROM User t WHERE " +
            "LOWER(t.firstname) LIKE LOWER(CONCAT('%',:searchTerm, '%')) OR " +
            "LOWER(t.lastname) LIKE LOWER(CONCAT('%',:searchTerm, '%')) OR " +
            "t.department = :deptId ")
    Page<User> findBySearchTerm(@Param("searchTerm") String searchTerm,
    							@Param("deptId") String deptId,
                                Pageable pageRequest);
	
	@Query("SELECT t FROM User t WHERE " +           
            "t.department = :deptId ")
    Page<User> findByDept(@Param("deptId") String deptId, Pageable pageRequest);
	
	@Query("SELECT t FROM User t WHERE " +           
            "t.email = :email ")
	Optional<User> findByEmail(@Param("email") String email);
}
