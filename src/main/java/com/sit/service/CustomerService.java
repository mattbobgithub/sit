package com.sit.service;

import com.sit.domain.Customer;
import com.sit.repository.tenant.CustomerRepository;
import com.sit.service.dto.CustomerDTO;
import com.sit.service.mapper.CustomerMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Customer.
 */
@Service
@Transactional
public class CustomerService {

    private final Logger log = LoggerFactory.getLogger(CustomerService.class);

    @Inject
    private CustomerRepository customerRepository;

    @Inject
    private CustomerMapper customerMapper;

    /**
     * Save a customer.
     *
     * @param customerDTO the entity to save
     * @return the persisted entity
     */
    public CustomerDTO save(CustomerDTO customerDTO) {
        log.debug("Request to save Customer : {}", customerDTO);
        Customer customer = customerMapper.customerDTOToCustomer(customerDTO);
        customer = customerRepository.save(customer);
        CustomerDTO result = customerMapper.customerToCustomerDTO(customer);
        return result;
    }

    /**
     *  Get all the customers.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<CustomerDTO> findAll() {
        log.debug("Request to get all Customers");
        List<CustomerDTO> result = customerRepository.findAll().stream()
            .map(customerMapper::customerToCustomerDTO)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     *  Get one customer by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public CustomerDTO findOne(Long id) {
        log.debug("Request to get Customer : {}", id);
        Customer customer = customerRepository.findOne(id);
        CustomerDTO customerDTO = customerMapper.customerToCustomerDTO(customer);

        return customerDTO;
    }

    @Transactional(readOnly = true)
    public Customer findOneDomain(Long id) {
        log.debug("Request to get Customer Domain Obj : {}", id);
        Customer customer = customerRepository.findOne(id);

        return customer;
    }

    /**
     *  Delete the  customer by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Customer : {}", id);
        customerRepository.delete(id);
    }
}
