package com.sit.web.rest;

import com.sit.SitApp;

import com.sit.domain.PriceCategory;
import com.sit.repository.tenant.PriceCategoryRepository;
import com.sit.service.PriceCategoryService;
import com.sit.service.dto.PriceCategoryDTO;
import com.sit.service.mapper.PriceCategoryMapper;

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
 * Test class for the PriceCategoryResource REST controller.
 *
 * @see PriceCategoryResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SitApp.class)
public class PriceCategoryResourceIntTest {

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Double DEFAULT_PERCENTAGE_DISCOUNT = 1D;
    private static final Double UPDATED_PERCENTAGE_DISCOUNT = 2D;

    private static final Double DEFAULT_AMOUNT_DISCOUNT = 1D;
    private static final Double UPDATED_AMOUNT_DISCOUNT = 2D;

    @Inject
    private PriceCategoryRepository priceCategoryRepository;

    @Inject
    private PriceCategoryMapper priceCategoryMapper;

    @Inject
    private PriceCategoryService priceCategoryService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restPriceCategoryMockMvc;

    private PriceCategory priceCategory;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PriceCategoryResource priceCategoryResource = new PriceCategoryResource();
        ReflectionTestUtils.setField(priceCategoryResource, "priceCategoryService", priceCategoryService);
        this.restPriceCategoryMockMvc = MockMvcBuilders.standaloneSetup(priceCategoryResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PriceCategory createEntity(EntityManager em) {
        PriceCategory priceCategory = new PriceCategory()
                .description(DEFAULT_DESCRIPTION)
                .percentageDiscount(DEFAULT_PERCENTAGE_DISCOUNT)
                .amountDiscount(DEFAULT_AMOUNT_DISCOUNT);
        return priceCategory;
    }

    @Before
    public void initTest() {
        priceCategory = createEntity(em);
    }

    @Test
    @Transactional
    public void createPriceCategory() throws Exception {
        int databaseSizeBeforeCreate = priceCategoryRepository.findAll().size();

        // Create the PriceCategory
        PriceCategoryDTO priceCategoryDTO = priceCategoryMapper.priceCategoryToPriceCategoryDTO(priceCategory);

        restPriceCategoryMockMvc.perform(post("/api/price-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(priceCategoryDTO)))
            .andExpect(status().isCreated());

        // Validate the PriceCategory in the database
        List<PriceCategory> priceCategoryList = priceCategoryRepository.findAll();
        assertThat(priceCategoryList).hasSize(databaseSizeBeforeCreate + 1);
        PriceCategory testPriceCategory = priceCategoryList.get(priceCategoryList.size() - 1);
        assertThat(testPriceCategory.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testPriceCategory.getPercentageDiscount()).isEqualTo(DEFAULT_PERCENTAGE_DISCOUNT);
        assertThat(testPriceCategory.getAmountDiscount()).isEqualTo(DEFAULT_AMOUNT_DISCOUNT);
    }

    @Test
    @Transactional
    public void createPriceCategoryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = priceCategoryRepository.findAll().size();

        // Create the PriceCategory with an existing ID
        PriceCategory existingPriceCategory = new PriceCategory();
        existingPriceCategory.setId(1L);
        PriceCategoryDTO existingPriceCategoryDTO = priceCategoryMapper.priceCategoryToPriceCategoryDTO(existingPriceCategory);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPriceCategoryMockMvc.perform(post("/api/price-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingPriceCategoryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<PriceCategory> priceCategoryList = priceCategoryRepository.findAll();
        assertThat(priceCategoryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = priceCategoryRepository.findAll().size();
        // set the field null
        priceCategory.setDescription(null);

        // Create the PriceCategory, which fails.
        PriceCategoryDTO priceCategoryDTO = priceCategoryMapper.priceCategoryToPriceCategoryDTO(priceCategory);

        restPriceCategoryMockMvc.perform(post("/api/price-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(priceCategoryDTO)))
            .andExpect(status().isBadRequest());

        List<PriceCategory> priceCategoryList = priceCategoryRepository.findAll();
        assertThat(priceCategoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPriceCategories() throws Exception {
        // Initialize the database
        priceCategoryRepository.saveAndFlush(priceCategory);

        // Get all the priceCategoryList
        restPriceCategoryMockMvc.perform(get("/api/price-categories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(priceCategory.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].percentageDiscount").value(hasItem(DEFAULT_PERCENTAGE_DISCOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].amountDiscount").value(hasItem(DEFAULT_AMOUNT_DISCOUNT.doubleValue())));
    }

    @Test
    @Transactional
    public void getPriceCategory() throws Exception {
        // Initialize the database
        priceCategoryRepository.saveAndFlush(priceCategory);

        // Get the priceCategory
        restPriceCategoryMockMvc.perform(get("/api/price-categories/{id}", priceCategory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(priceCategory.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.percentageDiscount").value(DEFAULT_PERCENTAGE_DISCOUNT.doubleValue()))
            .andExpect(jsonPath("$.amountDiscount").value(DEFAULT_AMOUNT_DISCOUNT.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPriceCategory() throws Exception {
        // Get the priceCategory
        restPriceCategoryMockMvc.perform(get("/api/price-categories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePriceCategory() throws Exception {
        // Initialize the database
        priceCategoryRepository.saveAndFlush(priceCategory);
        int databaseSizeBeforeUpdate = priceCategoryRepository.findAll().size();

        // Update the priceCategory
        PriceCategory updatedPriceCategory = priceCategoryRepository.findOne(priceCategory.getId());
        updatedPriceCategory
                .description(UPDATED_DESCRIPTION)
                .percentageDiscount(UPDATED_PERCENTAGE_DISCOUNT)
                .amountDiscount(UPDATED_AMOUNT_DISCOUNT);
        PriceCategoryDTO priceCategoryDTO = priceCategoryMapper.priceCategoryToPriceCategoryDTO(updatedPriceCategory);

        restPriceCategoryMockMvc.perform(put("/api/price-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(priceCategoryDTO)))
            .andExpect(status().isOk());

        // Validate the PriceCategory in the database
        List<PriceCategory> priceCategoryList = priceCategoryRepository.findAll();
        assertThat(priceCategoryList).hasSize(databaseSizeBeforeUpdate);
        PriceCategory testPriceCategory = priceCategoryList.get(priceCategoryList.size() - 1);
        assertThat(testPriceCategory.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testPriceCategory.getPercentageDiscount()).isEqualTo(UPDATED_PERCENTAGE_DISCOUNT);
        assertThat(testPriceCategory.getAmountDiscount()).isEqualTo(UPDATED_AMOUNT_DISCOUNT);
    }

    @Test
    @Transactional
    public void updateNonExistingPriceCategory() throws Exception {
        int databaseSizeBeforeUpdate = priceCategoryRepository.findAll().size();

        // Create the PriceCategory
        PriceCategoryDTO priceCategoryDTO = priceCategoryMapper.priceCategoryToPriceCategoryDTO(priceCategory);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPriceCategoryMockMvc.perform(put("/api/price-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(priceCategoryDTO)))
            .andExpect(status().isCreated());

        // Validate the PriceCategory in the database
        List<PriceCategory> priceCategoryList = priceCategoryRepository.findAll();
        assertThat(priceCategoryList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePriceCategory() throws Exception {
        // Initialize the database
        priceCategoryRepository.saveAndFlush(priceCategory);
        int databaseSizeBeforeDelete = priceCategoryRepository.findAll().size();

        // Get the priceCategory
        restPriceCategoryMockMvc.perform(delete("/api/price-categories/{id}", priceCategory.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PriceCategory> priceCategoryList = priceCategoryRepository.findAll();
        assertThat(priceCategoryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
