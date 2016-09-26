package com.engagepoint.msap.repository;

import com.engagepoint.msap.domain.LookupQualityRating;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the LookupQualityRating entity.
 */
public interface LookupQualityRatingRepository extends JpaRepository<LookupQualityRating,Long> {

}
