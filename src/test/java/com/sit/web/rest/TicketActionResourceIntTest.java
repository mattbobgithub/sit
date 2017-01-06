package com.sit.web.rest;

import com.sit.SitApp;

import com.sit.domain.TicketAction;
import com.sit.repository.tenant.TicketActionRepository;
import com.sit.service.TicketActionService;
import com.sit.service.dto.TicketActionDTO;
import com.sit.service.mapper.TicketActionMapper;

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
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static com.sit.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.sit.domain.enumeration.ActionType;
/**
 * Test class for the TicketActionResource REST controller.
 *
 * @see TicketActionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SitApp.class)
public class TicketActionResourceIntTest {

    private static final ActionType DEFAULT_ACTION_TYPE = ActionType.NEW;
    private static final ActionType UPDATED_ACTION_TYPE = ActionType.TRANSFER_REQUEST;

    private static final ZonedDateTime DEFAULT_ACTION_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_ACTION_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_ACTION_BY = "AAAAAAAAAA";
    private static final String UPDATED_ACTION_BY = "BBBBBBBBBB";

    @Inject
    private TicketActionRepository ticketActionRepository;

    @Inject
    private TicketActionMapper ticketActionMapper;

    @Inject
    private TicketActionService ticketActionService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restTicketActionMockMvc;

    private TicketAction ticketAction;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TicketActionResource ticketActionResource = new TicketActionResource();
        ReflectionTestUtils.setField(ticketActionResource, "ticketActionService", ticketActionService);
        this.restTicketActionMockMvc = MockMvcBuilders.standaloneSetup(ticketActionResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TicketAction createEntity(EntityManager em) {
        TicketAction ticketAction = new TicketAction()
                .actionType(DEFAULT_ACTION_TYPE)
                .actionDate(DEFAULT_ACTION_DATE)
                .actionBy(DEFAULT_ACTION_BY);
        return ticketAction;
    }

    @Before
    public void initTest() {
        ticketAction = createEntity(em);
    }

    @Test
    @Transactional
    public void createTicketAction() throws Exception {
        int databaseSizeBeforeCreate = ticketActionRepository.findAll().size();

        // Create the TicketAction
        TicketActionDTO ticketActionDTO = ticketActionMapper.ticketActionToTicketActionDTO(ticketAction);

        restTicketActionMockMvc.perform(post("/api/ticket-actions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ticketActionDTO)))
            .andExpect(status().isCreated());

        // Validate the TicketAction in the database
        List<TicketAction> ticketActionList = ticketActionRepository.findAll();
        assertThat(ticketActionList).hasSize(databaseSizeBeforeCreate + 1);
        TicketAction testTicketAction = ticketActionList.get(ticketActionList.size() - 1);
        assertThat(testTicketAction.getActionType()).isEqualTo(DEFAULT_ACTION_TYPE);
        assertThat(testTicketAction.getActionDate()).isEqualTo(DEFAULT_ACTION_DATE);
        assertThat(testTicketAction.getActionBy()).isEqualTo(DEFAULT_ACTION_BY);
    }

    @Test
    @Transactional
    public void createTicketActionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = ticketActionRepository.findAll().size();

        // Create the TicketAction with an existing ID
        TicketAction existingTicketAction = new TicketAction();
        existingTicketAction.setId(1L);
        TicketActionDTO existingTicketActionDTO = ticketActionMapper.ticketActionToTicketActionDTO(existingTicketAction);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTicketActionMockMvc.perform(post("/api/ticket-actions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingTicketActionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<TicketAction> ticketActionList = ticketActionRepository.findAll();
        assertThat(ticketActionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllTicketActions() throws Exception {
        // Initialize the database
        ticketActionRepository.saveAndFlush(ticketAction);

        // Get all the ticketActionList
        restTicketActionMockMvc.perform(get("/api/ticket-actions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ticketAction.getId().intValue())))
            .andExpect(jsonPath("$.[*].actionType").value(hasItem(DEFAULT_ACTION_TYPE.toString())))
            .andExpect(jsonPath("$.[*].actionDate").value(hasItem(sameInstant(DEFAULT_ACTION_DATE))))
            .andExpect(jsonPath("$.[*].actionBy").value(hasItem(DEFAULT_ACTION_BY.toString())));
    }

    @Test
    @Transactional
    public void getTicketAction() throws Exception {
        // Initialize the database
        ticketActionRepository.saveAndFlush(ticketAction);

        // Get the ticketAction
        restTicketActionMockMvc.perform(get("/api/ticket-actions/{id}", ticketAction.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(ticketAction.getId().intValue()))
            .andExpect(jsonPath("$.actionType").value(DEFAULT_ACTION_TYPE.toString()))
            .andExpect(jsonPath("$.actionDate").value(sameInstant(DEFAULT_ACTION_DATE)))
            .andExpect(jsonPath("$.actionBy").value(DEFAULT_ACTION_BY.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTicketAction() throws Exception {
        // Get the ticketAction
        restTicketActionMockMvc.perform(get("/api/ticket-actions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTicketAction() throws Exception {
        // Initialize the database
        ticketActionRepository.saveAndFlush(ticketAction);
        int databaseSizeBeforeUpdate = ticketActionRepository.findAll().size();

        // Update the ticketAction
        TicketAction updatedTicketAction = ticketActionRepository.findOne(ticketAction.getId());
        updatedTicketAction
                .actionType(UPDATED_ACTION_TYPE)
                .actionDate(UPDATED_ACTION_DATE)
                .actionBy(UPDATED_ACTION_BY);
        TicketActionDTO ticketActionDTO = ticketActionMapper.ticketActionToTicketActionDTO(updatedTicketAction);

        restTicketActionMockMvc.perform(put("/api/ticket-actions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ticketActionDTO)))
            .andExpect(status().isOk());

        // Validate the TicketAction in the database
        List<TicketAction> ticketActionList = ticketActionRepository.findAll();
        assertThat(ticketActionList).hasSize(databaseSizeBeforeUpdate);
        TicketAction testTicketAction = ticketActionList.get(ticketActionList.size() - 1);
        assertThat(testTicketAction.getActionType()).isEqualTo(UPDATED_ACTION_TYPE);
        assertThat(testTicketAction.getActionDate()).isEqualTo(UPDATED_ACTION_DATE);
        assertThat(testTicketAction.getActionBy()).isEqualTo(UPDATED_ACTION_BY);
    }

    @Test
    @Transactional
    public void updateNonExistingTicketAction() throws Exception {
        int databaseSizeBeforeUpdate = ticketActionRepository.findAll().size();

        // Create the TicketAction
        TicketActionDTO ticketActionDTO = ticketActionMapper.ticketActionToTicketActionDTO(ticketAction);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTicketActionMockMvc.perform(put("/api/ticket-actions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ticketActionDTO)))
            .andExpect(status().isCreated());

        // Validate the TicketAction in the database
        List<TicketAction> ticketActionList = ticketActionRepository.findAll();
        assertThat(ticketActionList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTicketAction() throws Exception {
        // Initialize the database
        ticketActionRepository.saveAndFlush(ticketAction);
        int databaseSizeBeforeDelete = ticketActionRepository.findAll().size();

        // Get the ticketAction
        restTicketActionMockMvc.perform(delete("/api/ticket-actions/{id}", ticketAction.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TicketAction> ticketActionList = ticketActionRepository.findAll();
        assertThat(ticketActionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
