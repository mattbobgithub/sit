package com.sit.web.rest;

import com.sit.SitApp;

import com.sit.domain.Alteration;
import com.sit.repository.tenant.AlterationRepository;
import com.sit.service.AlterationService;
import com.sit.service.dto.AlterationDTO;
import com.sit.service.mapper.AlterationMapper;

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

import com.sit.domain.enumeration.Gender;
/**
 * Test class for the AlterationResource REST controller.
 *
 * @see AlterationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SitApp.class)
public class AlterationResourceIntTest {

    private static final Gender DEFAULT_GENDER = Gender.MENS;
    private static final Gender UPDATED_GENDER = Gender.WOMENS;

    private static final Boolean DEFAULT_ACTIVE_STATUS = false;
    private static final Boolean UPDATED_ACTIVE_STATUS = true;

    private static final String DEFAULT_SHORT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_SHORT_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_LONG_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_LONG_DESCRIPTION = "BBBBBBBBBB";

    private static final Integer DEFAULT_ESTIMATED_TIME = 1;
    private static final Integer UPDATED_ESTIMATED_TIME = 2;

    private static final Double DEFAULT_MEASUREMENT_1 = 1D;
    private static final Double UPDATED_MEASUREMENT_1 = 2D;

    private static final Double DEFAULT_MEASUREMENT_2 = 1D;
    private static final Double UPDATED_MEASUREMENT_2 = 2D;

    private static final Integer DEFAULT_QUANTITY = 1;
    private static final Integer UPDATED_QUANTITY = 2;

    private static final Boolean DEFAULT_SHORT_LIST_IND = false;
    private static final Boolean UPDATED_SHORT_LIST_IND = true;

    private static final Boolean DEFAULT_AUTO_DEFAULT_IND = false;
    private static final Boolean UPDATED_AUTO_DEFAULT_IND = true;

    private static final Integer DEFAULT_GROUP_ORDER_NUM = 1;
    private static final Integer UPDATED_GROUP_ORDER_NUM = 2;

    @Inject
    private AlterationRepository alterationRepository;

    @Inject
    private AlterationMapper alterationMapper;

    @Inject
    private AlterationService alterationService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restAlterationMockMvc;

    private Alteration alteration;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        AlterationResource alterationResource = new AlterationResource();
        ReflectionTestUtils.setField(alterationResource, "alterationService", alterationService);
        this.restAlterationMockMvc = MockMvcBuilders.standaloneSetup(alterationResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Alteration createEntity(EntityManager em) {
        Alteration alteration = new Alteration()
                .gender(DEFAULT_GENDER)
                .activeStatus(DEFAULT_ACTIVE_STATUS)
                .shortDescription(DEFAULT_SHORT_DESCRIPTION)
                .longDescription(DEFAULT_LONG_DESCRIPTION)
                .estimatedTime(DEFAULT_ESTIMATED_TIME)
                .measurement1(DEFAULT_MEASUREMENT_1)
                .measurement2(DEFAULT_MEASUREMENT_2)
                .quantity(DEFAULT_QUANTITY)
                .shortListInd(DEFAULT_SHORT_LIST_IND)
                .autoDefaultInd(DEFAULT_AUTO_DEFAULT_IND)
                .groupOrderNum(DEFAULT_GROUP_ORDER_NUM);
        return alteration;
    }

    @Before
    public void initTest() {
        alteration = createEntity(em);
    }

    @Test
    @Transactional
    public void createAlteration() throws Exception {
        int databaseSizeBeforeCreate = alterationRepository.findAll().size();

        // Create the Alteration
        AlterationDTO alterationDTO = alterationMapper.alterationToAlterationDTO(alteration);

        restAlterationMockMvc.perform(post("/api/alterations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(alterationDTO)))
            .andExpect(status().isCreated());

        // Validate the Alteration in the database
        List<Alteration> alterationList = alterationRepository.findAll();
        assertThat(alterationList).hasSize(databaseSizeBeforeCreate + 1);
        Alteration testAlteration = alterationList.get(alterationList.size() - 1);
        assertThat(testAlteration.getGender()).isEqualTo(DEFAULT_GENDER);
        assertThat(testAlteration.isActiveStatus()).isEqualTo(DEFAULT_ACTIVE_STATUS);
        assertThat(testAlteration.getShortDescription()).isEqualTo(DEFAULT_SHORT_DESCRIPTION);
        assertThat(testAlteration.getLongDescription()).isEqualTo(DEFAULT_LONG_DESCRIPTION);
        assertThat(testAlteration.getEstimatedTime()).isEqualTo(DEFAULT_ESTIMATED_TIME);
        assertThat(testAlteration.getMeasurement1()).isEqualTo(DEFAULT_MEASUREMENT_1);
        assertThat(testAlteration.getMeasurement2()).isEqualTo(DEFAULT_MEASUREMENT_2);
        assertThat(testAlteration.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testAlteration.isShortListInd()).isEqualTo(DEFAULT_SHORT_LIST_IND);
        assertThat(testAlteration.isAutoDefaultInd()).isEqualTo(DEFAULT_AUTO_DEFAULT_IND);
        assertThat(testAlteration.getGroupOrderNum()).isEqualTo(DEFAULT_GROUP_ORDER_NUM);
    }

    @Test
    @Transactional
    public void createAlterationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = alterationRepository.findAll().size();

        // Create the Alteration with an existing ID
        Alteration existingAlteration = new Alteration();
        existingAlteration.setId(1L);
        AlterationDTO existingAlterationDTO = alterationMapper.alterationToAlterationDTO(existingAlteration);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAlterationMockMvc.perform(post("/api/alterations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingAlterationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Alteration> alterationList = alterationRepository.findAll();
        assertThat(alterationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkShortDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = alterationRepository.findAll().size();
        // set the field null
        alteration.setShortDescription(null);

        // Create the Alteration, which fails.
        AlterationDTO alterationDTO = alterationMapper.alterationToAlterationDTO(alteration);

        restAlterationMockMvc.perform(post("/api/alterations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(alterationDTO)))
            .andExpect(status().isBadRequest());

        List<Alteration> alterationList = alterationRepository.findAll();
        assertThat(alterationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAlterations() throws Exception {
        // Initialize the database
        alterationRepository.saveAndFlush(alteration);

        // Get all the alterationList
        restAlterationMockMvc.perform(get("/api/alterations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(alteration.getId().intValue())))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER.toString())))
            .andExpect(jsonPath("$.[*].activeStatus").value(hasItem(DEFAULT_ACTIVE_STATUS.booleanValue())))
            .andExpect(jsonPath("$.[*].shortDescription").value(hasItem(DEFAULT_SHORT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].longDescription").value(hasItem(DEFAULT_LONG_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].estimatedTime").value(hasItem(DEFAULT_ESTIMATED_TIME)))
            .andExpect(jsonPath("$.[*].measurement1").value(hasItem(DEFAULT_MEASUREMENT_1.doubleValue())))
            .andExpect(jsonPath("$.[*].measurement2").value(hasItem(DEFAULT_MEASUREMENT_2.doubleValue())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY)))
            .andExpect(jsonPath("$.[*].shortListInd").value(hasItem(DEFAULT_SHORT_LIST_IND.booleanValue())))
            .andExpect(jsonPath("$.[*].autoDefaultInd").value(hasItem(DEFAULT_AUTO_DEFAULT_IND.booleanValue())))
            .andExpect(jsonPath("$.[*].groupOrderNum").value(hasItem(DEFAULT_GROUP_ORDER_NUM)));
    }

    @Test
    @Transactional
    public void getAlteration() throws Exception {
        // Initialize the database
        alterationRepository.saveAndFlush(alteration);

        // Get the alteration
        restAlterationMockMvc.perform(get("/api/alterations/{id}", alteration.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(alteration.getId().intValue()))
            .andExpect(jsonPath("$.gender").value(DEFAULT_GENDER.toString()))
            .andExpect(jsonPath("$.activeStatus").value(DEFAULT_ACTIVE_STATUS.booleanValue()))
            .andExpect(jsonPath("$.shortDescription").value(DEFAULT_SHORT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.longDescription").value(DEFAULT_LONG_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.estimatedTime").value(DEFAULT_ESTIMATED_TIME))
            .andExpect(jsonPath("$.measurement1").value(DEFAULT_MEASUREMENT_1.doubleValue()))
            .andExpect(jsonPath("$.measurement2").value(DEFAULT_MEASUREMENT_2.doubleValue()))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY))
            .andExpect(jsonPath("$.shortListInd").value(DEFAULT_SHORT_LIST_IND.booleanValue()))
            .andExpect(jsonPath("$.autoDefaultInd").value(DEFAULT_AUTO_DEFAULT_IND.booleanValue()))
            .andExpect(jsonPath("$.groupOrderNum").value(DEFAULT_GROUP_ORDER_NUM));
    }

    @Test
    @Transactional
    public void getNonExistingAlteration() throws Exception {
        // Get the alteration
        restAlterationMockMvc.perform(get("/api/alterations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAlteration() throws Exception {
        // Initialize the database
        alterationRepository.saveAndFlush(alteration);
        int databaseSizeBeforeUpdate = alterationRepository.findAll().size();

        // Update the alteration
        Alteration updatedAlteration = alterationRepository.findOne(alteration.getId());
        updatedAlteration
                .gender(UPDATED_GENDER)
                .activeStatus(UPDATED_ACTIVE_STATUS)
                .shortDescription(UPDATED_SHORT_DESCRIPTION)
                .longDescription(UPDATED_LONG_DESCRIPTION)
                .estimatedTime(UPDATED_ESTIMATED_TIME)
                .measurement1(UPDATED_MEASUREMENT_1)
                .measurement2(UPDATED_MEASUREMENT_2)
                .quantity(UPDATED_QUANTITY)
                .shortListInd(UPDATED_SHORT_LIST_IND)
                .autoDefaultInd(UPDATED_AUTO_DEFAULT_IND)
                .groupOrderNum(UPDATED_GROUP_ORDER_NUM);
        AlterationDTO alterationDTO = alterationMapper.alterationToAlterationDTO(updatedAlteration);

        restAlterationMockMvc.perform(put("/api/alterations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(alterationDTO)))
            .andExpect(status().isOk());

        // Validate the Alteration in the database
        List<Alteration> alterationList = alterationRepository.findAll();
        assertThat(alterationList).hasSize(databaseSizeBeforeUpdate);
        Alteration testAlteration = alterationList.get(alterationList.size() - 1);
        assertThat(testAlteration.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testAlteration.isActiveStatus()).isEqualTo(UPDATED_ACTIVE_STATUS);
        assertThat(testAlteration.getShortDescription()).isEqualTo(UPDATED_SHORT_DESCRIPTION);
        assertThat(testAlteration.getLongDescription()).isEqualTo(UPDATED_LONG_DESCRIPTION);
        assertThat(testAlteration.getEstimatedTime()).isEqualTo(UPDATED_ESTIMATED_TIME);
        assertThat(testAlteration.getMeasurement1()).isEqualTo(UPDATED_MEASUREMENT_1);
        assertThat(testAlteration.getMeasurement2()).isEqualTo(UPDATED_MEASUREMENT_2);
        assertThat(testAlteration.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testAlteration.isShortListInd()).isEqualTo(UPDATED_SHORT_LIST_IND);
        assertThat(testAlteration.isAutoDefaultInd()).isEqualTo(UPDATED_AUTO_DEFAULT_IND);
        assertThat(testAlteration.getGroupOrderNum()).isEqualTo(UPDATED_GROUP_ORDER_NUM);
    }

    @Test
    @Transactional
    public void updateNonExistingAlteration() throws Exception {
        int databaseSizeBeforeUpdate = alterationRepository.findAll().size();

        // Create the Alteration
        AlterationDTO alterationDTO = alterationMapper.alterationToAlterationDTO(alteration);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAlterationMockMvc.perform(put("/api/alterations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(alterationDTO)))
            .andExpect(status().isCreated());

        // Validate the Alteration in the database
        List<Alteration> alterationList = alterationRepository.findAll();
        assertThat(alterationList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteAlteration() throws Exception {
        // Initialize the database
        alterationRepository.saveAndFlush(alteration);
        int databaseSizeBeforeDelete = alterationRepository.findAll().size();

        // Get the alteration
        restAlterationMockMvc.perform(delete("/api/alterations/{id}", alteration.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Alteration> alterationList = alterationRepository.findAll();
        assertThat(alterationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
