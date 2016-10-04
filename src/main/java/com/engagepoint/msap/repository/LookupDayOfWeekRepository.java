package com.engagepoint.msap.repository;

import com.engagepoint.msap.domain.LookupDayOfWeek;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the LookupDayOfWeek entity.
 */
public interface LookupDayOfWeekRepository extends JpaRepository<LookupDayOfWeek,Long> {

}
