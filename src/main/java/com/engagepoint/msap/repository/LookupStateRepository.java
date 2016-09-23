package com.engagepoint.msap.repository;

import com.engagepoint.msap.domain.LookupState;

import org.springframework.data.jpa.repository.*;

/**
 * Spring Data JPA repository for the LookupState entity.
 */
public interface LookupStateRepository extends JpaRepository<LookupState,Long> {

}
