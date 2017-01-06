package com.sit.web.rest;

import com.sit.SitApp;

import com.sit.domain.Color;
import com.sit.repository.tenant.ColorRepository;
import com.sit.service.ColorService;
import com.sit.service.dto.ColorDTO;
import com.sit.service.mapper.ColorMapper;

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
 * Test class for the ColorResource REST controller.
 *
 * @see ColorResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SitApp.class)
public class ColorResourceIntTest {

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Integer DEFAULT_ORDER_NUMBER = 1;
    private static final Integer UPDATED_ORDER_NUMBER = 2;

    @Inject
    private ColorRepository colorRepository;

    @Inject
    private ColorMapper colorMapper;

    @Inject
    private ColorService colorService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restColorMockMvc;

    private Color color;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ColorResource colorResource = new ColorResource();
        ReflectionTestUtils.setField(colorResource, "colorService", colorService);
        this.restColorMockMvc = MockMvcBuilders.standaloneSetup(colorResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Color createEntity(EntityManager em) {
        Color color = new Color()
                .description(DEFAULT_DESCRIPTION)
                .orderNumber(DEFAULT_ORDER_NUMBER);
        return color;
    }

    @Before
    public void initTest() {
        color = createEntity(em);
    }

    @Test
    @Transactional
    public void createColor() throws Exception {
        int databaseSizeBeforeCreate = colorRepository.findAll().size();

        // Create the Color
        ColorDTO colorDTO = colorMapper.colorToColorDTO(color);

        restColorMockMvc.perform(post("/api/colors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(colorDTO)))
            .andExpect(status().isCreated());

        // Validate the Color in the database
        List<Color> colorList = colorRepository.findAll();
        assertThat(colorList).hasSize(databaseSizeBeforeCreate + 1);
        Color testColor = colorList.get(colorList.size() - 1);
        assertThat(testColor.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testColor.getOrderNumber()).isEqualTo(DEFAULT_ORDER_NUMBER);
    }

    @Test
    @Transactional
    public void createColorWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = colorRepository.findAll().size();

        // Create the Color with an existing ID
        Color existingColor = new Color();
        existingColor.setId(1L);
        ColorDTO existingColorDTO = colorMapper.colorToColorDTO(existingColor);

        // An entity with an existing ID cannot be created, so this API call must fail
        restColorMockMvc.perform(post("/api/colors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingColorDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Color> colorList = colorRepository.findAll();
        assertThat(colorList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = colorRepository.findAll().size();
        // set the field null
        color.setDescription(null);

        // Create the Color, which fails.
        ColorDTO colorDTO = colorMapper.colorToColorDTO(color);

        restColorMockMvc.perform(post("/api/colors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(colorDTO)))
            .andExpect(status().isBadRequest());

        List<Color> colorList = colorRepository.findAll();
        assertThat(colorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllColors() throws Exception {
        // Initialize the database
        colorRepository.saveAndFlush(color);

        // Get all the colorList
        restColorMockMvc.perform(get("/api/colors?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(color.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].orderNumber").value(hasItem(DEFAULT_ORDER_NUMBER)));
    }

    @Test
    @Transactional
    public void getColor() throws Exception {
        // Initialize the database
        colorRepository.saveAndFlush(color);

        // Get the color
        restColorMockMvc.perform(get("/api/colors/{id}", color.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(color.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.orderNumber").value(DEFAULT_ORDER_NUMBER));
    }

    @Test
    @Transactional
    public void getNonExistingColor() throws Exception {
        // Get the color
        restColorMockMvc.perform(get("/api/colors/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateColor() throws Exception {
        // Initialize the database
        colorRepository.saveAndFlush(color);
        int databaseSizeBeforeUpdate = colorRepository.findAll().size();

        // Update the color
        Color updatedColor = colorRepository.findOne(color.getId());
        updatedColor
                .description(UPDATED_DESCRIPTION)
                .orderNumber(UPDATED_ORDER_NUMBER);
        ColorDTO colorDTO = colorMapper.colorToColorDTO(updatedColor);

        restColorMockMvc.perform(put("/api/colors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(colorDTO)))
            .andExpect(status().isOk());

        // Validate the Color in the database
        List<Color> colorList = colorRepository.findAll();
        assertThat(colorList).hasSize(databaseSizeBeforeUpdate);
        Color testColor = colorList.get(colorList.size() - 1);
        assertThat(testColor.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testColor.getOrderNumber()).isEqualTo(UPDATED_ORDER_NUMBER);
    }

    @Test
    @Transactional
    public void updateNonExistingColor() throws Exception {
        int databaseSizeBeforeUpdate = colorRepository.findAll().size();

        // Create the Color
        ColorDTO colorDTO = colorMapper.colorToColorDTO(color);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restColorMockMvc.perform(put("/api/colors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(colorDTO)))
            .andExpect(status().isCreated());

        // Validate the Color in the database
        List<Color> colorList = colorRepository.findAll();
        assertThat(colorList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteColor() throws Exception {
        // Initialize the database
        colorRepository.saveAndFlush(color);
        int databaseSizeBeforeDelete = colorRepository.findAll().size();

        // Get the color
        restColorMockMvc.perform(delete("/api/colors/{id}", color.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Color> colorList = colorRepository.findAll();
        assertThat(colorList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
