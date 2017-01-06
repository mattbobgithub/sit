package com.sit.service.mapper;

import com.sit.domain.Company;
import com.sit.domain.SitUser;
import com.sit.domain.Store;
import com.sit.domain.Workroom;
import com.sit.service.dto.SitUserDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity SitUser and its DTO SitUserDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, })
public interface SitUserMapper {

    @Mapping(source = "company.id", target = "companyId")
    @Mapping(source = "store.id", target = "storeId")
    @Mapping(source = "workroom.id", target = "workroomId")
    @Mapping(source = "user.id", target = "userId")
    SitUserDTO sitUserToSitUserDTO(SitUser sitUser);

    List<SitUserDTO> sitUsersToSitUserDTOs(List<SitUser> sitUsers);

    @Mapping(source = "companyId", target = "company")
    @Mapping(source = "storeId", target = "store")
    @Mapping(source = "workroomId", target = "workroom")
    @Mapping(source = "userId", target = "user")
    SitUser sitUserDTOToSitUser(SitUserDTO sitUserDTO);

    List<SitUser> sitUserDTOsToSitUsers(List<SitUserDTO> sitUserDTOs);

    default Company companyFromId(Long id) {
        if (id == null) {
            return null;
        }
        Company company = new Company();
        company.setId(id);
        return company;
    }

    default Store storeFromId(Long id) {
        if (id == null) {
            return null;
        }
        Store store = new Store();
        store.setId(id);
        return store;
    }

    default Workroom workroomFromId(Long id) {
        if (id == null) {
            return null;
        }
        Workroom workroom = new Workroom();
        workroom.setId(id);
        return workroom;
    }
}
