package com.sit.service;

import com.sit.domain.WorkroomMetric;
import com.sit.repository.tenant.WorkroomMetricRepository;
import com.sit.service.dto.WorkroomMetricDTO;
import com.sit.service.mapper.WorkroomMetricMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing WorkroomMetric.
 */
@Service
@Transactional
public class WorkroomMetricService {

    private final Logger log = LoggerFactory.getLogger(WorkroomMetricService.class);

    @Inject
    private WorkroomMetricRepository workroomMetricRepository;

    @Inject
    private WorkroomMetricMapper workroomMetricMapper;

    /**
     * Save a workroomMetric.
     *
     * @param workroomMetricDTO the entity to save
     * @return the persisted entity
     */
    public WorkroomMetricDTO save(WorkroomMetricDTO workroomMetricDTO) {
        log.debug("Request to save WorkroomMetric : {}", workroomMetricDTO);
        WorkroomMetric workroomMetric = workroomMetricMapper.workroomMetricDTOToWorkroomMetric(workroomMetricDTO);
        workroomMetric = workroomMetricRepository.save(workroomMetric);
        WorkroomMetricDTO result = workroomMetricMapper.workroomMetricToWorkroomMetricDTO(workroomMetric);
        return result;
    }

    /**
     *  Get all the workroomMetrics.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<WorkroomMetricDTO> findAll() {
        log.debug("Request to get all WorkroomMetrics");
        List<WorkroomMetricDTO> result = workroomMetricRepository.findAll().stream()
            .map(workroomMetricMapper::workroomMetricToWorkroomMetricDTO)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     *  Get one workroomMetric by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public WorkroomMetricDTO findOne(Long id) {
        log.debug("Request to get WorkroomMetric : {}", id);
        WorkroomMetric workroomMetric = workroomMetricRepository.findOne(id);
        WorkroomMetricDTO workroomMetricDTO = workroomMetricMapper.workroomMetricToWorkroomMetricDTO(workroomMetric);
        return workroomMetricDTO;
    }

    /**
     *  Delete the  workroomMetric by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete WorkroomMetric : {}", id);
        workroomMetricRepository.delete(id);
    }
}
