package com.engagepoint.msap.repository;

import com.engagepoint.msap.domain.SubstantiatedAllegation;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the SubstantiatedAllegation entity.
 */
public interface SubstantiatedAllegationRepository extends JpaRepository<SubstantiatedAllegation,Long> {

}
