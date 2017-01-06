package com.sit.service.mapper;

import com.sit.domain.Transfer;
import com.sit.service.dto.TransferDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Transfer and its DTO TransferDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TransferMapper {

    TransferDTO transferToTransferDTO(Transfer transfer);

    List<TransferDTO> transfersToTransferDTOs(List<Transfer> transfers);

    @Mapping(target = "tickets", ignore = true)
    Transfer transferDTOToTransfer(TransferDTO transferDTO);

    List<Transfer> transferDTOsToTransfers(List<TransferDTO> transferDTOs);
}
