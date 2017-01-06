package com.sit.web.rest;

import com.sit.SitApp;

import com.sit.domain.Transfer;
import com.sit.repository.tenant.TransferRepository;
import com.sit.service.TransferService;
import com.sit.service.dto.TransferDTO;
import com.sit.service.mapper.TransferMapper;

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

import com.sit.domain.enumeration.TransferStatus;
/**
 * Test class for the TransferResource REST controller.
 *
 * @see TransferResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SitApp.class)
public class TransferResourceIntTest {

    private static final Long DEFAULT_FROM_WORKROOM_ID = 1L;
    private static final Long UPDATED_FROM_WORKROOM_ID = 2L;

    private static final Long DEFAULT_TO_WORKROOM_ID = 1L;
    private static final Long UPDATED_TO_WORKROOM_ID = 2L;

    private static final TransferStatus DEFAULT_STATUS = TransferStatus.OUT;
    private static final TransferStatus UPDATED_STATUS = TransferStatus.ENROUTE;

    @Inject
    private TransferRepository transferRepository;

    @Inject
    private TransferMapper transferMapper;

    @Inject
    private TransferService transferService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restTransferMockMvc;

    private Transfer transfer;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TransferResource transferResource = new TransferResource();
        ReflectionTestUtils.setField(transferResource, "transferService", transferService);
        this.restTransferMockMvc = MockMvcBuilders.standaloneSetup(transferResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Transfer createEntity(EntityManager em) {
        Transfer transfer = new Transfer()
                .fromWorkroomId(DEFAULT_FROM_WORKROOM_ID)
                .toWorkroomId(DEFAULT_TO_WORKROOM_ID)
                .status(DEFAULT_STATUS);
        return transfer;
    }

    @Before
    public void initTest() {
        transfer = createEntity(em);
    }

    @Test
    @Transactional
    public void createTransfer() throws Exception {
        int databaseSizeBeforeCreate = transferRepository.findAll().size();

        // Create the Transfer
        TransferDTO transferDTO = transferMapper.transferToTransferDTO(transfer);

        restTransferMockMvc.perform(post("/api/transfers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transferDTO)))
            .andExpect(status().isCreated());

        // Validate the Transfer in the database
        List<Transfer> transferList = transferRepository.findAll();
        assertThat(transferList).hasSize(databaseSizeBeforeCreate + 1);
        Transfer testTransfer = transferList.get(transferList.size() - 1);
        assertThat(testTransfer.getFromWorkroomId()).isEqualTo(DEFAULT_FROM_WORKROOM_ID);
        assertThat(testTransfer.getToWorkroomId()).isEqualTo(DEFAULT_TO_WORKROOM_ID);
        assertThat(testTransfer.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createTransferWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = transferRepository.findAll().size();

        // Create the Transfer with an existing ID
        Transfer existingTransfer = new Transfer();
        existingTransfer.setId(1L);
        TransferDTO existingTransferDTO = transferMapper.transferToTransferDTO(existingTransfer);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTransferMockMvc.perform(post("/api/transfers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingTransferDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Transfer> transferList = transferRepository.findAll();
        assertThat(transferList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllTransfers() throws Exception {
        // Initialize the database
        transferRepository.saveAndFlush(transfer);

        // Get all the transferList
        restTransferMockMvc.perform(get("/api/transfers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(transfer.getId().intValue())))
            .andExpect(jsonPath("$.[*].fromWorkroomId").value(hasItem(DEFAULT_FROM_WORKROOM_ID.intValue())))
            .andExpect(jsonPath("$.[*].toWorkroomId").value(hasItem(DEFAULT_TO_WORKROOM_ID.intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }

    @Test
    @Transactional
    public void getTransfer() throws Exception {
        // Initialize the database
        transferRepository.saveAndFlush(transfer);

        // Get the transfer
        restTransferMockMvc.perform(get("/api/transfers/{id}", transfer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(transfer.getId().intValue()))
            .andExpect(jsonPath("$.fromWorkroomId").value(DEFAULT_FROM_WORKROOM_ID.intValue()))
            .andExpect(jsonPath("$.toWorkroomId").value(DEFAULT_TO_WORKROOM_ID.intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTransfer() throws Exception {
        // Get the transfer
        restTransferMockMvc.perform(get("/api/transfers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTransfer() throws Exception {
        // Initialize the database
        transferRepository.saveAndFlush(transfer);
        int databaseSizeBeforeUpdate = transferRepository.findAll().size();

        // Update the transfer
        Transfer updatedTransfer = transferRepository.findOne(transfer.getId());
        updatedTransfer
                .fromWorkroomId(UPDATED_FROM_WORKROOM_ID)
                .toWorkroomId(UPDATED_TO_WORKROOM_ID)
                .status(UPDATED_STATUS);
        TransferDTO transferDTO = transferMapper.transferToTransferDTO(updatedTransfer);

        restTransferMockMvc.perform(put("/api/transfers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transferDTO)))
            .andExpect(status().isOk());

        // Validate the Transfer in the database
        List<Transfer> transferList = transferRepository.findAll();
        assertThat(transferList).hasSize(databaseSizeBeforeUpdate);
        Transfer testTransfer = transferList.get(transferList.size() - 1);
        assertThat(testTransfer.getFromWorkroomId()).isEqualTo(UPDATED_FROM_WORKROOM_ID);
        assertThat(testTransfer.getToWorkroomId()).isEqualTo(UPDATED_TO_WORKROOM_ID);
        assertThat(testTransfer.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingTransfer() throws Exception {
        int databaseSizeBeforeUpdate = transferRepository.findAll().size();

        // Create the Transfer
        TransferDTO transferDTO = transferMapper.transferToTransferDTO(transfer);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTransferMockMvc.perform(put("/api/transfers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transferDTO)))
            .andExpect(status().isCreated());

        // Validate the Transfer in the database
        List<Transfer> transferList = transferRepository.findAll();
        assertThat(transferList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTransfer() throws Exception {
        // Initialize the database
        transferRepository.saveAndFlush(transfer);
        int databaseSizeBeforeDelete = transferRepository.findAll().size();

        // Get the transfer
        restTransferMockMvc.perform(delete("/api/transfers/{id}", transfer.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Transfer> transferList = transferRepository.findAll();
        assertThat(transferList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
