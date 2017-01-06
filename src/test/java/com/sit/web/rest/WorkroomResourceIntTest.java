package com.sit.web.rest;

import com.sit.SitApp;

import com.sit.domain.Workroom;
import com.sit.repository.tenant.WorkroomRepository;
import com.sit.service.WorkroomService;
import com.sit.service.dto.WorkroomDTO;
import com.sit.service.mapper.WorkroomMapper;

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
 * Test class for the WorkroomResource REST controller.
 *
 * @see WorkroomResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SitApp.class)
public class WorkroomResourceIntTest {

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_PHONE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_CENTRAL_WORKROOM_INDICATOR = false;
    private static final Boolean UPDATED_CENTRAL_WORKROOM_INDICATOR = true;

    @Inject
    private WorkroomRepository workroomRepository;

    @Inject
    private WorkroomMapper workroomMapper;

    @Inject
    private WorkroomService workroomService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restWorkroomMockMvc;

    private Workroom workroom;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        WorkroomResource workroomResource = new WorkroomResource();
        ReflectionTestUtils.setField(workroomResource, "workroomService", workroomService);
        this.restWorkroomMockMvc = MockMvcBuilders.standaloneSetup(workroomResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Workroom createEntity(EntityManager em) {
        Workroom workroom = new Workroom()
                .description(DEFAULT_DESCRIPTION)
                .phone(DEFAULT_PHONE)
                .centralWorkroomIndicator(DEFAULT_CENTRAL_WORKROOM_INDICATOR);
        return workroom;
    }

    @Before
    public void initTest() {
        workroom = createEntity(em);
    }

    @Test
    @Transactional
    public void createWorkroom() throws Exception {
        int databaseSizeBeforeCreate = workroomRepository.findAll().size();

        // Create the Workroom
        WorkroomDTO workroomDTO = workroomMapper.workroomToWorkroomDTO(workroom);

        restWorkroomMockMvc.perform(post("/api/workrooms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(workroomDTO)))
            .andExpect(status().isCreated());

        // Validate the Workroom in the database
        List<Workroom> workroomList = workroomRepository.findAll();
        assertThat(workroomList).hasSize(databaseSizeBeforeCreate + 1);
        Workroom testWorkroom = workroomList.get(workroomList.size() - 1);
        assertThat(testWorkroom.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testWorkroom.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testWorkroom.isCentralWorkroomIndicator()).isEqualTo(DEFAULT_CENTRAL_WORKROOM_INDICATOR);
    }

    @Test
    @Transactional
    public void createWorkroomWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = workroomRepository.findAll().size();

        // Create the Workroom with an existing ID
        Workroom existingWorkroom = new Workroom();
        existingWorkroom.setId(1L);
        WorkroomDTO existingWorkroomDTO = workroomMapper.workroomToWorkroomDTO(existingWorkroom);

        // An entity with an existing ID cannot be created, so this API call must fail
        restWorkroomMockMvc.perform(post("/api/workrooms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingWorkroomDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Workroom> workroomList = workroomRepository.findAll();
        assertThat(workroomList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = workroomRepository.findAll().size();
        // set the field null
        workroom.setDescription(null);

        // Create the Workroom, which fails.
        WorkroomDTO workroomDTO = workroomMapper.workroomToWorkroomDTO(workroom);

        restWorkroomMockMvc.perform(post("/api/workrooms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(workroomDTO)))
            .andExpect(status().isBadRequest());

        List<Workroom> workroomList = workroomRepository.findAll();
        assertThat(workroomList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllWorkrooms() throws Exception {
        // Initialize the database
        workroomRepository.saveAndFlush(workroom);

        // Get all the workroomList
        restWorkroomMockMvc.perform(get("/api/workrooms?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(workroom.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE.toString())))
            .andExpect(jsonPath("$.[*].centralWorkroomIndicator").value(hasItem(DEFAULT_CENTRAL_WORKROOM_INDICATOR.booleanValue())));
    }

    @Test
    @Transactional
    public void getWorkroom() throws Exception {
        // Initialize the database
        workroomRepository.saveAndFlush(workroom);

        // Get the workroom
        restWorkroomMockMvc.perform(get("/api/workrooms/{id}", workroom.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(workroom.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE.toString()))
            .andExpect(jsonPath("$.centralWorkroomIndicator").value(DEFAULT_CENTRAL_WORKROOM_INDICATOR.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingWorkroom() throws Exception {
        // Get the workroom
        restWorkroomMockMvc.perform(get("/api/workrooms/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateWorkroom() throws Exception {
        // Initialize the database
        workroomRepository.saveAndFlush(workroom);
        int databaseSizeBeforeUpdate = workroomRepository.findAll().size();

        // Update the workroom
        Workroom updatedWorkroom = workroomRepository.findOne(workroom.getId());
        updatedWorkroom
                .description(UPDATED_DESCRIPTION)
                .phone(UPDATED_PHONE)
                .centralWorkroomIndicator(UPDATED_CENTRAL_WORKROOM_INDICATOR);
        WorkroomDTO workroomDTO = workroomMapper.workroomToWorkroomDTO(updatedWorkroom);

        restWorkroomMockMvc.perform(put("/api/workrooms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(workroomDTO)))
            .andExpect(status().isOk());

        // Validate the Workroom in the database
        List<Workroom> workroomList = workroomRepository.findAll();
        assertThat(workroomList).hasSize(databaseSizeBeforeUpdate);
        Workroom testWorkroom = workroomList.get(workroomList.size() - 1);
        assertThat(testWorkroom.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testWorkroom.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testWorkroom.isCentralWorkroomIndicator()).isEqualTo(UPDATED_CENTRAL_WORKROOM_INDICATOR);
    }

    @Test
    @Transactional
    public void updateNonExistingWorkroom() throws Exception {
        int databaseSizeBeforeUpdate = workroomRepository.findAll().size();

        // Create the Workroom
        WorkroomDTO workroomDTO = workroomMapper.workroomToWorkroomDTO(workroom);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restWorkroomMockMvc.perform(put("/api/workrooms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(workroomDTO)))
            .andExpect(status().isCreated());

        // Validate the Workroom in the database
        List<Workroom> workroomList = workroomRepository.findAll();
        assertThat(workroomList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteWorkroom() throws Exception {
        // Initialize the database
        workroomRepository.saveAndFlush(workroom);
        int databaseSizeBeforeDelete = workroomRepository.findAll().size();

        // Get the workroom
        restWorkroomMockMvc.perform(delete("/api/workrooms/{id}", workroom.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Workroom> workroomList = workroomRepository.findAll();
        assertThat(workroomList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
