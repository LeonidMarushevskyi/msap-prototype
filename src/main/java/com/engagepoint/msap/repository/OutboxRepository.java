package com.engagepoint.msap.repository;

import com.engagepoint.msap.domain.Outbox;

import org.springframework.data.jpa.repository.*;

/**
 * Spring Data JPA repository for the Outbox entity.
 */
public interface OutboxRepository extends JpaRepository<Outbox,Long> {

}
