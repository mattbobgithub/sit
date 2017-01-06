package com.sit.service.mapper;

import com.sit.domain.Store;
import com.sit.domain.Workroom;
import com.sit.service.dto.StoreDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Store and its DTO StoreDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface StoreMapper {

    @Mapping(source = "workroom.id", target = "workroomId")
    StoreDTO storeToStoreDTO(Store store);

    List<StoreDTO> storesToStoreDTOs(List<Store> stores);

    @Mapping(target = "storeUsers", ignore = true)
    @Mapping(source = "workroomId", target = "workroom")
    Store storeDTOToStore(StoreDTO storeDTO);

    List<Store> storeDTOsToStores(List<StoreDTO> storeDTOs);

    default Workroom workroomFromId(Long id) {
        if (id == null) {
            return null;
        }
        Workroom workroom = new Workroom();
        workroom.setId(id);
        return workroom;
    }
}
