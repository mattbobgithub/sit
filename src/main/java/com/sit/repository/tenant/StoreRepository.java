package com.sit.repository.tenant;

import com.sit.domain.Store;

import org.springframework.data.jpa.repository.*;

/**
 * Spring Data JPA repository for the Store entity.
 */
@SuppressWarnings("unused")
public interface StoreRepository extends JpaRepository<Store,Long> {

}
