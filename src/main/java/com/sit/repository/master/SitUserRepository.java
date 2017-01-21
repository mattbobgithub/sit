package com.sit.repository.master;

import com.sit.domain.SitUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Spring Data JPA repository for the SitUser entity.
 */
@SuppressWarnings("unused")
public interface SitUserRepository extends JpaRepository<SitUser,Long> {

    SitUser findByUsername(String username);

    List<SitUser> findByStoreId(Long storeId);

    List<SitUser> findByWorkroomId(Long workroomId);

}
