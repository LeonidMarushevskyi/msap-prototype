package com.engagepoint.msap.repository;

import com.engagepoint.msap.domain.Attachment;

import org.springframework.data.jpa.repository.*;

/**
 * Spring Data JPA repository for the Attachment entity.
 */
public interface AttachmentRepository extends JpaRepository<Attachment,Long> {

}
