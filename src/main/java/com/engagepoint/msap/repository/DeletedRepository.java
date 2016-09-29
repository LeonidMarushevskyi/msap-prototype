package com.engagepoint.msap.repository;

import com.engagepoint.msap.domain.Deleted;
import com.engagepoint.msap.domain.Message;
import com.engagepoint.msap.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the Deleted entity.
 */
public interface DeletedRepository extends JpaRepository<Deleted,Long> {

    Deleted findOneByMessageAndDeletedBy(Message message, User deletedBy);
}
