package com.sit.web.rest;

import com.sit.SitApp;

import com.sit.domain.AlterationSubGroup;
import com.sit.repository.tenant.AlterationSubGroupRepository;
import com.sit.service.AlterationSubGroupService;
import com.sit.service.dto.AlterationSubGroupDTO;
import com.sit.service.mapper.AlterationSubGroupMapper;

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
 * Test class for the AlterationSubGroupResource REST controller.
 *
 * @see AlterationSubGroupResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SitApp.class)
public class AlterationSubGroupResourceIntTest {

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Gender DEFAULT_GENDER = Gender.MENS;
    private static final Gender UPDATED_GENDER = Gender.WOMENS;

    @Inject
    private AlterationSubGroupRepository alterationSubGroupRepository;

    @Inject
    private AlterationSubGroupMapper alterationSubGroupMapper;

    @Inject
    private AlterationSubGroupService alterationSubGroupService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restAlterationSubGroupMockMvc;

    private AlterationSubGroup alterationSubGroup;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        AlterationSubGroupResource alterationSubGroupResource = new AlterationSubGroupResource();
        ReflectionTestUtils.setField(alterationSubGroupResource, "alterationSubGroupService", alterationSubGroupService);
        this.restAlterationSubGroupMockMvc = MockMvcBuilders.standaloneSetup(alterationSubGroupResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AlterationSubGroup createEntity(EntityManager em) {
        AlterationSubGroup alterationSubGroup = new AlterationSubGroup()
                .description(DEFAULT_DESCRIPTION)
                .gender(DEFAULT_GENDER);
        return alterationSubGroup;
    }

    @Before
    public void initTest() {
        alterationSubGroup = createEntity(em);
    }

    @Test
    @Transactional
    public void createAlterationSubGroup() throws Exception {
        int databaseSizeBeforeCreate = alterationSubGroupRepository.findAll().size();

        // Create the AlterationSubGroup
        AlterationSubGroupDTO alterationSubGroupDTO = alterationSubGroupMapper.alterationSubGroupToAlterationSubGroupDTO(alterationSubGroup);

        restAlterationSubGroupMockMvc.perform(post("/api/alteration-sub-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(alterationSubGroupDTO)))
            .andExpect(status().isCreated());

        // Validate the AlterationSubGroup in the database
        List<AlterationSubGroup> alterationSubGroupList = alterationSubGroupRepository.findAll();
        assertThat(alterationSubGroupList).hasSize(databaseSizeBeforeCreate + 1);
        AlterationSubGroup testAlterationSubGroup = alterationSubGroupList.get(alterationSubGroupList.size() - 1);
        assertThat(testAlterationSubGroup.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testAlterationSubGroup.getGender()).isEqualTo(DEFAULT_GENDER);
    }

    @Test
    @Transactional
    public void createAlterationSubGroupWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = alterationSubGroupRepository.findAll().size();

        // Create the AlterationSubGroup with an existing ID
        AlterationSubGroup existingAlterationSubGroup = new AlterationSubGroup();
        existingAlterationSubGroup.setId(1L);
        AlterationSubGroupDTO existingAlterationSubGroupDTO = alterationSubGroupMapper.alterationSubGroupToAlterationSubGroupDTO(existingAlterationSubGroup);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAlterationSubGroupMockMvc.perform(post("/api/alteration-sub-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingAlterationSubGroupDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<AlterationSubGroup> alterationSubGroupList = alterationSubGroupRepository.findAll();
        assertThat(alterationSubGroupList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = alterationSubGroupRepository.findAll().size();
        // set the field null
        alterationSubGroup.setDescription(null);

        // Create the AlterationSubGroup, which fails.
        AlterationSubGroupDTO alterationSubGroupDTO = alterationSubGroupMapper.alterationSubGroupToAlterationSubGroupDTO(alterationSubGroup);

        restAlterationSubGroupMockMvc.perform(post("/api/alteration-sub-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(alterationSubGroupDTO)))
            .andExpect(status().isBadRequest());

        List<AlterationSubGroup> alterationSubGroupList = alterationSubGroupRepository.findAll();
        assertThat(alterationSubGroupList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAlterationSubGroups() throws Exception {
        // Initialize the database
        alterationSubGroupRepository.saveAndFlush(alterationSubGroup);

        // Get all the alterationSubGroupList
        restAlterationSubGroupMockMvc.perform(get("/api/alteration-sub-groups?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(alterationSubGroup.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER.toString())));
    }

    @Test
    @Transactional
    public void getAlterationSubGroup() throws Exception {
        // Initialize the database
        alterationSubGroupRepository.saveAndFlush(alterationSubGroup);

        // Get the alterationSubGroup
        restAlterationSubGroupMockMvc.perform(get("/api/alteration-sub-groups/{id}", alterationSubGroup.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(alterationSubGroup.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.gender").value(DEFAULT_GENDER.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAlterationSubGroup() throws Exception {
        // Get the alterationSubGroup
        restAlterationSubGroupMockMvc.perform(get("/api/alteration-sub-groups/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAlterationSubGroup() throws Exception {
        // Initialize the database
        alterationSubGroupRepository.saveAndFlush(alterationSubGroup);
        int databaseSizeBeforeUpdate = alterationSubGroupRepository.findAll().size();

        // Update the alterationSubGroup
        AlterationSubGroup updatedAlterationSubGroup = alterationSubGroupRepository.findOne(alterationSubGroup.getId());
        updatedAlterationSubGroup
                .description(UPDATED_DESCRIPTION)
                .gender(UPDATED_GENDER);
        AlterationSubGroupDTO alterationSubGroupDTO = alterationSubGroupMapper.alterationSubGroupToAlterationSubGroupDTO(updatedAlterationSubGroup);

        restAlterationSubGroupMockMvc.perform(put("/api/alteration-sub-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(alterationSubGroupDTO)))
            .andExpect(status().isOk());

        // Validate the AlterationSubGroup in the database
        List<AlterationSubGroup> alterationSubGroupList = alterationSubGroupRepository.findAll();
        assertThat(alterationSubGroupList).hasSize(databaseSizeBeforeUpdate);
        AlterationSubGroup testAlterationSubGroup = alterationSubGroupList.get(alterationSubGroupList.size() - 1);
        assertThat(testAlterationSubGroup.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testAlterationSubGroup.getGender()).isEqualTo(UPDATED_GENDER);
    }

    @Test
    @Transactional
    public void updateNonExistingAlterationSubGroup() throws Exception {
        int databaseSizeBeforeUpdate = alterationSubGroupRepository.findAll().size();

        // Create the AlterationSubGroup
        AlterationSubGroupDTO alterationSubGroupDTO = alterationSubGroupMapper.alterationSubGroupToAlterationSubGroupDTO(alterationSubGroup);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAlterationSubGroupMockMvc.perform(put("/api/alteration-sub-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(alterationSubGroupDTO)))
            .andExpect(status().isCreated());

        // Validate the AlterationSubGroup in the database
        List<AlterationSubGroup> alterationSubGroupList = alterationSubGroupRepository.findAll();
        assertThat(alterationSubGroupList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteAlterationSubGroup() throws Exception {
        // Initialize the database
        alterationSubGroupRepository.saveAndFlush(alterationSubGroup);
        int databaseSizeBeforeDelete = alterationSubGroupRepository.findAll().size();

        // Get the alterationSubGroup
        restAlterationSubGroupMockMvc.perform(delete("/api/alteration-sub-groups/{id}", alterationSubGroup.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<AlterationSubGroup> alterationSubGroupList = alterationSubGroupRepository.findAll();
        assertThat(alterationSubGroupList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
