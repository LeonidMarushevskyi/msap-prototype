package com.engagepoint.msap.repository;

import com.engagepoint.msap.domain.LookupMaritalStatus;

import org.springframework.data.jpa.repository.*;

/**
 * Spring Data JPA repository for the LookupMaritalStatus entity.
 */
public interface LookupMaritalStatusRepository extends JpaRepository<LookupMaritalStatus,Long> {

}
