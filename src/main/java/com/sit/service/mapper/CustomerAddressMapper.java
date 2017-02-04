package com.sit.service.mapper;

import com.sit.domain.Customer;
import com.sit.domain.CustomerAddress;
import com.sit.service.dto.CustomerAddressDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * Mapper for the entity Customer and its DTO CustomerDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CustomerAddressMapper {

    @Mapping(source = "customer.id", target = "customerId")
    CustomerAddressDTO customerAddressToCustomerAddressDTO(CustomerAddress customerAddress);
    List<CustomerAddressDTO> customerAddressesToCustomerAddressDTOs(List<CustomerAddress> customerAddresses);


    @Mapping(source = "customerId", target = "customer")
    CustomerAddress customerAddressDTOToCustomerAddress(CustomerAddressDTO customerAddressDTO);
    List<CustomerAddress> customerAddressDTOsToCustomerAddresses(List<CustomerAddressDTO> customerAddressDTOs);

    default Customer customerFromId(Long id) {
        if (id == null) {
            return null;
        }
        Customer customer = new Customer();
        customer.setId(id);
        return customer;
    }



}
