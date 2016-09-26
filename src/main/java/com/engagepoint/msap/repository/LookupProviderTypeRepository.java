package com.engagepoint.msap.repository;

import com.engagepoint.msap.domain.LookupProviderType;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the LookupProviderType entity.
 */
public interface LookupProviderTypeRepository extends JpaRepository<LookupProviderType,Long> {

}
