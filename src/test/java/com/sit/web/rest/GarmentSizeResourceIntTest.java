package com.sit.web.rest;

import com.sit.SitApp;

import com.sit.domain.GarmentSize;
import com.sit.repository.tenant.GarmentSizeRepository;
import com.sit.service.GarmentSizeService;
import com.sit.service.dto.GarmentSizeDTO;
import com.sit.service.mapper.GarmentSizeMapper;

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
 * Test class for the GarmentSizeResource REST controller.
 *
 * @see GarmentSizeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SitApp.class)
public class GarmentSizeResourceIntTest {

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Inject
    private GarmentSizeRepository garmentSizeRepository;

    @Inject
    private GarmentSizeMapper garmentSizeMapper;

    @Inject
    private GarmentSizeService garmentSizeService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restGarmentSizeMockMvc;

    private GarmentSize garmentSize;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        GarmentSizeResource garmentSizeResource = new GarmentSizeResource();
        ReflectionTestUtils.setField(garmentSizeResource, "garmentSizeService", garmentSizeService);
        this.restGarmentSizeMockMvc = MockMvcBuilders.standaloneSetup(garmentSizeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GarmentSize createEntity(EntityManager em) {
        GarmentSize garmentSize = new GarmentSize()
                .description(DEFAULT_DESCRIPTION);
        return garmentSize;
    }

    @Before
    public void initTest() {
        garmentSize = createEntity(em);
    }

    @Test
    @Transactional
    public void createGarmentSize() throws Exception {
        int databaseSizeBeforeCreate = garmentSizeRepository.findAll().size();

        // Create the GarmentSize
        GarmentSizeDTO garmentSizeDTO = garmentSizeMapper.garmentSizeToGarmentSizeDTO(garmentSize);

        restGarmentSizeMockMvc.perform(post("/api/garment-sizes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(garmentSizeDTO)))
            .andExpect(status().isCreated());

        // Validate the GarmentSize in the database
        List<GarmentSize> garmentSizeList = garmentSizeRepository.findAll();
        assertThat(garmentSizeList).hasSize(databaseSizeBeforeCreate + 1);
        GarmentSize testGarmentSize = garmentSizeList.get(garmentSizeList.size() - 1);
        assertThat(testGarmentSize.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createGarmentSizeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = garmentSizeRepository.findAll().size();

        // Create the GarmentSize with an existing ID
        GarmentSize existingGarmentSize = new GarmentSize();
        existingGarmentSize.setId(1L);
        GarmentSizeDTO existingGarmentSizeDTO = garmentSizeMapper.garmentSizeToGarmentSizeDTO(existingGarmentSize);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGarmentSizeMockMvc.perform(post("/api/garment-sizes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingGarmentSizeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<GarmentSize> garmentSizeList = garmentSizeRepository.findAll();
        assertThat(garmentSizeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = garmentSizeRepository.findAll().size();
        // set the field null
        garmentSize.setDescription(null);

        // Create the GarmentSize, which fails.
        GarmentSizeDTO garmentSizeDTO = garmentSizeMapper.garmentSizeToGarmentSizeDTO(garmentSize);

        restGarmentSizeMockMvc.perform(post("/api/garment-sizes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(garmentSizeDTO)))
            .andExpect(status().isBadRequest());

        List<GarmentSize> garmentSizeList = garmentSizeRepository.findAll();
        assertThat(garmentSizeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllGarmentSizes() throws Exception {
        // Initialize the database
        garmentSizeRepository.saveAndFlush(garmentSize);

        // Get all the garmentSizeList
        restGarmentSizeMockMvc.perform(get("/api/garment-sizes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(garmentSize.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getGarmentSize() throws Exception {
        // Initialize the database
        garmentSizeRepository.saveAndFlush(garmentSize);

        // Get the garmentSize
        restGarmentSizeMockMvc.perform(get("/api/garment-sizes/{id}", garmentSize.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(garmentSize.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingGarmentSize() throws Exception {
        // Get the garmentSize
        restGarmentSizeMockMvc.perform(get("/api/garment-sizes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGarmentSize() throws Exception {
        // Initialize the database
        garmentSizeRepository.saveAndFlush(garmentSize);
        int databaseSizeBeforeUpdate = garmentSizeRepository.findAll().size();

        // Update the garmentSize
        GarmentSize updatedGarmentSize = garmentSizeRepository.findOne(garmentSize.getId());
        updatedGarmentSize
                .description(UPDATED_DESCRIPTION);
        GarmentSizeDTO garmentSizeDTO = garmentSizeMapper.garmentSizeToGarmentSizeDTO(updatedGarmentSize);

        restGarmentSizeMockMvc.perform(put("/api/garment-sizes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(garmentSizeDTO)))
            .andExpect(status().isOk());

        // Validate the GarmentSize in the database
        List<GarmentSize> garmentSizeList = garmentSizeRepository.findAll();
        assertThat(garmentSizeList).hasSize(databaseSizeBeforeUpdate);
        GarmentSize testGarmentSize = garmentSizeList.get(garmentSizeList.size() - 1);
        assertThat(testGarmentSize.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingGarmentSize() throws Exception {
        int databaseSizeBeforeUpdate = garmentSizeRepository.findAll().size();

        // Create the GarmentSize
        GarmentSizeDTO garmentSizeDTO = garmentSizeMapper.garmentSizeToGarmentSizeDTO(garmentSize);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restGarmentSizeMockMvc.perform(put("/api/garment-sizes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(garmentSizeDTO)))
            .andExpect(status().isCreated());

        // Validate the GarmentSize in the database
        List<GarmentSize> garmentSizeList = garmentSizeRepository.findAll();
        assertThat(garmentSizeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteGarmentSize() throws Exception {
        // Initialize the database
        garmentSizeRepository.saveAndFlush(garmentSize);
        int databaseSizeBeforeDelete = garmentSizeRepository.findAll().size();

        // Get the garmentSize
        restGarmentSizeMockMvc.perform(delete("/api/garment-sizes/{id}", garmentSize.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<GarmentSize> garmentSizeList = garmentSizeRepository.findAll();
        assertThat(garmentSizeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
