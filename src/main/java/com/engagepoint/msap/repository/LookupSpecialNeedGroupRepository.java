package com.engagepoint.msap.repository;

import com.engagepoint.msap.domain.LookupSpecialNeedGroup;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the LookupSpecialNeedGroup entity.
 */
public interface LookupSpecialNeedGroupRepository extends JpaRepository<LookupSpecialNeedGroup,Long> {

}
