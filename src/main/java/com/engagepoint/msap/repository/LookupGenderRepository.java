package com.engagepoint.msap.repository;

import com.engagepoint.msap.domain.LookupGender;

import org.springframework.data.jpa.repository.*;

/**
 * Spring Data JPA repository for the LookupGender entity.
 */
public interface LookupGenderRepository extends JpaRepository<LookupGender,Long> {

}
