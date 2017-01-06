package com.sit.web.rest;

import com.sit.SitApp;

import com.sit.domain.AlterationPrice;
import com.sit.repository.tenant.AlterationPriceRepository;
import com.sit.service.AlterationPriceService;
import com.sit.service.dto.AlterationPriceDTO;
import com.sit.service.mapper.AlterationPriceMapper;

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
 * Test class for the AlterationPriceResource REST controller.
 *
 * @see AlterationPriceResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SitApp.class)
public class AlterationPriceResourceIntTest {

    private static final Double DEFAULT_PRICE = 1D;
    private static final Double UPDATED_PRICE = 2D;

    @Inject
    private AlterationPriceRepository alterationPriceRepository;

    @Inject
    private AlterationPriceMapper alterationPriceMapper;

    @Inject
    private AlterationPriceService alterationPriceService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restAlterationPriceMockMvc;

    private AlterationPrice alterationPrice;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        AlterationPriceResource alterationPriceResource = new AlterationPriceResource();
        ReflectionTestUtils.setField(alterationPriceResource, "alterationPriceService", alterationPriceService);
        this.restAlterationPriceMockMvc = MockMvcBuilders.standaloneSetup(alterationPriceResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AlterationPrice createEntity(EntityManager em) {
        AlterationPrice alterationPrice = new AlterationPrice()
                .price(DEFAULT_PRICE);
        return alterationPrice;
    }

    @Before
    public void initTest() {
        alterationPrice = createEntity(em);
    }

    @Test
    @Transactional
    public void createAlterationPrice() throws Exception {
        int databaseSizeBeforeCreate = alterationPriceRepository.findAll().size();

        // Create the AlterationPrice
        AlterationPriceDTO alterationPriceDTO = alterationPriceMapper.alterationPriceToAlterationPriceDTO(alterationPrice);

        restAlterationPriceMockMvc.perform(post("/api/alteration-prices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(alterationPriceDTO)))
            .andExpect(status().isCreated());

        // Validate the AlterationPrice in the database
        List<AlterationPrice> alterationPriceList = alterationPriceRepository.findAll();
        assertThat(alterationPriceList).hasSize(databaseSizeBeforeCreate + 1);
        AlterationPrice testAlterationPrice = alterationPriceList.get(alterationPriceList.size() - 1);
        assertThat(testAlterationPrice.getPrice()).isEqualTo(DEFAULT_PRICE);
    }

    @Test
    @Transactional
    public void createAlterationPriceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = alterationPriceRepository.findAll().size();

        // Create the AlterationPrice with an existing ID
        AlterationPrice existingAlterationPrice = new AlterationPrice();
        existingAlterationPrice.setId(1L);
        AlterationPriceDTO existingAlterationPriceDTO = alterationPriceMapper.alterationPriceToAlterationPriceDTO(existingAlterationPrice);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAlterationPriceMockMvc.perform(post("/api/alteration-prices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingAlterationPriceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<AlterationPrice> alterationPriceList = alterationPriceRepository.findAll();
        assertThat(alterationPriceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllAlterationPrices() throws Exception {
        // Initialize the database
        alterationPriceRepository.saveAndFlush(alterationPrice);

        // Get all the alterationPriceList
        restAlterationPriceMockMvc.perform(get("/api/alteration-prices?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(alterationPrice.getId().intValue())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.doubleValue())));
    }

    @Test
    @Transactional
    public void getAlterationPrice() throws Exception {
        // Initialize the database
        alterationPriceRepository.saveAndFlush(alterationPrice);

        // Get the alterationPrice
        restAlterationPriceMockMvc.perform(get("/api/alteration-prices/{id}", alterationPrice.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(alterationPrice.getId().intValue()))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingAlterationPrice() throws Exception {
        // Get the alterationPrice
        restAlterationPriceMockMvc.perform(get("/api/alteration-prices/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAlterationPrice() throws Exception {
        // Initialize the database
        alterationPriceRepository.saveAndFlush(alterationPrice);
        int databaseSizeBeforeUpdate = alterationPriceRepository.findAll().size();

        // Update the alterationPrice
        AlterationPrice updatedAlterationPrice = alterationPriceRepository.findOne(alterationPrice.getId());
        updatedAlterationPrice
                .price(UPDATED_PRICE);
        AlterationPriceDTO alterationPriceDTO = alterationPriceMapper.alterationPriceToAlterationPriceDTO(updatedAlterationPrice);

        restAlterationPriceMockMvc.perform(put("/api/alteration-prices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(alterationPriceDTO)))
            .andExpect(status().isOk());

        // Validate the AlterationPrice in the database
        List<AlterationPrice> alterationPriceList = alterationPriceRepository.findAll();
        assertThat(alterationPriceList).hasSize(databaseSizeBeforeUpdate);
        AlterationPrice testAlterationPrice = alterationPriceList.get(alterationPriceList.size() - 1);
        assertThat(testAlterationPrice.getPrice()).isEqualTo(UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void updateNonExistingAlterationPrice() throws Exception {
        int databaseSizeBeforeUpdate = alterationPriceRepository.findAll().size();

        // Create the AlterationPrice
        AlterationPriceDTO alterationPriceDTO = alterationPriceMapper.alterationPriceToAlterationPriceDTO(alterationPrice);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAlterationPriceMockMvc.perform(put("/api/alteration-prices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(alterationPriceDTO)))
            .andExpect(status().isCreated());

        // Validate the AlterationPrice in the database
        List<AlterationPrice> alterationPriceList = alterationPriceRepository.findAll();
        assertThat(alterationPriceList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteAlterationPrice() throws Exception {
        // Initialize the database
        alterationPriceRepository.saveAndFlush(alterationPrice);
        int databaseSizeBeforeDelete = alterationPriceRepository.findAll().size();

        // Get the alterationPrice
        restAlterationPriceMockMvc.perform(delete("/api/alteration-prices/{id}", alterationPrice.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<AlterationPrice> alterationPriceList = alterationPriceRepository.findAll();
        assertThat(alterationPriceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
