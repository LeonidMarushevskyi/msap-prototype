package com.engagepoint.msap.repository;

import com.engagepoint.msap.domain.LookupCounty;

import org.springframework.data.jpa.repository.*;

/**
 * Spring Data JPA repository for the LookupCounty entity.
 */
public interface LookupCountyRepository extends JpaRepository<LookupCounty,Long> {

}
