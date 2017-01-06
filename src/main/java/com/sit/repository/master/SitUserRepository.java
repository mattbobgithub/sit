package com.sit.repository.master;

import com.sit.domain.SitUser;
import org.springframework.data.jpa.repository.JpaRepository;
/**
 * Spring Data JPA repository for the SitUser entity.
 */
@SuppressWarnings("unused")
public interface SitUserRepository extends JpaRepository<SitUser,Long> {

    SitUser findByUsername(String username);

}
