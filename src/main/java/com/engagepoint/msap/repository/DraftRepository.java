package com.engagepoint.msap.repository;

import com.engagepoint.msap.domain.Draft;

import org.springframework.data.jpa.repository.*;

/**
 * Spring Data JPA repository for the Draft entity.
 */
public interface DraftRepository extends JpaRepository<Draft,Long> {

}
