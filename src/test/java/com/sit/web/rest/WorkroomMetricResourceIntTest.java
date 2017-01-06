package com.sit.web.rest;

import com.sit.SitApp;

import com.sit.domain.WorkroomMetric;
import com.sit.repository.tenant.WorkroomMetricRepository;
import com.sit.service.WorkroomMetricService;
import com.sit.service.dto.WorkroomMetricDTO;
import com.sit.service.mapper.WorkroomMetricMapper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the WorkroomMetricResource REST controller.
 *
 * @see WorkroomMetricResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SitApp.class)
public class WorkroomMetricResourceIntTest {

    private static final Integer DEFAULT_DEFAULT_FITTING_TIME_MINS = 1;
    private static final Integer UPDATED_DEFAULT_FITTING_TIME_MINS = 2;

    private static final Double DEFAULT_EFFICIENCY_PERCENTAGE = 1D;
    private static final Double UPDATED_EFFICIENCY_PERCENTAGE = 2D;

    private static final Double DEFAULT_UTILIZATION_PERCENTAGE = 1D;
    private static final Double UPDATED_UTILIZATION_PERCENTAGE = 2D;

    @Inject
    private WorkroomMetricRepository workroomMetricRepository;

    @Inject
    private WorkroomMetricMapper workroomMetricMapper;

    @Inject
    private WorkroomMetricService workroomMetricService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restWorkroomMetricMockMvc;

