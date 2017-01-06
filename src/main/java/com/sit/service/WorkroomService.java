package com.sit.service;

import com.sit.domain.Workroom;
import com.sit.repository.tenant.WorkroomRepository;
import com.sit.service.dto.WorkroomDTO;
import com.sit.service.mapper.WorkroomMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Workroom.
 */
@Service
@Transactional
public class WorkroomService {

    private final Logger log = LoggerFactory.getLogger(WorkroomService.class);

    @Inject
    private WorkroomRepository workroomRepository;

    @Inject
    private WorkroomMapper workroomMapper;

    /**
     * Save a workroom.
     *
     * @param workroomDTO the entity to save
     * @return the persisted entity
     */
    public WorkroomDTO save(WorkroomDTO workroomDTO) {
        log.debug("Request to save Workroom : {}", workroomDTO);
        Workroom workroom = workroomMapper.workroomDTOToWorkroom(workroomDTO);
        workroom = workroomRepository.save(workroom);
        WorkroomDTO result = workroomMapper.workroomToWorkroomDTO(workroom);
        return result;
    }

    /**
     *  Get all the workrooms.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<WorkroomDTO> findAll() {
        log.debug("Request to get all Workrooms");
        List<WorkroomDTO> result = workroomRepository.findAll().stream()
            .map(workroomMapper::workroomToWorkroomDTO)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     *  Get one workroom by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public WorkroomDTO findOne(Long id) {
        log.debug("Request to get Workroom : {}", id);
        Workroom workroom = workroomRepository.findOne(id);
        WorkroomDTO workroomDTO = workroomMapper.workroomToWorkroomDTO(workroom);
        return workroomDTO;
    }

    /**
     *  Delete the  workroom by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Workroom : {}", id);
        workroomRepository.delete(id);
    }
}
