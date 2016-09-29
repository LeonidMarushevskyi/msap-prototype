package com.engagepoint.msap.repository;

import com.engagepoint.msap.domain.LookupLanguage;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the LookupLanguage entity.
 */
public interface LookupLanguageRepository extends JpaRepository<LookupLanguage,Long> {

}
