package com.sit.web.rest;

import com.sit.SitApp;

import com.sit.domain.Sku;
import com.sit.repository.tenant.SkuRepository;
import com.sit.service.SkuService;
import com.sit.service.dto.SkuDTO;
import com.sit.service.mapper.SkuMapper;

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
 * Test class for the SkuResource REST controller.
 *
 * @see SkuResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SitApp.class)
public class SkuResourceIntTest {

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_SKU_CODE = "AAAAAAAAAA";
    private static final String UPDATED_SKU_CODE = "BBBBBBBBBB";

    @Inject
    private SkuRepository skuRepository;

    @Inject
    private SkuMapper skuMapper;

    @Inject
    private SkuService skuService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restSkuMockMvc;

    private Sku sku;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SkuResource skuResource = new SkuResource();
        ReflectionTestUtils.setField(skuResource, "skuService", skuService);
        this.restSkuMockMvc = MockMvcBuilders.standaloneSetup(skuResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Sku createEntity(EntityManager em) {
        Sku sku = new Sku()
                .description(DEFAULT_DESCRIPTION)
                .skuCode(DEFAULT_SKU_CODE);
        return sku;
    }

    @Before
    public void initTest() {
        sku = createEntity(em);
    }

    @Test
    @Transactional
    public void createSku() throws Exception {
        int databaseSizeBeforeCreate = skuRepository.findAll().size();

        // Create the Sku
        SkuDTO skuDTO = skuMapper.skuToSkuDTO(sku);

        restSkuMockMvc.perform(post("/api/skus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(skuDTO)))
            .andExpect(status().isCreated());

        // Validate the Sku in the database
        List<Sku> skuList = skuRepository.findAll();
        assertThat(skuList).hasSize(databaseSizeBeforeCreate + 1);
        Sku testSku = skuList.get(skuList.size() - 1);
        assertThat(testSku.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testSku.getSkuCode()).isEqualTo(DEFAULT_SKU_CODE);
    }

    @Test
    @Transactional
    public void createSkuWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = skuRepository.findAll().size();

        // Create the Sku with an existing ID
        Sku existingSku = new Sku();
        existingSku.setId(1L);
        SkuDTO existingSkuDTO = skuMapper.skuToSkuDTO(existingSku);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSkuMockMvc.perform(post("/api/skus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingSkuDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Sku> skuList = skuRepository.findAll();
        assertThat(skuList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = skuRepository.findAll().size();
        // set the field null
        sku.setDescription(null);

        // Create the Sku, which fails.
        SkuDTO skuDTO = skuMapper.skuToSkuDTO(sku);

        restSkuMockMvc.perform(post("/api/skus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(skuDTO)))
            .andExpect(status().isBadRequest());

        List<Sku> skuList = skuRepository.findAll();
        assertThat(skuList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSkuCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = skuRepository.findAll().size();
        // set the field null
        sku.setSkuCode(null);

        // Create the Sku, which fails.
        SkuDTO skuDTO = skuMapper.skuToSkuDTO(sku);

        restSkuMockMvc.perform(post("/api/skus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(skuDTO)))
            .andExpect(status().isBadRequest());

        List<Sku> skuList = skuRepository.findAll();
        assertThat(skuList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSkus() throws Exception {
        // Initialize the database
        skuRepository.saveAndFlush(sku);

        // Get all the skuList
        restSkuMockMvc.perform(get("/api/skus?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sku.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].skuCode").value(hasItem(DEFAULT_SKU_CODE.toString())));
    }

    @Test
    @Transactional
    public void getSku() throws Exception {
        // Initialize the database
        skuRepository.saveAndFlush(sku);

        // Get the sku
        restSkuMockMvc.perform(get("/api/skus/{id}", sku.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(sku.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.skuCode").value(DEFAULT_SKU_CODE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSku() throws Exception {
        // Get the sku
        restSkuMockMvc.perform(get("/api/skus/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSku() throws Exception {
        // Initialize the database
        skuRepository.saveAndFlush(sku);
        int databaseSizeBeforeUpdate = skuRepository.findAll().size();

        // Update the sku
        Sku updatedSku = skuRepository.findOne(sku.getId());
        updatedSku
                .description(UPDATED_DESCRIPTION)
                .skuCode(UPDATED_SKU_CODE);
        SkuDTO skuDTO = skuMapper.skuToSkuDTO(updatedSku);

        restSkuMockMvc.perform(put("/api/skus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(skuDTO)))
            .andExpect(status().isOk());

        // Validate the Sku in the database
        List<Sku> skuList = skuRepository.findAll();
        assertThat(skuList).hasSize(databaseSizeBeforeUpdate);
        Sku testSku = skuList.get(skuList.size() - 1);
        assertThat(testSku.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testSku.getSkuCode()).isEqualTo(UPDATED_SKU_CODE);
    }

    @Test
    @Transactional
    public void updateNonExistingSku() throws Exception {
        int databaseSizeBeforeUpdate = skuRepository.findAll().size();

        // Create the Sku
        SkuDTO skuDTO = skuMapper.skuToSkuDTO(sku);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSkuMockMvc.perform(put("/api/skus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(skuDTO)))
            .andExpect(status().isCreated());

        // Validate the Sku in the database
        List<Sku> skuList = skuRepository.findAll();
        assertThat(skuList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteSku() throws Exception {
        // Initialize the database
        skuRepository.saveAndFlush(sku);
        int databaseSizeBeforeDelete = skuRepository.findAll().size();

        // Get the sku
        restSkuMockMvc.perform(delete("/api/skus/{id}", sku.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Sku> skuList = skuRepository.findAll();
        assertThat(skuList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
