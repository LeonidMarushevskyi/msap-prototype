package com.engagepoint.msap.repository;

import com.engagepoint.msap.domain.LookupAgeGroups;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the LookupAgeGroups entity.
 */
public interface LookupAgeGroupsRepository extends JpaRepository<LookupAgeGroups,Long> {

}
