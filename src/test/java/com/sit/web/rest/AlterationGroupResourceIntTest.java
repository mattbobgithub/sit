package com.sit.web.rest;

import com.sit.SitApp;

import com.sit.domain.AlterationGroup;
import com.sit.repository.tenant.AlterationGroupRepository;
import com.sit.service.AlterationGroupService;
import com.sit.service.dto.AlterationGroupDTO;
import com.sit.service.mapper.AlterationGroupMapper;

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
 * Test class for the AlterationGroupResource REST controller.
 *
 * @see AlterationGroupResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SitApp.class)
public class AlterationGroupResourceIntTest {

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Gender DEFAULT_GENDER = Gender.MENS;
    private static final Gender UPDATED_GENDER = Gender.WOMENS;

    @Inject
    private AlterationGroupRepository alterationGroupRepository;

    @Inject
    private AlterationGroupMapper alterationGroupMapper;

    @Inject
    private AlterationGroupService alterationGroupService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restAlterationGroupMockMvc;

    private AlterationGroup alterationGroup;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        AlterationGroupResource alterationGroupResource = new AlterationGroupResource();
        ReflectionTestUtils.setField(alterationGroupResource, "alterationGroupService", alterationGroupService);
        this.restAlterationGroupMockMvc = MockMvcBuilders.standaloneSetup(alterationGroupResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AlterationGroup createEntity(EntityManager em) {
        AlterationGroup alterationGroup = new AlterationGroup()
                .description(DEFAULT_DESCRIPTION)
                .gender(DEFAULT_GENDER);
        return alterationGroup;
    }

    @Before
    public void initTest() {
        alterationGroup = createEntity(em);
    }

    @Test
    @Transactional
    public void createAlterationGroup() throws Exception {
        int databaseSizeBeforeCreate = alterationGroupRepository.findAll().size();

        // Create the AlterationGroup
        AlterationGroupDTO alterationGroupDTO = alterationGroupMapper.alterationGroupToAlterationGroupDTO(alterationGroup);

        restAlterationGroupMockMvc.perform(post("/api/alteration-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(alterationGroupDTO)))
            .andExpect(status().isCreated());

        // Validate the AlterationGroup in the database
        List<AlterationGroup> alterationGroupList = alterationGroupRepository.findAll();
        assertThat(alterationGroupList).hasSize(databaseSizeBeforeCreate + 1);
        AlterationGroup testAlterationGroup = alterationGroupList.get(alterationGroupList.size() - 1);
        assertThat(testAlterationGroup.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testAlterationGroup.getGender()).isEqualTo(DEFAULT_GENDER);
    }

    @Test
    @Transactional
    public void createAlterationGroupWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = alterationGroupRepository.findAll().size();

        // Create the AlterationGroup with an existing ID
        AlterationGroup existingAlterationGroup = new AlterationGroup();
        existingAlterationGroup.setId(1L);
        AlterationGroupDTO existingAlterationGroupDTO = alterationGroupMapper.alterationGroupToAlterationGroupDTO(existingAlterationGroup);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAlterationGroupMockMvc.perform(post("/api/alteration-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingAlterationGroupDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<AlterationGroup> alterationGroupList = alterationGroupRepository.findAll();
        assertThat(alterationGroupList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = alterationGroupRepository.findAll().size();
        // set the field null
        alterationGroup.setDescription(null);

        // Create the AlterationGroup, which fails.
        AlterationGroupDTO alterationGroupDTO = alterationGroupMapper.alterationGroupToAlterationGroupDTO(alterationGroup);

        restAlterationGroupMockMvc.perform(post("/api/alteration-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(alterationGroupDTO)))
            .andExpect(status().isBadRequest());

        List<AlterationGroup> alterationGroupList = alterationGroupRepository.findAll();
        assertThat(alterationGroupList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAlterationGroups() throws Exception {
        // Initialize the database
        alterationGroupRepository.saveAndFlush(alterationGroup);

        // Get all the alterationGroupList
        restAlterationGroupMockMvc.perform(get("/api/alteration-groups?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(alterationGroup.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER.toString())));
    }

    @Test
    @Transactional
    public void getAlterationGroup() throws Exception {
        // Initialize the database
        alterationGroupRepository.saveAndFlush(alterationGroup);

        // Get the alterationGroup
        restAlterationGroupMockMvc.perform(get("/api/alteration-groups/{id}", alterationGroup.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(alterationGroup.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.gender").value(DEFAULT_GENDER.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAlterationGroup() throws Exception {
        // Get the alterationGroup
        restAlterationGroupMockMvc.perform(get("/api/alteration-groups/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAlterationGroup() throws Exception {
        // Initialize the database
        alterationGroupRepository.saveAndFlush(alterationGroup);
        int databaseSizeBeforeUpdate = alterationGroupRepository.findAll().size();

        // Update the alterationGroup
        AlterationGroup updatedAlterationGroup = alterationGroupRepository.findOne(alterationGroup.getId());
        updatedAlterationGroup
                .description(UPDATED_DESCRIPTION)
                .gender(UPDATED_GENDER);
        AlterationGroupDTO alterationGroupDTO = alterationGroupMapper.alterationGroupToAlterationGroupDTO(updatedAlterationGroup);

        restAlterationGroupMockMvc.perform(put("/api/alteration-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(alterationGroupDTO)))
            .andExpect(status().isOk());

        // Validate the AlterationGroup in the database
        List<AlterationGroup> alterationGroupList = alterationGroupRepository.findAll();
        assertThat(alterationGroupList).hasSize(databaseSizeBeforeUpdate);
        AlterationGroup testAlterationGroup = alterationGroupList.get(alterationGroupList.size() - 1);
        assertThat(testAlterationGroup.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testAlterationGroup.getGender()).isEqualTo(UPDATED_GENDER);
    }

    @Test
    @Transactional
    public void updateNonExistingAlterationGroup() throws Exception {
        int databaseSizeBeforeUpdate = alterationGroupRepository.findAll().size();

        // Create the AlterationGroup
        AlterationGroupDTO alterationGroupDTO = alterationGroupMapper.alterationGroupToAlterationGroupDTO(alterationGroup);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAlterationGroupMockMvc.perform(put("/api/alteration-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(alterationGroupDTO)))
            .andExpect(status().isCreated());

        // Validate the AlterationGroup in the database
        List<AlterationGroup> alterationGroupList = alterationGroupRepository.findAll();
        assertThat(alterationGroupList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteAlterationGroup() throws Exception {
        // Initialize the database
        alterationGroupRepository.saveAndFlush(alterationGroup);
        int databaseSizeBeforeDelete = alterationGroupRepository.findAll().size();

        // Get the alterationGroup
        restAlterationGroupMockMvc.perform(delete("/api/alteration-groups/{id}", alterationGroup.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<AlterationGroup> alterationGroupList = alterationGroupRepository.findAll();
        assertThat(alterationGroupList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
