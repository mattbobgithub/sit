package com.sit.web.rest;

import com.sit.SitApp;

import com.sit.domain.TicketAlteration;
import com.sit.repository.tenant.TicketAlterationRepository;
import com.sit.service.TicketAlterationService;
import com.sit.service.dto.TicketAlterationDTO;
import com.sit.service.mapper.TicketAlterationMapper;

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
 * Test class for the TicketAlterationResource REST controller.
 *
 * @see TicketAlterationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SitApp.class)
public class TicketAlterationResourceIntTest {

    private static final Integer DEFAULT_LINE_NUMBER = 1;
    private static final Integer UPDATED_LINE_NUMBER = 2;

    private static final Gender DEFAULT_GENDER = Gender.MENS;
    private static final Gender UPDATED_GENDER = Gender.WOMENS;

    private static final Long DEFAULT_ALT_ID = 1L;
    private static final Long UPDATED_ALT_ID = 2L;

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_TAG_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TAG_TYPE = "BBBBBBBBBB";

    private static final Integer DEFAULT_ESTIMATE_TIME = 1;
    private static final Integer UPDATED_ESTIMATE_TIME = 2;

    private static final Double DEFAULT_PRICE = 1D;
    private static final Double UPDATED_PRICE = 2D;

    private static final Double DEFAULT_MEASUREMENT_1 = 1D;
    private static final Double UPDATED_MEASUREMENT_1 = 2D;

    private static final Double DEFAULT_MEASUREMENT_2 = 1D;
    private static final Double UPDATED_MEASUREMENT_2 = 2D;

    private static final Integer DEFAULT_QUANTITY = 1;
    private static final Integer UPDATED_QUANTITY = 2;

    private static final Boolean DEFAULT_COMPLETE_INDICATOR = false;
    private static final Boolean UPDATED_COMPLETE_INDICATOR = true;

    @Inject
    private TicketAlterationRepository ticketAlterationRepository;

    @Inject
    private TicketAlterationMapper ticketAlterationMapper;

    @Inject
    private TicketAlterationService ticketAlterationService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restTicketAlterationMockMvc;

