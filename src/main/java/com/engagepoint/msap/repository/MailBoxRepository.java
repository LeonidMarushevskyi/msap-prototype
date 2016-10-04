package com.engagepoint.msap.repository;

import com.engagepoint.msap.domain.MailBox;

import org.springframework.data.jpa.repository.*;

/**
 * Spring Data JPA repository for the MailBox entity.
 */
public interface MailBoxRepository extends JpaRepository<MailBox,Long> {

}
