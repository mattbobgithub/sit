package com.sit.repository.tenant;

import com.sit.domain.Transfer;

import org.springframework.data.jpa.repository.*;

/**
 * Spring Data JPA repository for the Transfer entity.
 */
@SuppressWarnings("unused")
public interface TransferRepository extends JpaRepository<Transfer,Long> {

}
