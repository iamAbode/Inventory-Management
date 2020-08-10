package com.rapidbite.ims.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rapidbite.ims.entity.Department;

public interface DepartmentRepo extends JpaRepository<Department, Integer>
{

	@Query("SELECT t FROM Department t WHERE " +
            "LOWER(t.code) LIKE LOWER(CONCAT('%',:searchTerm, '%')) OR " +
            "LOWER(t.name) LIKE LOWER(CONCAT('%',:searchTerm, '%'))")
    Page<Department> findBySearchTerm(@Param("searchTerm") String searchTerm, 
                                Pageable pageRequest);
}
