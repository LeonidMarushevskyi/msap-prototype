package com.engagepoint.msap.repository;

import com.engagepoint.msap.domain.SupportedLanguage;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the SupportedLanguage entity.
 */
public interface SupportedLanguageRepository extends JpaRepository<SupportedLanguage,Long> {

}
