package com.engagepoint.msap.repository;

import com.engagepoint.msap.domain.OpenSlot;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the OpenSlot entity.
 */
public interface OpenSlotRepository extends JpaRepository<OpenSlot,Long> {

}
