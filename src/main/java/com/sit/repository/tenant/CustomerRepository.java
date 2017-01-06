package com.sit.repository.tenant;

import com.sit.domain.Customer;

import org.springframework.data.jpa.repository.*;

/**
 * Spring Data JPA repository for the Customer entity.
 */
@SuppressWarnings("unused")
public interface CustomerRepository extends JpaRepository<Customer,Long> {

}
