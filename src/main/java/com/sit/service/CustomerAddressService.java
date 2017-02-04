package com.sit.service;

import com.sit.domain.CustomerAddress;
import com.sit.repository.tenant.CustomerAddressRepository;
import com.sit.service.dto.CustomerAddressDTO;
import com.sit.service.mapper.CustomerAddressMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

/**
 * Service Implementation for managing Customer.
 */
@Service
@Transactional
public class CustomerAddressService {

    private final Logger log = LoggerFactory.getLogger(CustomerService.class);

    @Inject
    private CustomerAddressRepository customerAddressRepository;

    @Inject
    private CustomerAddressMapper customerAddressMapper;


    public CustomerAddressDTO save(CustomerAddressDTO cadto) {
        log.debug("Request to save TicketAction : {}", cadto);
        CustomerAddress customerAddress = customerAddressMapper.customerAddressDTOToCustomerAddress(cadto);
        customerAddress = customerAddressRepository.save(customerAddress);
        CustomerAddressDTO result = customerAddressMapper.customerAddressToCustomerAddressDTO(customerAddress);
        return result;
    }

    /**
     *  Get one customer by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public CustomerAddressDTO findOne(Long id) {
        log.debug("Request to get Customer : {}", id);
        CustomerAddress customerAddress = customerAddressRepository.findOne(id);
        CustomerAddressDTO customerAddressDTO = customerAddressMapper.customerAddressToCustomerAddressDTO(customerAddress);

        return customerAddressDTO;
    }


    /**
     *  Delete the  customer by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Customer : {}", id);
        customerAddressRepository.delete(id);
    }
}
