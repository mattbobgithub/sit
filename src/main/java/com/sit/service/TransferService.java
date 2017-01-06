package com.sit.service;

import com.sit.domain.Transfer;
import com.sit.repository.tenant.TransferRepository;
import com.sit.service.dto.TransferDTO;
import com.sit.service.mapper.TransferMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Transfer.
 */
@Service
@Transactional
public class TransferService {

    private final Logger log = LoggerFactory.getLogger(TransferService.class);

    @Inject
    private TransferRepository transferRepository;

    @Inject
    private TransferMapper transferMapper;

    /**
     * Save a transfer.
     *
     * @param transferDTO the entity to save
     * @return the persisted entity
     */
    public TransferDTO save(TransferDTO transferDTO) {
        log.debug("Request to save Transfer : {}", transferDTO);
        Transfer transfer = transferMapper.transferDTOToTransfer(transferDTO);
        transfer = transferRepository.save(transfer);
        TransferDTO result = transferMapper.transferToTransferDTO(transfer);
        return result;
    }

    /**
     *  Get all the transfers.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<TransferDTO> findAll() {
        log.debug("Request to get all Transfers");
        List<TransferDTO> result = transferRepository.findAll().stream()
            .map(transferMapper::transferToTransferDTO)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     *  Get one transfer by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public TransferDTO findOne(Long id) {
        log.debug("Request to get Transfer : {}", id);
        Transfer transfer = transferRepository.findOne(id);
        TransferDTO transferDTO = transferMapper.transferToTransferDTO(transfer);
        return transferDTO;
    }

    /**
     *  Delete the  transfer by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Transfer : {}", id);
        transferRepository.delete(id);
    }
}
