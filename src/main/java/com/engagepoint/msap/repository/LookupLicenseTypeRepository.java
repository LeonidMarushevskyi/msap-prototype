package com.engagepoint.msap.repository;

import com.engagepoint.msap.domain.LookupLicenseType;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the LookupLicenseType entity.
 */
public interface LookupLicenseTypeRepository extends JpaRepository<LookupLicenseType,Long> {

}
