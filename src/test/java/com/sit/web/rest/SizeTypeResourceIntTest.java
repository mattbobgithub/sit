package com.sit.web.rest;

import com.sit.SitApp;

import com.sit.domain.SizeType;
import com.sit.repository.tenant.SizeTypeRepository;
import com.sit.service.SizeTypeService;
import com.sit.service.dto.SizeTypeDTO;
import com.sit.service.mapper.SizeTypeMapper;

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
 * Test class for the SizeTypeResource REST controller.
 *
 * @see SizeTypeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SitApp.class)
public class SizeTypeResourceIntTest {

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Inject
    private SizeTypeRepository sizeTypeRepository;

    @Inject
    private SizeTypeMapper sizeTypeMapper;

    @Inject
    private SizeTypeService sizeTypeService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restSizeTypeMockMvc;

    private SizeType sizeType;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SizeTypeResource sizeTypeResource = new SizeTypeResource();
        ReflectionTestUtils.setField(sizeTypeResource, "sizeTypeService", sizeTypeService);
        this.restSizeTypeMockMvc = MockMvcBuilders.standaloneSetup(sizeTypeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SizeType createEntity(EntityManager em) {
        SizeType sizeType = new SizeType()
                .description(DEFAULT_DESCRIPTION);
        return sizeType;
    }

    @Before
    public void initTest() {
        sizeType = createEntity(em);
    }

    @Test
    @Transactional
    public void createSizeType() throws Exception {
        int databaseSizeBeforeCreate = sizeTypeRepository.findAll().size();

        // Create the SizeType
        SizeTypeDTO sizeTypeDTO = sizeTypeMapper.sizeTypeToSizeTypeDTO(sizeType);

        restSizeTypeMockMvc.perform(post("/api/size-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sizeTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the SizeType in the database
        List<SizeType> sizeTypeList = sizeTypeRepository.findAll();
        assertThat(sizeTypeList).hasSize(databaseSizeBeforeCreate + 1);
        SizeType testSizeType = sizeTypeList.get(sizeTypeList.size() - 1);
        assertThat(testSizeType.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createSizeTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = sizeTypeRepository.findAll().size();

        // Create the SizeType with an existing ID
        SizeType existingSizeType = new SizeType();
        existingSizeType.setId(1L);
        SizeTypeDTO existingSizeTypeDTO = sizeTypeMapper.sizeTypeToSizeTypeDTO(existingSizeType);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSizeTypeMockMvc.perform(post("/api/size-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingSizeTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<SizeType> sizeTypeList = sizeTypeRepository.findAll();
        assertThat(sizeTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = sizeTypeRepository.findAll().size();
        // set the field null
        sizeType.setDescription(null);

        // Create the SizeType, which fails.
        SizeTypeDTO sizeTypeDTO = sizeTypeMapper.sizeTypeToSizeTypeDTO(sizeType);

        restSizeTypeMockMvc.perform(post("/api/size-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sizeTypeDTO)))
            .andExpect(status().isBadRequest());

        List<SizeType> sizeTypeList = sizeTypeRepository.findAll();
        assertThat(sizeTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSizeTypes() throws Exception {
        // Initialize the database
        sizeTypeRepository.saveAndFlush(sizeType);

        // Get all the sizeTypeList
        restSizeTypeMockMvc.perform(get("/api/size-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sizeType.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getSizeType() throws Exception {
        // Initialize the database
        sizeTypeRepository.saveAndFlush(sizeType);

        // Get the sizeType
        restSizeTypeMockMvc.perform(get("/api/size-types/{id}", sizeType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(sizeType.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSizeType() throws Exception {
        // Get the sizeType
        restSizeTypeMockMvc.perform(get("/api/size-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSizeType() throws Exception {
        // Initialize the database
        sizeTypeRepository.saveAndFlush(sizeType);
        int databaseSizeBeforeUpdate = sizeTypeRepository.findAll().size();

        // Update the sizeType
        SizeType updatedSizeType = sizeTypeRepository.findOne(sizeType.getId());
        updatedSizeType
                .description(UPDATED_DESCRIPTION);
        SizeTypeDTO sizeTypeDTO = sizeTypeMapper.sizeTypeToSizeTypeDTO(updatedSizeType);

        restSizeTypeMockMvc.perform(put("/api/size-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sizeTypeDTO)))
            .andExpect(status().isOk());

        // Validate the SizeType in the database
        List<SizeType> sizeTypeList = sizeTypeRepository.findAll();
        assertThat(sizeTypeList).hasSize(databaseSizeBeforeUpdate);
        SizeType testSizeType = sizeTypeList.get(sizeTypeList.size() - 1);
        assertThat(testSizeType.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingSizeType() throws Exception {
        int databaseSizeBeforeUpdate = sizeTypeRepository.findAll().size();

        // Create the SizeType
        SizeTypeDTO sizeTypeDTO = sizeTypeMapper.sizeTypeToSizeTypeDTO(sizeType);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSizeTypeMockMvc.perform(put("/api/size-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sizeTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the SizeType in the database
        List<SizeType> sizeTypeList = sizeTypeRepository.findAll();
        assertThat(sizeTypeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteSizeType() throws Exception {
        // Initialize the database
        sizeTypeRepository.saveAndFlush(sizeType);
        int databaseSizeBeforeDelete = sizeTypeRepository.findAll().size();

        // Get the sizeType
        restSizeTypeMockMvc.perform(delete("/api/size-types/{id}", sizeType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<SizeType> sizeTypeList = sizeTypeRepository.findAll();
        assertThat(sizeTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
