package com.engagepoint.msap.repository;

import com.engagepoint.msap.domain.Provider;

import org.springframework.data.jpa.repository.*;

/**
 * Spring Data JPA repository for the Provider entity.
 */
public interface ProviderRepository extends JpaRepository<Provider,Long> {

}
