package com.engagepoint.msap.repository;

import com.engagepoint.msap.domain.Price;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Price entity.
 */
public interface PriceRepository extends JpaRepository<Price,Long> {

}
