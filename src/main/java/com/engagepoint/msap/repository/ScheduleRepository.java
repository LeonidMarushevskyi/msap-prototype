package com.engagepoint.msap.repository;

import com.engagepoint.msap.domain.Schedule;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Schedule entity.
 */
public interface ScheduleRepository extends JpaRepository<Schedule,Long> {

}
