package com.engagepoint.msap.repository;

import com.engagepoint.msap.domain.LookupSpecialNeedType;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the LookupSpecialNeedType entity.
 */
public interface LookupSpecialNeedTypeRepository extends JpaRepository<LookupSpecialNeedType,Long> {

}
