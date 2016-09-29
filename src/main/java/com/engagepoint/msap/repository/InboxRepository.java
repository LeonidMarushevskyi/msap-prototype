package com.engagepoint.msap.repository;

import com.engagepoint.msap.domain.Inbox;

import org.springframework.data.jpa.repository.*;

/**
 * Spring Data JPA repository for the Inbox entity.
 */
public interface InboxRepository extends JpaRepository<Inbox,Long> {

}