    private TicketAlteration ticketAlteration;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TicketAlterationResource ticketAlterationResource = new TicketAlterationResource();
        ReflectionTestUtils.setField(ticketAlterationResource, "ticketAlterationService", ticketAlterationService);
        this.restTicketAlterationMockMvc = MockMvcBuilders.standaloneSetup(ticketAlterationResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TicketAlteration createEntity(EntityManager em) {
        TicketAlteration ticketAlteration = new TicketAlteration()
                .lineNumber(DEFAULT_LINE_NUMBER)
                .gender(DEFAULT_GENDER)
                .altId(DEFAULT_ALT_ID)
                .description(DEFAULT_DESCRIPTION)
                .tagType(DEFAULT_TAG_TYPE)
                .estimateTime(DEFAULT_ESTIMATE_TIME)
                .price(DEFAULT_PRICE)
                .measurement1(DEFAULT_MEASUREMENT_1)
                .measurement2(DEFAULT_MEASUREMENT_2)
                .quantity(DEFAULT_QUANTITY)
                .completeIndicator(DEFAULT_COMPLETE_INDICATOR);
        return ticketAlteration;
    }

    @Before
    public void initTest() {
        ticketAlteration = createEntity(em);
    }

    @Test
    @Transactional
    public void createTicketAlteration() throws Exception {
        int databaseSizeBeforeCreate = ticketAlterationRepository.findAll().size();

        // Create the TicketAlteration
        TicketAlterationDTO ticketAlterationDTO = ticketAlterationMapper.ticketAlterationToTicketAlterationDTO(ticketAlteration);

        restTicketAlterationMockMvc.perform(post("/api/ticket-alterations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ticketAlterationDTO)))
            .andExpect(status().isCreated());

        // Validate the TicketAlteration in the database
        List<TicketAlteration> ticketAlterationList = ticketAlterationRepository.findAll();
        assertThat(ticketAlterationList).hasSize(databaseSizeBeforeCreate + 1);
        TicketAlteration testTicketAlteration = ticketAlterationList.get(ticketAlterationList.size() - 1);
        assertThat(testTicketAlteration.getLineNumber()).isEqualTo(DEFAULT_LINE_NUMBER);
        assertThat(testTicketAlteration.getGender()).isEqualTo(DEFAULT_GENDER);
        assertThat(testTicketAlteration.getAltId()).isEqualTo(DEFAULT_ALT_ID);
        assertThat(testTicketAlteration.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testTicketAlteration.getTagType()).isEqualTo(DEFAULT_TAG_TYPE);
        assertThat(testTicketAlteration.getEstimateTime()).isEqualTo(DEFAULT_ESTIMATE_TIME);
        assertThat(testTicketAlteration.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testTicketAlteration.getMeasurement1()).isEqualTo(DEFAULT_MEASUREMENT_1);
        assertThat(testTicketAlteration.getMeasurement2()).isEqualTo(DEFAULT_MEASUREMENT_2);
        assertThat(testTicketAlteration.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testTicketAlteration.isCompleteIndicator()).isEqualTo(DEFAULT_COMPLETE_INDICATOR);
    }

    @Test
    @Transactional
    public void createTicketAlterationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = ticketAlterationRepository.findAll().size();

        // Create the TicketAlteration with an existing ID
        TicketAlteration existingTicketAlteration = new TicketAlteration();
        existingTicketAlteration.setId(1L);
        TicketAlterationDTO existingTicketAlterationDTO = ticketAlterationMapper.ticketAlterationToTicketAlterationDTO(existingTicketAlteration);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTicketAlterationMockMvc.perform(post("/api/ticket-alterations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingTicketAlterationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<TicketAlteration> ticketAlterationList = ticketAlterationRepository.findAll();
        assertThat(ticketAlterationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllTicketAlterations() throws Exception {
        // Initialize the database
        ticketAlterationRepository.saveAndFlush(ticketAlteration);

        // Get all the ticketAlterationList
        restTicketAlterationMockMvc.perform(get("/api/ticket-alterations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ticketAlteration.getId().intValue())))
            .andExpect(jsonPath("$.[*].lineNumber").value(hasItem(DEFAULT_LINE_NUMBER)))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER.toString())))
            .andExpect(jsonPath("$.[*].altId").value(hasItem(DEFAULT_ALT_ID.intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].tagType").value(hasItem(DEFAULT_TAG_TYPE.toString())))
            .andExpect(jsonPath("$.[*].estimateTime").value(hasItem(DEFAULT_ESTIMATE_TIME)))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].measurement1").value(hasItem(DEFAULT_MEASUREMENT_1.doubleValue())))
            .andExpect(jsonPath("$.[*].measurement2").value(hasItem(DEFAULT_MEASUREMENT_2.doubleValue())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY)))
            .andExpect(jsonPath("$.[*].completeIndicator").value(hasItem(DEFAULT_COMPLETE_INDICATOR.booleanValue())));
    }

    @Test
    @Transactional
    public void getTicketAlteration() throws Exception {
        // Initialize the database
        ticketAlterationRepository.saveAndFlush(ticketAlteration);

        // Get the ticketAlteration
        restTicketAlterationMockMvc.perform(get("/api/ticket-alterations/{id}", ticketAlteration.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(ticketAlteration.getId().intValue()))
            .andExpect(jsonPath("$.lineNumber").value(DEFAULT_LINE_NUMBER))
            .andExpect(jsonPath("$.gender").value(DEFAULT_GENDER.toString()))
            .andExpect(jsonPath("$.altId").value(DEFAULT_ALT_ID.intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.tagType").value(DEFAULT_TAG_TYPE.toString()))
            .andExpect(jsonPath("$.estimateTime").value(DEFAULT_ESTIMATE_TIME))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.doubleValue()))
            .andExpect(jsonPath("$.measurement1").value(DEFAULT_MEASUREMENT_1.doubleValue()))
            .andExpect(jsonPath("$.measurement2").value(DEFAULT_MEASUREMENT_2.doubleValue()))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY))
            .andExpect(jsonPath("$.completeIndicator").value(DEFAULT_COMPLETE_INDICATOR.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTicketAlteration() throws Exception {
        // Get the ticketAlteration
        restTicketAlterationMockMvc.perform(get("/api/ticket-alterations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTicketAlteration() throws Exception {
        // Initialize the database
        ticketAlterationRepository.saveAndFlush(ticketAlteration);
        int databaseSizeBeforeUpdate = ticketAlterationRepository.findAll().size();

        // Update the ticketAlteration
        TicketAlteration updatedTicketAlteration = ticketAlterationRepository.findOne(ticketAlteration.getId());
        updatedTicketAlteration
                .lineNumber(UPDATED_LINE_NUMBER)
                .gender(UPDATED_GENDER)
                .altId(UPDATED_ALT_ID)
                .description(UPDATED_DESCRIPTION)
                .tagType(UPDATED_TAG_TYPE)
                .estimateTime(UPDATED_ESTIMATE_TIME)
                .price(UPDATED_PRICE)
                .measurement1(UPDATED_MEASUREMENT_1)
                .measurement2(UPDATED_MEASUREMENT_2)
                .quantity(UPDATED_QUANTITY)
                .completeIndicator(UPDATED_COMPLETE_INDICATOR);
        TicketAlterationDTO ticketAlterationDTO = ticketAlterationMapper.ticketAlterationToTicketAlterationDTO(updatedTicketAlteration);

        restTicketAlterationMockMvc.perform(put("/api/ticket-alterations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ticketAlterationDTO)))
            .andExpect(status().isOk());

        // Validate the TicketAlteration in the database
        List<TicketAlteration> ticketAlterationList = ticketAlterationRepository.findAll();
        assertThat(ticketAlterationList).hasSize(databaseSizeBeforeUpdate);
        TicketAlteration testTicketAlteration = ticketAlterationList.get(ticketAlterationList.size() - 1);
        assertThat(testTicketAlteration.getLineNumber()).isEqualTo(UPDATED_LINE_NUMBER);
        assertThat(testTicketAlteration.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testTicketAlteration.getAltId()).isEqualTo(UPDATED_ALT_ID);
        assertThat(testTicketAlteration.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testTicketAlteration.getTagType()).isEqualTo(UPDATED_TAG_TYPE);
        assertThat(testTicketAlteration.getEstimateTime()).isEqualTo(UPDATED_ESTIMATE_TIME);
        assertThat(testTicketAlteration.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testTicketAlteration.getMeasurement1()).isEqualTo(UPDATED_MEASUREMENT_1);
        assertThat(testTicketAlteration.getMeasurement2()).isEqualTo(UPDATED_MEASUREMENT_2);
        assertThat(testTicketAlteration.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testTicketAlteration.isCompleteIndicator()).isEqualTo(UPDATED_COMPLETE_INDICATOR);
    }

    @Test
    @Transactional
    public void updateNonExistingTicketAlteration() throws Exception {
        int databaseSizeBeforeUpdate = ticketAlterationRepository.findAll().size();

        // Create the TicketAlteration
        TicketAlterationDTO ticketAlterationDTO = ticketAlterationMapper.ticketAlterationToTicketAlterationDTO(ticketAlteration);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTicketAlterationMockMvc.perform(put("/api/ticket-alterations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ticketAlterationDTO)))
            .andExpect(status().isCreated());

        // Validate the TicketAlteration in the database
        List<TicketAlteration> ticketAlterationList = ticketAlterationRepository.findAll();
        assertThat(ticketAlterationList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTicketAlteration() throws Exception {
        // Initialize the database
        ticketAlterationRepository.saveAndFlush(ticketAlteration);
        int databaseSizeBeforeDelete = ticketAlterationRepository.findAll().size();

        // Get the ticketAlteration
        restTicketAlterationMockMvc.perform(delete("/api/ticket-alterations/{id}", ticketAlteration.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TicketAlteration> ticketAlterationList = ticketAlterationRepository.findAll();
        assertThat(ticketAlterationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
