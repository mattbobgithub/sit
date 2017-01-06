package com.sit.web.rest;

import com.sit.SitApp;

import com.sit.domain.Ticket;
import com.sit.repository.tenant.TicketRepository;
import com.sit.service.TicketService;
import com.sit.service.dto.TicketDTO;
import com.sit.service.mapper.TicketMapper;

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

import com.sit.domain.enumeration.PurchaseType;
import com.sit.domain.enumeration.TicketPriority;
import com.sit.domain.enumeration.WaiveCostReason;
/**
 * Test class for the TicketResource REST controller.
 *
 * @see TicketResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SitApp.class)
public class TicketResourceIntTest {

    private static final ZonedDateTime DEFAULT_DROP_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DROP_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_PROMISE_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_PROMISE_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_WORKROOM_DEADLINE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_WORKROOM_DEADLINE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_RECIEPT_ID = "AAAAAAAAAA";
    private static final String UPDATED_RECIEPT_ID = "BBBBBBBBBB";

    private static final Double DEFAULT_PRICE_CHARGED = 1D;
    private static final Double UPDATED_PRICE_CHARGED = 2D;

    private static final PurchaseType DEFAULT_PURCHASE_TYPE = PurchaseType.NEW;
    private static final PurchaseType UPDATED_PURCHASE_TYPE = PurchaseType.RETURN;

    private static final TicketPriority DEFAULT_PRIORITY = TicketPriority.NORMAL;
    private static final TicketPriority UPDATED_PRIORITY = TicketPriority.HIGH;

    private static final String DEFAULT_COMPLETED_BY = "AAAAAAAAAA";
    private static final String UPDATED_COMPLETED_BY = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_COMPLETED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_COMPLETED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Boolean DEFAULT_WAIVE_COST_INDICATOR = false;
    private static final Boolean UPDATED_WAIVE_COST_INDICATOR = true;

    private static final WaiveCostReason DEFAULT_WAIVE_COST_REASON = WaiveCostReason.SALES_PREFERENCE;
    private static final WaiveCostReason UPDATED_WAIVE_COST_REASON = WaiveCostReason.LOYALTY;

    @Inject
    private TicketRepository ticketRepository;

    @Inject
    private TicketMapper ticketMapper;

    @Inject
    private TicketService ticketService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restTicketMockMvc;

    private Ticket ticket;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TicketResource ticketResource = new TicketResource();
        ReflectionTestUtils.setField(ticketResource, "ticketService", ticketService);
        this.restTicketMockMvc = MockMvcBuilders.standaloneSetup(ticketResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Ticket createEntity(EntityManager em) {
        Ticket ticket = new Ticket()
                .dropDate(DEFAULT_DROP_DATE)
                .promiseDate(DEFAULT_PROMISE_DATE)
                .workroomDeadline(DEFAULT_WORKROOM_DEADLINE)
                .recieptId(DEFAULT_RECIEPT_ID)
                .priceCharged(DEFAULT_PRICE_CHARGED)
                .purchaseType(DEFAULT_PURCHASE_TYPE)
                .priority(DEFAULT_PRIORITY)
                .completedBy(DEFAULT_COMPLETED_BY)
                .completedDate(DEFAULT_COMPLETED_DATE)
                .waiveCostIndicator(DEFAULT_WAIVE_COST_INDICATOR)
                .waiveCostReason(DEFAULT_WAIVE_COST_REASON);
        return ticket;
    }

    @Before
    public void initTest() {
        ticket = createEntity(em);
    }

    @Test
    @Transactional
    public void createTicket() throws Exception {
        int databaseSizeBeforeCreate = ticketRepository.findAll().size();

        // Create the Ticket
        TicketDTO ticketDTO = ticketMapper.ticketToTicketDTO(ticket);

        restTicketMockMvc.perform(post("/api/tickets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ticketDTO)))
            .andExpect(status().isCreated());

        // Validate the Ticket in the database
        List<Ticket> ticketList = ticketRepository.findAll();
        assertThat(ticketList).hasSize(databaseSizeBeforeCreate + 1);
        Ticket testTicket = ticketList.get(ticketList.size() - 1);
        assertThat(testTicket.getDropDate()).isEqualTo(DEFAULT_DROP_DATE);
        assertThat(testTicket.getPromiseDate()).isEqualTo(DEFAULT_PROMISE_DATE);
        assertThat(testTicket.getWorkroomDeadline()).isEqualTo(DEFAULT_WORKROOM_DEADLINE);
        assertThat(testTicket.getRecieptId()).isEqualTo(DEFAULT_RECIEPT_ID);
        assertThat(testTicket.getPriceCharged()).isEqualTo(DEFAULT_PRICE_CHARGED);
        assertThat(testTicket.getPurchaseType()).isEqualTo(DEFAULT_PURCHASE_TYPE);
        assertThat(testTicket.getPriority()).isEqualTo(DEFAULT_PRIORITY);
        assertThat(testTicket.getCompletedBy()).isEqualTo(DEFAULT_COMPLETED_BY);
        assertThat(testTicket.getCompletedDate()).isEqualTo(DEFAULT_COMPLETED_DATE);
        assertThat(testTicket.isWaiveCostIndicator()).isEqualTo(DEFAULT_WAIVE_COST_INDICATOR);
        assertThat(testTicket.getWaiveCostReason()).isEqualTo(DEFAULT_WAIVE_COST_REASON);
    }

    @Test
    @Transactional
    public void createTicketWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = ticketRepository.findAll().size();

        // Create the Ticket with an existing ID
        Ticket existingTicket = new Ticket();
        existingTicket.setId(1L);
        TicketDTO existingTicketDTO = ticketMapper.ticketToTicketDTO(existingTicket);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTicketMockMvc.perform(post("/api/tickets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingTicketDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Ticket> ticketList = ticketRepository.findAll();
        assertThat(ticketList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllTickets() throws Exception {
        // Initialize the database
        ticketRepository.saveAndFlush(ticket);

        // Get all the ticketList
        restTicketMockMvc.perform(get("/api/tickets?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ticket.getId().intValue())))
            .andExpect(jsonPath("$.[*].dropDate").value(hasItem(sameInstant(DEFAULT_DROP_DATE))))
            .andExpect(jsonPath("$.[*].promiseDate").value(hasItem(sameInstant(DEFAULT_PROMISE_DATE))))
            .andExpect(jsonPath("$.[*].workroomDeadline").value(hasItem(sameInstant(DEFAULT_WORKROOM_DEADLINE))))
            .andExpect(jsonPath("$.[*].recieptId").value(hasItem(DEFAULT_RECIEPT_ID.toString())))
            .andExpect(jsonPath("$.[*].priceCharged").value(hasItem(DEFAULT_PRICE_CHARGED.doubleValue())))
            .andExpect(jsonPath("$.[*].purchaseType").value(hasItem(DEFAULT_PURCHASE_TYPE.toString())))
            .andExpect(jsonPath("$.[*].priority").value(hasItem(DEFAULT_PRIORITY.toString())))
            .andExpect(jsonPath("$.[*].completedBy").value(hasItem(DEFAULT_COMPLETED_BY.toString())))
            .andExpect(jsonPath("$.[*].completedDate").value(hasItem(sameInstant(DEFAULT_COMPLETED_DATE))))
            .andExpect(jsonPath("$.[*].waiveCostIndicator").value(hasItem(DEFAULT_WAIVE_COST_INDICATOR.booleanValue())))
            .andExpect(jsonPath("$.[*].waiveCostReason").value(hasItem(DEFAULT_WAIVE_COST_REASON.toString())));
    }

    @Test
    @Transactional
    public void getTicket() throws Exception {
        // Initialize the database
        ticketRepository.saveAndFlush(ticket);

        // Get the ticket
        restTicketMockMvc.perform(get("/api/tickets/{id}", ticket.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(ticket.getId().intValue()))
            .andExpect(jsonPath("$.dropDate").value(sameInstant(DEFAULT_DROP_DATE)))
            .andExpect(jsonPath("$.promiseDate").value(sameInstant(DEFAULT_PROMISE_DATE)))
            .andExpect(jsonPath("$.workroomDeadline").value(sameInstant(DEFAULT_WORKROOM_DEADLINE)))
            .andExpect(jsonPath("$.recieptId").value(DEFAULT_RECIEPT_ID.toString()))
            .andExpect(jsonPath("$.priceCharged").value(DEFAULT_PRICE_CHARGED.doubleValue()))
            .andExpect(jsonPath("$.purchaseType").value(DEFAULT_PURCHASE_TYPE.toString()))
            .andExpect(jsonPath("$.priority").value(DEFAULT_PRIORITY.toString()))
            .andExpect(jsonPath("$.completedBy").value(DEFAULT_COMPLETED_BY.toString()))
            .andExpect(jsonPath("$.completedDate").value(sameInstant(DEFAULT_COMPLETED_DATE)))
            .andExpect(jsonPath("$.waiveCostIndicator").value(DEFAULT_WAIVE_COST_INDICATOR.booleanValue()))
            .andExpect(jsonPath("$.waiveCostReason").value(DEFAULT_WAIVE_COST_REASON.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTicket() throws Exception {
        // Get the ticket
        restTicketMockMvc.perform(get("/api/tickets/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTicket() throws Exception {
        // Initialize the database
        ticketRepository.saveAndFlush(ticket);
        int databaseSizeBeforeUpdate = ticketRepository.findAll().size();

        // Update the ticket
        Ticket updatedTicket = ticketRepository.findOne(ticket.getId());
        updatedTicket
                .dropDate(UPDATED_DROP_DATE)
                .promiseDate(UPDATED_PROMISE_DATE)
                .workroomDeadline(UPDATED_WORKROOM_DEADLINE)
                .recieptId(UPDATED_RECIEPT_ID)
                .priceCharged(UPDATED_PRICE_CHARGED)
                .purchaseType(UPDATED_PURCHASE_TYPE)
                .priority(UPDATED_PRIORITY)
                .completedBy(UPDATED_COMPLETED_BY)
                .completedDate(UPDATED_COMPLETED_DATE)
                .waiveCostIndicator(UPDATED_WAIVE_COST_INDICATOR)
                .waiveCostReason(UPDATED_WAIVE_COST_REASON);
        TicketDTO ticketDTO = ticketMapper.ticketToTicketDTO(updatedTicket);

        restTicketMockMvc.perform(put("/api/tickets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ticketDTO)))
            .andExpect(status().isOk());

        // Validate the Ticket in the database
        List<Ticket> ticketList = ticketRepository.findAll();
        assertThat(ticketList).hasSize(databaseSizeBeforeUpdate);
        Ticket testTicket = ticketList.get(ticketList.size() - 1);
        assertThat(testTicket.getDropDate()).isEqualTo(UPDATED_DROP_DATE);
        assertThat(testTicket.getPromiseDate()).isEqualTo(UPDATED_PROMISE_DATE);
        assertThat(testTicket.getWorkroomDeadline()).isEqualTo(UPDATED_WORKROOM_DEADLINE);
        assertThat(testTicket.getRecieptId()).isEqualTo(UPDATED_RECIEPT_ID);
        assertThat(testTicket.getPriceCharged()).isEqualTo(UPDATED_PRICE_CHARGED);
        assertThat(testTicket.getPurchaseType()).isEqualTo(UPDATED_PURCHASE_TYPE);
        assertThat(testTicket.getPriority()).isEqualTo(UPDATED_PRIORITY);
        assertThat(testTicket.getCompletedBy()).isEqualTo(UPDATED_COMPLETED_BY);
        assertThat(testTicket.getCompletedDate()).isEqualTo(UPDATED_COMPLETED_DATE);
        assertThat(testTicket.isWaiveCostIndicator()).isEqualTo(UPDATED_WAIVE_COST_INDICATOR);
        assertThat(testTicket.getWaiveCostReason()).isEqualTo(UPDATED_WAIVE_COST_REASON);
    }

    @Test
    @Transactional
    public void updateNonExistingTicket() throws Exception {
        int databaseSizeBeforeUpdate = ticketRepository.findAll().size();

        // Create the Ticket
        TicketDTO ticketDTO = ticketMapper.ticketToTicketDTO(ticket);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTicketMockMvc.perform(put("/api/tickets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ticketDTO)))
            .andExpect(status().isCreated());

        // Validate the Ticket in the database
        List<Ticket> ticketList = ticketRepository.findAll();
        assertThat(ticketList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTicket() throws Exception {
        // Initialize the database
        ticketRepository.saveAndFlush(ticket);
        int databaseSizeBeforeDelete = ticketRepository.findAll().size();

        // Get the ticket
        restTicketMockMvc.perform(delete("/api/tickets/{id}", ticket.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Ticket> ticketList = ticketRepository.findAll();
        assertThat(ticketList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
