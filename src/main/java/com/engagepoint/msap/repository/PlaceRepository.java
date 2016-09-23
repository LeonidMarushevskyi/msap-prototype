package com.engagepoint.msap.repository;

import com.engagepoint.msap.domain.Place;

import org.springframework.data.jpa.repository.*;

/**
 * Spring Data JPA repository for the Place entity.
 */
public interface PlaceRepository extends JpaRepository<Place,Long> {

}