    private WorkroomMetric workroomMetric;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        WorkroomMetricResource workroomMetricResource = new WorkroomMetricResource();
        ReflectionTestUtils.setField(workroomMetricResource, "workroomMetricService", workroomMetricService);
        this.restWorkroomMetricMockMvc = MockMvcBuilders.standaloneSetup(workroomMetricResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WorkroomMetric createEntity(EntityManager em) {
        WorkroomMetric workroomMetric = new WorkroomMetric()
                .defaultFittingTimeMins(DEFAULT_DEFAULT_FITTING_TIME_MINS)
                .efficiencyPercentage(DEFAULT_EFFICIENCY_PERCENTAGE)
                .utilizationPercentage(DEFAULT_UTILIZATION_PERCENTAGE);
        return workroomMetric;
    }

    @Before
    public void initTest() {
        workroomMetric = createEntity(em);
    }

    @Test
    @Transactional
    public void createWorkroomMetric() throws Exception {
        int databaseSizeBeforeCreate = workroomMetricRepository.findAll().size();

        // Create the WorkroomMetric
        WorkroomMetricDTO workroomMetricDTO = workroomMetricMapper.workroomMetricToWorkroomMetricDTO(workroomMetric);

        restWorkroomMetricMockMvc.perform(post("/api/workroom-metrics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(workroomMetricDTO)))
            .andExpect(status().isCreated());

        // Validate the WorkroomMetric in the database
        List<WorkroomMetric> workroomMetricList = workroomMetricRepository.findAll();
        assertThat(workroomMetricList).hasSize(databaseSizeBeforeCreate + 1);
        WorkroomMetric testWorkroomMetric = workroomMetricList.get(workroomMetricList.size() - 1);
        assertThat(testWorkroomMetric.getDefaultFittingTimeMins()).isEqualTo(DEFAULT_DEFAULT_FITTING_TIME_MINS);
        assertThat(testWorkroomMetric.getEfficiencyPercentage()).isEqualTo(DEFAULT_EFFICIENCY_PERCENTAGE);
        assertThat(testWorkroomMetric.getUtilizationPercentage()).isEqualTo(DEFAULT_UTILIZATION_PERCENTAGE);
    }

    @Test
    @Transactional
    public void createWorkroomMetricWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = workroomMetricRepository.findAll().size();

        // Create the WorkroomMetric with an existing ID
        WorkroomMetric existingWorkroomMetric = new WorkroomMetric();
        existingWorkroomMetric.setId(1L);
        WorkroomMetricDTO existingWorkroomMetricDTO = workroomMetricMapper.workroomMetricToWorkroomMetricDTO(existingWorkroomMetric);

        // An entity with an existing ID cannot be created, so this API call must fail
        restWorkroomMetricMockMvc.perform(post("/api/workroom-metrics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingWorkroomMetricDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<WorkroomMetric> workroomMetricList = workroomMetricRepository.findAll();
        assertThat(workroomMetricList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllWorkroomMetrics() throws Exception {
        // Initialize the database
        workroomMetricRepository.saveAndFlush(workroomMetric);

        // Get all the workroomMetricList
        restWorkroomMetricMockMvc.perform(get("/api/workroom-metrics?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(workroomMetric.getId().intValue())))
            .andExpect(jsonPath("$.[*].defaultFittingTimeMins").value(hasItem(DEFAULT_DEFAULT_FITTING_TIME_MINS)))
            .andExpect(jsonPath("$.[*].efficiencyPercentage").value(hasItem(DEFAULT_EFFICIENCY_PERCENTAGE.doubleValue())))
            .andExpect(jsonPath("$.[*].utilizationPercentage").value(hasItem(DEFAULT_UTILIZATION_PERCENTAGE.doubleValue())));
    }

    @Test
    @Transactional
    public void getWorkroomMetric() throws Exception {
        // Initialize the database
        workroomMetricRepository.saveAndFlush(workroomMetric);

        // Get the workroomMetric
        restWorkroomMetricMockMvc.perform(get("/api/workroom-metrics/{id}", workroomMetric.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(workroomMetric.getId().intValue()))
            .andExpect(jsonPath("$.defaultFittingTimeMins").value(DEFAULT_DEFAULT_FITTING_TIME_MINS))
            .andExpect(jsonPath("$.efficiencyPercentage").value(DEFAULT_EFFICIENCY_PERCENTAGE.doubleValue()))
            .andExpect(jsonPath("$.utilizationPercentage").value(DEFAULT_UTILIZATION_PERCENTAGE.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingWorkroomMetric() throws Exception {
        // Get the workroomMetric
        restWorkroomMetricMockMvc.perform(get("/api/workroom-metrics/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateWorkroomMetric() throws Exception {
        // Initialize the database
        workroomMetricRepository.saveAndFlush(workroomMetric);
        int databaseSizeBeforeUpdate = workroomMetricRepository.findAll().size();

        // Update the workroomMetric
        WorkroomMetric updatedWorkroomMetric = workroomMetricRepository.findOne(workroomMetric.getId());
        updatedWorkroomMetric
                .defaultFittingTimeMins(UPDATED_DEFAULT_FITTING_TIME_MINS)
                .efficiencyPercentage(UPDATED_EFFICIENCY_PERCENTAGE)
                .utilizationPercentage(UPDATED_UTILIZATION_PERCENTAGE);
        WorkroomMetricDTO workroomMetricDTO = workroomMetricMapper.workroomMetricToWorkroomMetricDTO(updatedWorkroomMetric);

        restWorkroomMetricMockMvc.perform(put("/api/workroom-metrics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(workroomMetricDTO)))
            .andExpect(status().isOk());

        // Validate the WorkroomMetric in the database
        List<WorkroomMetric> workroomMetricList = workroomMetricRepository.findAll();
        assertThat(workroomMetricList).hasSize(databaseSizeBeforeUpdate);
        WorkroomMetric testWorkroomMetric = workroomMetricList.get(workroomMetricList.size() - 1);
        assertThat(testWorkroomMetric.getDefaultFittingTimeMins()).isEqualTo(UPDATED_DEFAULT_FITTING_TIME_MINS);
        assertThat(testWorkroomMetric.getEfficiencyPercentage()).isEqualTo(UPDATED_EFFICIENCY_PERCENTAGE);
        assertThat(testWorkroomMetric.getUtilizationPercentage()).isEqualTo(UPDATED_UTILIZATION_PERCENTAGE);
    }

    @Test
    @Transactional
    public void updateNonExistingWorkroomMetric() throws Exception {
        int databaseSizeBeforeUpdate = workroomMetricRepository.findAll().size();

        // Create the WorkroomMetric
        WorkroomMetricDTO workroomMetricDTO = workroomMetricMapper.workroomMetricToWorkroomMetricDTO(workroomMetric);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restWorkroomMetricMockMvc.perform(put("/api/workroom-metrics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(workroomMetricDTO)))
            .andExpect(status().isCreated());

        // Validate the WorkroomMetric in the database
        List<WorkroomMetric> workroomMetricList = workroomMetricRepository.findAll();
        assertThat(workroomMetricList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteWorkroomMetric() throws Exception {
        // Initialize the database
        workroomMetricRepository.saveAndFlush(workroomMetric);
        int databaseSizeBeforeDelete = workroomMetricRepository.findAll().size();

        // Get the workroomMetric
        restWorkroomMetricMockMvc.perform(delete("/api/workroom-metrics/{id}", workroomMetric.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<WorkroomMetric> workroomMetricList = workroomMetricRepository.findAll();
        assertThat(workroomMetricList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
