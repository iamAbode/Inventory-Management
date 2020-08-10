package com.rapidbite.ims.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.rapidbite.ims.entity.Vendor;

public interface VendorRepo extends JpaRepository<Vendor, Integer>
{
	@Query("SELECT t FROM Vendor t WHERE " +
            "LOWER(t.code) LIKE LOWER(CONCAT('%',:searchTerm, '%')) OR " +
            "LOWER(t.name) LIKE LOWER(CONCAT('%',:searchTerm, '%'))")
    Page<Vendor> findBySearchTerm(@Param("searchTerm") String searchTerm, 
                                Pageable pageRequest);
}
