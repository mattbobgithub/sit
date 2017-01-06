package com.sit.web.rest;

import com.sit.SitApp;

import com.sit.domain.AlterationDisplayType;
import com.sit.repository.tenant.AlterationDisplayTypeRepository;
import com.sit.service.AlterationDisplayTypeService;
import com.sit.service.dto.AlterationDisplayTypeDTO;
import com.sit.service.mapper.AlterationDisplayTypeMapper;

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

import com.sit.domain.enumeration.MeasurementType;
/**
 * Test class for the AlterationDisplayTypeResource REST controller.
 *
 * @see AlterationDisplayTypeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SitApp.class)
public class AlterationDisplayTypeResourceIntTest {

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final MeasurementType DEFAULT_MEASUREMENT_TYPE = MeasurementType.NONE;
    private static final MeasurementType UPDATED_MEASUREMENT_TYPE = MeasurementType.PANTS;

    private static final Boolean DEFAULT_DISPLAYPRICE = false;
    private static final Boolean UPDATED_DISPLAYPRICE = true;

    private static final Boolean DEFAULT_DISPLAYTIME = false;
    private static final Boolean UPDATED_DISPLAYTIME = true;

    private static final Boolean DEFAULT_DISPLAYQUANTITY = false;
    private static final Boolean UPDATED_DISPLAYQUANTITY = true;

    private static final Boolean DEFAULT_DISPLAYMEASUREMENT_1 = false;
    private static final Boolean UPDATED_DISPLAYMEASUREMENT_1 = true;

    private static final Boolean DEFAULT_DISPLAYMEASUREMENT_2 = false;
    private static final Boolean UPDATED_DISPLAYMEASUREMENT_2 = true;

    private static final Boolean DEFAULT_ENABLEPRICE = false;
    private static final Boolean UPDATED_ENABLEPRICE = true;

    private static final Boolean DEFAULT_ENABLETIME = false;
    private static final Boolean UPDATED_ENABLETIME = true;

    private static final Boolean DEFAULT_ENABLEQUANTITY = false;
    private static final Boolean UPDATED_ENABLEQUANTITY = true;

    private static final Boolean DEFAULT_ENABLEMEASUREMENT_1 = false;
    private static final Boolean UPDATED_ENABLEMEASUREMENT_1 = true;

    private static final Boolean DEFAULT_ENABLEMEASUREMENT_2 = false;
    private static final Boolean UPDATED_ENABLEMEASUREMENT_2 = true;

    private static final Double DEFAULT_DEFAULT_PRICE = 1D;
    private static final Double UPDATED_DEFAULT_PRICE = 2D;

    private static final Integer DEFAULT_DEFAULT_TIME = 1;
    private static final Integer UPDATED_DEFAULT_TIME = 2;

    private static final Integer DEFAULT_DEFAULT_QUANTITY = 1;
    private static final Integer UPDATED_DEFAULT_QUANTITY = 2;

    private static final Double DEFAULT_DEFAULT_MEASUREMENT_1 = 1D;
    private static final Double UPDATED_DEFAULT_MEASUREMENT_1 = 2D;

    private static final Double DEFAULT_DEFAULT_MEASUREMENT_2 = 1D;
    private static final Double UPDATED_DEFAULT_MEASUREMENT_2 = 2D;

    private static final Double DEFAULT_MAX_MEASUREMENT_1 = 1D;
    private static final Double UPDATED_MAX_MEASUREMENT_1 = 2D;

    private static final Double DEFAULT_MAX_MEASUREMENT_2 = 1D;
    private static final Double UPDATED_MAX_MEASUREMENT_2 = 2D;

    private static final Double DEFAULT_MIN_MEASUREMENT_1 = 1D;
    private static final Double UPDATED_MIN_MEASUREMENT_1 = 2D;

    private static final Double DEFAULT_MIN_MEASUREMENT_2 = 1D;
    private static final Double UPDATED_MIN_MEASUREMENT_2 = 2D;

    @Inject
    private AlterationDisplayTypeRepository alterationDisplayTypeRepository;

    @Inject
    private AlterationDisplayTypeMapper alterationDisplayTypeMapper;

    @Inject
    private AlterationDisplayTypeService alterationDisplayTypeService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restAlterationDisplayTypeMockMvc;

    private AlterationDisplayType alterationDisplayType;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        AlterationDisplayTypeResource alterationDisplayTypeResource = new AlterationDisplayTypeResource();
        ReflectionTestUtils.setField(alterationDisplayTypeResource, "alterationDisplayTypeService", alterationDisplayTypeService);
        this.restAlterationDisplayTypeMockMvc = MockMvcBuilders.standaloneSetup(alterationDisplayTypeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AlterationDisplayType createEntity(EntityManager em) {
        AlterationDisplayType alterationDisplayType = new AlterationDisplayType()
                .description(DEFAULT_DESCRIPTION)
                .measurementType(DEFAULT_MEASUREMENT_TYPE)
                .displayprice(DEFAULT_DISPLAYPRICE)
                .displaytime(DEFAULT_DISPLAYTIME)
                .displayquantity(DEFAULT_DISPLAYQUANTITY)
                .displaymeasurement1(DEFAULT_DISPLAYMEASUREMENT_1)
                .displaymeasurement2(DEFAULT_DISPLAYMEASUREMENT_2)
                .enableprice(DEFAULT_ENABLEPRICE)
                .enabletime(DEFAULT_ENABLETIME)
                .enablequantity(DEFAULT_ENABLEQUANTITY)
                .enablemeasurement1(DEFAULT_ENABLEMEASUREMENT_1)
                .enablemeasurement2(DEFAULT_ENABLEMEASUREMENT_2)
                .defaultPrice(DEFAULT_DEFAULT_PRICE)
                .defaultTime(DEFAULT_DEFAULT_TIME)
                .defaultQuantity(DEFAULT_DEFAULT_QUANTITY)
                .defaultMeasurement1(DEFAULT_DEFAULT_MEASUREMENT_1)
                .defaultMeasurement2(DEFAULT_DEFAULT_MEASUREMENT_2)
                .maxMeasurement1(DEFAULT_MAX_MEASUREMENT_1)
                .maxMeasurement2(DEFAULT_MAX_MEASUREMENT_2)
                .minMeasurement1(DEFAULT_MIN_MEASUREMENT_1)
                .minMeasurement2(DEFAULT_MIN_MEASUREMENT_2);
        return alterationDisplayType;
    }

    @Before
    public void initTest() {
        alterationDisplayType = createEntity(em);
    }

    @Test
    @Transactional
    public void createAlterationDisplayType() throws Exception {
        int databaseSizeBeforeCreate = alterationDisplayTypeRepository.findAll().size();

        // Create the AlterationDisplayType
        AlterationDisplayTypeDTO alterationDisplayTypeDTO = alterationDisplayTypeMapper.alterationDisplayTypeToAlterationDisplayTypeDTO(alterationDisplayType);

        restAlterationDisplayTypeMockMvc.perform(post("/api/alteration-display-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(alterationDisplayTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the AlterationDisplayType in the database
        List<AlterationDisplayType> alterationDisplayTypeList = alterationDisplayTypeRepository.findAll();
        assertThat(alterationDisplayTypeList).hasSize(databaseSizeBeforeCreate + 1);
        AlterationDisplayType testAlterationDisplayType = alterationDisplayTypeList.get(alterationDisplayTypeList.size() - 1);
        assertThat(testAlterationDisplayType.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testAlterationDisplayType.getMeasurementType()).isEqualTo(DEFAULT_MEASUREMENT_TYPE);
        assertThat(testAlterationDisplayType.isDisplayprice()).isEqualTo(DEFAULT_DISPLAYPRICE);
        assertThat(testAlterationDisplayType.isDisplaytime()).isEqualTo(DEFAULT_DISPLAYTIME);
        assertThat(testAlterationDisplayType.isDisplayquantity()).isEqualTo(DEFAULT_DISPLAYQUANTITY);
        assertThat(testAlterationDisplayType.isDisplaymeasurement1()).isEqualTo(DEFAULT_DISPLAYMEASUREMENT_1);
        assertThat(testAlterationDisplayType.isDisplaymeasurement2()).isEqualTo(DEFAULT_DISPLAYMEASUREMENT_2);
        assertThat(testAlterationDisplayType.isEnableprice()).isEqualTo(DEFAULT_ENABLEPRICE);
        assertThat(testAlterationDisplayType.isEnabletime()).isEqualTo(DEFAULT_ENABLETIME);
        assertThat(testAlterationDisplayType.isEnablequantity()).isEqualTo(DEFAULT_ENABLEQUANTITY);
        assertThat(testAlterationDisplayType.isEnablemeasurement1()).isEqualTo(DEFAULT_ENABLEMEASUREMENT_1);
        assertThat(testAlterationDisplayType.isEnablemeasurement2()).isEqualTo(DEFAULT_ENABLEMEASUREMENT_2);
        assertThat(testAlterationDisplayType.getDefaultPrice()).isEqualTo(DEFAULT_DEFAULT_PRICE);
        assertThat(testAlterationDisplayType.getDefaultTime()).isEqualTo(DEFAULT_DEFAULT_TIME);
        assertThat(testAlterationDisplayType.getDefaultQuantity()).isEqualTo(DEFAULT_DEFAULT_QUANTITY);
        assertThat(testAlterationDisplayType.getDefaultMeasurement1()).isEqualTo(DEFAULT_DEFAULT_MEASUREMENT_1);
        assertThat(testAlterationDisplayType.getDefaultMeasurement2()).isEqualTo(DEFAULT_DEFAULT_MEASUREMENT_2);
        assertThat(testAlterationDisplayType.getMaxMeasurement1()).isEqualTo(DEFAULT_MAX_MEASUREMENT_1);
        assertThat(testAlterationDisplayType.getMaxMeasurement2()).isEqualTo(DEFAULT_MAX_MEASUREMENT_2);
        assertThat(testAlterationDisplayType.getMinMeasurement1()).isEqualTo(DEFAULT_MIN_MEASUREMENT_1);
        assertThat(testAlterationDisplayType.getMinMeasurement2()).isEqualTo(DEFAULT_MIN_MEASUREMENT_2);
    }

    @Test
    @Transactional
    public void createAlterationDisplayTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = alterationDisplayTypeRepository.findAll().size();

        // Create the AlterationDisplayType with an existing ID
        AlterationDisplayType existingAlterationDisplayType = new AlterationDisplayType();
        existingAlterationDisplayType.setId(1L);
        AlterationDisplayTypeDTO existingAlterationDisplayTypeDTO = alterationDisplayTypeMapper.alterationDisplayTypeToAlterationDisplayTypeDTO(existingAlterationDisplayType);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAlterationDisplayTypeMockMvc.perform(post("/api/alteration-display-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingAlterationDisplayTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<AlterationDisplayType> alterationDisplayTypeList = alterationDisplayTypeRepository.findAll();
        assertThat(alterationDisplayTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = alterationDisplayTypeRepository.findAll().size();
        // set the field null
        alterationDisplayType.setDescription(null);

        // Create the AlterationDisplayType, which fails.
        AlterationDisplayTypeDTO alterationDisplayTypeDTO = alterationDisplayTypeMapper.alterationDisplayTypeToAlterationDisplayTypeDTO(alterationDisplayType);

        restAlterationDisplayTypeMockMvc.perform(post("/api/alteration-display-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(alterationDisplayTypeDTO)))
            .andExpect(status().isBadRequest());

        List<AlterationDisplayType> alterationDisplayTypeList = alterationDisplayTypeRepository.findAll();
        assertThat(alterationDisplayTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAlterationDisplayTypes() throws Exception {
        // Initialize the database
        alterationDisplayTypeRepository.saveAndFlush(alterationDisplayType);

        // Get all the alterationDisplayTypeList
        restAlterationDisplayTypeMockMvc.perform(get("/api/alteration-display-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(alterationDisplayType.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].measurementType").value(hasItem(DEFAULT_MEASUREMENT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].displayprice").value(hasItem(DEFAULT_DISPLAYPRICE.booleanValue())))
            .andExpect(jsonPath("$.[*].displaytime").value(hasItem(DEFAULT_DISPLAYTIME.booleanValue())))
            .andExpect(jsonPath("$.[*].displayquantity").value(hasItem(DEFAULT_DISPLAYQUANTITY.booleanValue())))
            .andExpect(jsonPath("$.[*].displaymeasurement1").value(hasItem(DEFAULT_DISPLAYMEASUREMENT_1.booleanValue())))
            .andExpect(jsonPath("$.[*].displaymeasurement2").value(hasItem(DEFAULT_DISPLAYMEASUREMENT_2.booleanValue())))
            .andExpect(jsonPath("$.[*].enableprice").value(hasItem(DEFAULT_ENABLEPRICE.booleanValue())))
            .andExpect(jsonPath("$.[*].enabletime").value(hasItem(DEFAULT_ENABLETIME.booleanValue())))
            .andExpect(jsonPath("$.[*].enablequantity").value(hasItem(DEFAULT_ENABLEQUANTITY.booleanValue())))
            .andExpect(jsonPath("$.[*].enablemeasurement1").value(hasItem(DEFAULT_ENABLEMEASUREMENT_1.booleanValue())))
            .andExpect(jsonPath("$.[*].enablemeasurement2").value(hasItem(DEFAULT_ENABLEMEASUREMENT_2.booleanValue())))
            .andExpect(jsonPath("$.[*].defaultPrice").value(hasItem(DEFAULT_DEFAULT_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].defaultTime").value(hasItem(DEFAULT_DEFAULT_TIME)))
            .andExpect(jsonPath("$.[*].defaultQuantity").value(hasItem(DEFAULT_DEFAULT_QUANTITY)))
            .andExpect(jsonPath("$.[*].defaultMeasurement1").value(hasItem(DEFAULT_DEFAULT_MEASUREMENT_1.doubleValue())))
            .andExpect(jsonPath("$.[*].defaultMeasurement2").value(hasItem(DEFAULT_DEFAULT_MEASUREMENT_2.doubleValue())))
            .andExpect(jsonPath("$.[*].maxMeasurement1").value(hasItem(DEFAULT_MAX_MEASUREMENT_1.doubleValue())))
            .andExpect(jsonPath("$.[*].maxMeasurement2").value(hasItem(DEFAULT_MAX_MEASUREMENT_2.doubleValue())))
            .andExpect(jsonPath("$.[*].minMeasurement1").value(hasItem(DEFAULT_MIN_MEASUREMENT_1.doubleValue())))
            .andExpect(jsonPath("$.[*].minMeasurement2").value(hasItem(DEFAULT_MIN_MEASUREMENT_2.doubleValue())));
    }

    @Test
    @Transactional
    public void getAlterationDisplayType() throws Exception {
        // Initialize the database
        alterationDisplayTypeRepository.saveAndFlush(alterationDisplayType);

        // Get the alterationDisplayType
        restAlterationDisplayTypeMockMvc.perform(get("/api/alteration-display-types/{id}", alterationDisplayType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(alterationDisplayType.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.measurementType").value(DEFAULT_MEASUREMENT_TYPE.toString()))
            .andExpect(jsonPath("$.displayprice").value(DEFAULT_DISPLAYPRICE.booleanValue()))
            .andExpect(jsonPath("$.displaytime").value(DEFAULT_DISPLAYTIME.booleanValue()))
            .andExpect(jsonPath("$.displayquantity").value(DEFAULT_DISPLAYQUANTITY.booleanValue()))
            .andExpect(jsonPath("$.displaymeasurement1").value(DEFAULT_DISPLAYMEASUREMENT_1.booleanValue()))
            .andExpect(jsonPath("$.displaymeasurement2").value(DEFAULT_DISPLAYMEASUREMENT_2.booleanValue()))
            .andExpect(jsonPath("$.enableprice").value(DEFAULT_ENABLEPRICE.booleanValue()))
            .andExpect(jsonPath("$.enabletime").value(DEFAULT_ENABLETIME.booleanValue()))
            .andExpect(jsonPath("$.enablequantity").value(DEFAULT_ENABLEQUANTITY.booleanValue()))
            .andExpect(jsonPath("$.enablemeasurement1").value(DEFAULT_ENABLEMEASUREMENT_1.booleanValue()))
            .andExpect(jsonPath("$.enablemeasurement2").value(DEFAULT_ENABLEMEASUREMENT_2.booleanValue()))
            .andExpect(jsonPath("$.defaultPrice").value(DEFAULT_DEFAULT_PRICE.doubleValue()))
            .andExpect(jsonPath("$.defaultTime").value(DEFAULT_DEFAULT_TIME))
            .andExpect(jsonPath("$.defaultQuantity").value(DEFAULT_DEFAULT_QUANTITY))
            .andExpect(jsonPath("$.defaultMeasurement1").value(DEFAULT_DEFAULT_MEASUREMENT_1.doubleValue()))
            .andExpect(jsonPath("$.defaultMeasurement2").value(DEFAULT_DEFAULT_MEASUREMENT_2.doubleValue()))
            .andExpect(jsonPath("$.maxMeasurement1").value(DEFAULT_MAX_MEASUREMENT_1.doubleValue()))
            .andExpect(jsonPath("$.maxMeasurement2").value(DEFAULT_MAX_MEASUREMENT_2.doubleValue()))
            .andExpect(jsonPath("$.minMeasurement1").value(DEFAULT_MIN_MEASUREMENT_1.doubleValue()))
            .andExpect(jsonPath("$.minMeasurement2").value(DEFAULT_MIN_MEASUREMENT_2.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingAlterationDisplayType() throws Exception {
        // Get the alterationDisplayType
        restAlterationDisplayTypeMockMvc.perform(get("/api/alteration-display-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAlterationDisplayType() throws Exception {
        // Initialize the database
        alterationDisplayTypeRepository.saveAndFlush(alterationDisplayType);
        int databaseSizeBeforeUpdate = alterationDisplayTypeRepository.findAll().size();

        // Update the alterationDisplayType
        AlterationDisplayType updatedAlterationDisplayType = alterationDisplayTypeRepository.findOne(alterationDisplayType.getId());
        updatedAlterationDisplayType
                .description(UPDATED_DESCRIPTION)
                .measurementType(UPDATED_MEASUREMENT_TYPE)
                .displayprice(UPDATED_DISPLAYPRICE)
                .displaytime(UPDATED_DISPLAYTIME)
                .displayquantity(UPDATED_DISPLAYQUANTITY)
                .displaymeasurement1(UPDATED_DISPLAYMEASUREMENT_1)
                .displaymeasurement2(UPDATED_DISPLAYMEASUREMENT_2)
                .enableprice(UPDATED_ENABLEPRICE)
                .enabletime(UPDATED_ENABLETIME)
                .enablequantity(UPDATED_ENABLEQUANTITY)
                .enablemeasurement1(UPDATED_ENABLEMEASUREMENT_1)
                .enablemeasurement2(UPDATED_ENABLEMEASUREMENT_2)
                .defaultPrice(UPDATED_DEFAULT_PRICE)
                .defaultTime(UPDATED_DEFAULT_TIME)
                .defaultQuantity(UPDATED_DEFAULT_QUANTITY)
                .defaultMeasurement1(UPDATED_DEFAULT_MEASUREMENT_1)
                .defaultMeasurement2(UPDATED_DEFAULT_MEASUREMENT_2)
                .maxMeasurement1(UPDATED_MAX_MEASUREMENT_1)
                .maxMeasurement2(UPDATED_MAX_MEASUREMENT_2)
                .minMeasurement1(UPDATED_MIN_MEASUREMENT_1)
                .minMeasurement2(UPDATED_MIN_MEASUREMENT_2);
        AlterationDisplayTypeDTO alterationDisplayTypeDTO = alterationDisplayTypeMapper.alterationDisplayTypeToAlterationDisplayTypeDTO(updatedAlterationDisplayType);

        restAlterationDisplayTypeMockMvc.perform(put("/api/alteration-display-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(alterationDisplayTypeDTO)))
            .andExpect(status().isOk());

        // Validate the AlterationDisplayType in the database
        List<AlterationDisplayType> alterationDisplayTypeList = alterationDisplayTypeRepository.findAll();
        assertThat(alterationDisplayTypeList).hasSize(databaseSizeBeforeUpdate);
        AlterationDisplayType testAlterationDisplayType = alterationDisplayTypeList.get(alterationDisplayTypeList.size() - 1);
        assertThat(testAlterationDisplayType.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testAlterationDisplayType.getMeasurementType()).isEqualTo(UPDATED_MEASUREMENT_TYPE);
        assertThat(testAlterationDisplayType.isDisplayprice()).isEqualTo(UPDATED_DISPLAYPRICE);
        assertThat(testAlterationDisplayType.isDisplaytime()).isEqualTo(UPDATED_DISPLAYTIME);
        assertThat(testAlterationDisplayType.isDisplayquantity()).isEqualTo(UPDATED_DISPLAYQUANTITY);
        assertThat(testAlterationDisplayType.isDisplaymeasurement1()).isEqualTo(UPDATED_DISPLAYMEASUREMENT_1);
        assertThat(testAlterationDisplayType.isDisplaymeasurement2()).isEqualTo(UPDATED_DISPLAYMEASUREMENT_2);
        assertThat(testAlterationDisplayType.isEnableprice()).isEqualTo(UPDATED_ENABLEPRICE);
        assertThat(testAlterationDisplayType.isEnabletime()).isEqualTo(UPDATED_ENABLETIME);
        assertThat(testAlterationDisplayType.isEnablequantity()).isEqualTo(UPDATED_ENABLEQUANTITY);
        assertThat(testAlterationDisplayType.isEnablemeasurement1()).isEqualTo(UPDATED_ENABLEMEASUREMENT_1);
        assertThat(testAlterationDisplayType.isEnablemeasurement2()).isEqualTo(UPDATED_ENABLEMEASUREMENT_2);
        assertThat(testAlterationDisplayType.getDefaultPrice()).isEqualTo(UPDATED_DEFAULT_PRICE);
        assertThat(testAlterationDisplayType.getDefaultTime()).isEqualTo(UPDATED_DEFAULT_TIME);
        assertThat(testAlterationDisplayType.getDefaultQuantity()).isEqualTo(UPDATED_DEFAULT_QUANTITY);
        assertThat(testAlterationDisplayType.getDefaultMeasurement1()).isEqualTo(UPDATED_DEFAULT_MEASUREMENT_1);
        assertThat(testAlterationDisplayType.getDefaultMeasurement2()).isEqualTo(UPDATED_DEFAULT_MEASUREMENT_2);
        assertThat(testAlterationDisplayType.getMaxMeasurement1()).isEqualTo(UPDATED_MAX_MEASUREMENT_1);
        assertThat(testAlterationDisplayType.getMaxMeasurement2()).isEqualTo(UPDATED_MAX_MEASUREMENT_2);
        assertThat(testAlterationDisplayType.getMinMeasurement1()).isEqualTo(UPDATED_MIN_MEASUREMENT_1);
        assertThat(testAlterationDisplayType.getMinMeasurement2()).isEqualTo(UPDATED_MIN_MEASUREMENT_2);
    }

    @Test
    @Transactional
    public void updateNonExistingAlterationDisplayType() throws Exception {
        int databaseSizeBeforeUpdate = alterationDisplayTypeRepository.findAll().size();

        // Create the AlterationDisplayType
        AlterationDisplayTypeDTO alterationDisplayTypeDTO = alterationDisplayTypeMapper.alterationDisplayTypeToAlterationDisplayTypeDTO(alterationDisplayType);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAlterationDisplayTypeMockMvc.perform(put("/api/alteration-display-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(alterationDisplayTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the AlterationDisplayType in the database
        List<AlterationDisplayType> alterationDisplayTypeList = alterationDisplayTypeRepository.findAll();
        assertThat(alterationDisplayTypeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteAlterationDisplayType() throws Exception {
        // Initialize the database
        alterationDisplayTypeRepository.saveAndFlush(alterationDisplayType);
        int databaseSizeBeforeDelete = alterationDisplayTypeRepository.findAll().size();

        // Get the alterationDisplayType
        restAlterationDisplayTypeMockMvc.perform(delete("/api/alteration-display-types/{id}", alterationDisplayType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<AlterationDisplayType> alterationDisplayTypeList = alterationDisplayTypeRepository.findAll();
        assertThat(alterationDisplayTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
