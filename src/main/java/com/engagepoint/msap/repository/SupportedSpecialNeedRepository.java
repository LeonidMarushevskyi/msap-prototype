package com.engagepoint.msap.repository;

import com.engagepoint.msap.domain.SupportedSpecialNeed;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the SupportedSpecialNeed entity.
 */
public interface SupportedSpecialNeedRepository extends JpaRepository<SupportedSpecialNeed,Long> {

}
