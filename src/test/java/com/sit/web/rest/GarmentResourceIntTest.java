package com.sit.web.rest;

import com.sit.SitApp;

import com.sit.domain.Garment;
import com.sit.repository.tenant.GarmentRepository;
import com.sit.service.GarmentService;
import com.sit.service.dto.GarmentDTO;
import com.sit.service.mapper.GarmentMapper;

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
 * Test class for the GarmentResource REST controller.
 *
 * @see GarmentResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SitApp.class)
public class GarmentResourceIntTest {

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Gender DEFAULT_GENDER = Gender.MENS;
    private static final Gender UPDATED_GENDER = Gender.WOMENS;

    @Inject
    private GarmentRepository garmentRepository;

    @Inject
    private GarmentMapper garmentMapper;

    @Inject
    private GarmentService garmentService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restGarmentMockMvc;

    private Garment garment;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        GarmentResource garmentResource = new GarmentResource();
        ReflectionTestUtils.setField(garmentResource, "garmentService", garmentService);
        this.restGarmentMockMvc = MockMvcBuilders.standaloneSetup(garmentResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Garment createEntity(EntityManager em) {
        Garment garment = new Garment()
                .description(DEFAULT_DESCRIPTION)
                .gender(DEFAULT_GENDER);
        return garment;
    }

    @Before
    public void initTest() {
        garment = createEntity(em);
    }

    @Test
    @Transactional
    public void createGarment() throws Exception {
        int databaseSizeBeforeCreate = garmentRepository.findAll().size();

        // Create the Garment
        GarmentDTO garmentDTO = garmentMapper.garmentToGarmentDTO(garment);

        restGarmentMockMvc.perform(post("/api/garments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(garmentDTO)))
            .andExpect(status().isCreated());

        // Validate the Garment in the database
        List<Garment> garmentList = garmentRepository.findAll();
        assertThat(garmentList).hasSize(databaseSizeBeforeCreate + 1);
        Garment testGarment = garmentList.get(garmentList.size() - 1);
        assertThat(testGarment.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testGarment.getGender()).isEqualTo(DEFAULT_GENDER);
    }

    @Test
    @Transactional
    public void createGarmentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = garmentRepository.findAll().size();

        // Create the Garment with an existing ID
        Garment existingGarment = new Garment();
        existingGarment.setId(1L);
        GarmentDTO existingGarmentDTO = garmentMapper.garmentToGarmentDTO(existingGarment);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGarmentMockMvc.perform(post("/api/garments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingGarmentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Garment> garmentList = garmentRepository.findAll();
        assertThat(garmentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = garmentRepository.findAll().size();
        // set the field null
        garment.setDescription(null);

        // Create the Garment, which fails.
        GarmentDTO garmentDTO = garmentMapper.garmentToGarmentDTO(garment);

        restGarmentMockMvc.perform(post("/api/garments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(garmentDTO)))
            .andExpect(status().isBadRequest());

        List<Garment> garmentList = garmentRepository.findAll();
        assertThat(garmentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllGarments() throws Exception {
        // Initialize the database
        garmentRepository.saveAndFlush(garment);

        // Get all the garmentList
        restGarmentMockMvc.perform(get("/api/garments?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(garment.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER.toString())));
    }

    @Test
    @Transactional
    public void getGarment() throws Exception {
        // Initialize the database
        garmentRepository.saveAndFlush(garment);

        // Get the garment
        restGarmentMockMvc.perform(get("/api/garments/{id}", garment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(garment.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.gender").value(DEFAULT_GENDER.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingGarment() throws Exception {
        // Get the garment
        restGarmentMockMvc.perform(get("/api/garments/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGarment() throws Exception {
        // Initialize the database
        garmentRepository.saveAndFlush(garment);
        int databaseSizeBeforeUpdate = garmentRepository.findAll().size();

        // Update the garment
        Garment updatedGarment = garmentRepository.findOne(garment.getId());
        updatedGarment
                .description(UPDATED_DESCRIPTION)
                .gender(UPDATED_GENDER);
        GarmentDTO garmentDTO = garmentMapper.garmentToGarmentDTO(updatedGarment);

        restGarmentMockMvc.perform(put("/api/garments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(garmentDTO)))
            .andExpect(status().isOk());

        // Validate the Garment in the database
        List<Garment> garmentList = garmentRepository.findAll();
        assertThat(garmentList).hasSize(databaseSizeBeforeUpdate);
        Garment testGarment = garmentList.get(garmentList.size() - 1);
        assertThat(testGarment.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testGarment.getGender()).isEqualTo(UPDATED_GENDER);
    }

    @Test
    @Transactional
    public void updateNonExistingGarment() throws Exception {
        int databaseSizeBeforeUpdate = garmentRepository.findAll().size();

        // Create the Garment
        GarmentDTO garmentDTO = garmentMapper.garmentToGarmentDTO(garment);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restGarmentMockMvc.perform(put("/api/garments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(garmentDTO)))
            .andExpect(status().isCreated());

        // Validate the Garment in the database
        List<Garment> garmentList = garmentRepository.findAll();
        assertThat(garmentList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteGarment() throws Exception {
        // Initialize the database
        garmentRepository.saveAndFlush(garment);
        int databaseSizeBeforeDelete = garmentRepository.findAll().size();

        // Get the garment
        restGarmentMockMvc.perform(delete("/api/garments/{id}", garment.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Garment> garmentList = garmentRepository.findAll();
        assertThat(garmentList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
