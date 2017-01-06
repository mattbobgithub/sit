package com.sit.web.rest;

import com.sit.SitApp;

import com.sit.domain.SitUser;
import com.sit.repository.master.SitUserRepository;
import com.sit.service.SitUserService;
import com.sit.service.dto.SitUserDTO;
import com.sit.service.mapper.SitUserMapper;

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

import com.sit.domain.enumeration.UserType;
/**
 * Test class for the SitUserResource REST controller.
 *
 * @see SitUserResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SitApp.class)
public class SitUserResourceIntTest {

    private static final String DEFAULT_USERNAME = "AAAAAAAAAA";
    private static final String UPDATED_USERNAME = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final UserType DEFAULT_USER_TYPE = UserType.ADMIN;
    private static final UserType UPDATED_USER_TYPE = UserType.MANAGER;

    private static final Boolean DEFAULT_FITTER_INDICATOR = false;
    private static final Boolean UPDATED_FITTER_INDICATOR = true;

    private static final Integer DEFAULT_MANAGER_APPROVAL_CODE = 1;
    private static final Integer UPDATED_MANAGER_APPROVAL_CODE = 2;

    @Inject
    private SitUserRepository sitUserRepository;

    @Inject
    private SitUserMapper sitUserMapper;

    @Inject
    private SitUserService sitUserService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restSitUserMockMvc;

    private SitUser sitUser;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SitUserResource sitUserResource = new SitUserResource();
        ReflectionTestUtils.setField(sitUserResource, "sitUserService", sitUserService);
        this.restSitUserMockMvc = MockMvcBuilders.standaloneSetup(sitUserResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SitUser createEntity(EntityManager em) {
        SitUser sitUser = new SitUser()
                .username(DEFAULT_USERNAME)
                .email(DEFAULT_EMAIL)
                .userType(DEFAULT_USER_TYPE)
                .fitterIndicator(DEFAULT_FITTER_INDICATOR)
                .managerApprovalCode(DEFAULT_MANAGER_APPROVAL_CODE);
        return sitUser;
    }

    @Before
    public void initTest() {
        sitUser = createEntity(em);
    }

    @Test
    @Transactional
    public void createSitUser() throws Exception {
        int databaseSizeBeforeCreate = sitUserRepository.findAll().size();

        // Create the SitUser
        SitUserDTO sitUserDTO = sitUserMapper.sitUserToSitUserDTO(sitUser);

        restSitUserMockMvc.perform(post("/api/sit-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sitUserDTO)))
            .andExpect(status().isCreated());

        // Validate the SitUser in the database
        List<SitUser> sitUserList = sitUserRepository.findAll();
        assertThat(sitUserList).hasSize(databaseSizeBeforeCreate + 1);
        SitUser testSitUser = sitUserList.get(sitUserList.size() - 1);
        assertThat(testSitUser.getUsername()).isEqualTo(DEFAULT_USERNAME);
        assertThat(testSitUser.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testSitUser.getUserType()).isEqualTo(DEFAULT_USER_TYPE);
        assertThat(testSitUser.isFitterIndicator()).isEqualTo(DEFAULT_FITTER_INDICATOR);
        assertThat(testSitUser.getManagerApprovalCode()).isEqualTo(DEFAULT_MANAGER_APPROVAL_CODE);
    }

    @Test
    @Transactional
    public void createSitUserWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = sitUserRepository.findAll().size();

        // Create the SitUser with an existing ID
        SitUser existingSitUser = new SitUser();
        existingSitUser.setId(1L);
        SitUserDTO existingSitUserDTO = sitUserMapper.sitUserToSitUserDTO(existingSitUser);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSitUserMockMvc.perform(post("/api/sit-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingSitUserDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<SitUser> sitUserList = sitUserRepository.findAll();
        assertThat(sitUserList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllSitUsers() throws Exception {
        // Initialize the database
        sitUserRepository.saveAndFlush(sitUser);

        // Get all the sitUserList
        restSitUserMockMvc.perform(get("/api/sit-users?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sitUser.getId().intValue())))
            .andExpect(jsonPath("$.[*].username").value(hasItem(DEFAULT_USERNAME.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].userType").value(hasItem(DEFAULT_USER_TYPE.toString())))
            .andExpect(jsonPath("$.[*].fitterIndicator").value(hasItem(DEFAULT_FITTER_INDICATOR.booleanValue())))
            .andExpect(jsonPath("$.[*].managerApprovalCode").value(hasItem(DEFAULT_MANAGER_APPROVAL_CODE)));
    }

    @Test
    @Transactional
    public void getSitUser() throws Exception {
        // Initialize the database
        sitUserRepository.saveAndFlush(sitUser);

        // Get the sitUser
        restSitUserMockMvc.perform(get("/api/sit-users/{id}", sitUser.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(sitUser.getId().intValue()))
            .andExpect(jsonPath("$.username").value(DEFAULT_USERNAME.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.userType").value(DEFAULT_USER_TYPE.toString()))
            .andExpect(jsonPath("$.fitterIndicator").value(DEFAULT_FITTER_INDICATOR.booleanValue()))
            .andExpect(jsonPath("$.managerApprovalCode").value(DEFAULT_MANAGER_APPROVAL_CODE));
    }

    @Test
    @Transactional
    public void getNonExistingSitUser() throws Exception {
        // Get the sitUser
        restSitUserMockMvc.perform(get("/api/sit-users/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSitUser() throws Exception {
        // Initialize the database
        sitUserRepository.saveAndFlush(sitUser);
        int databaseSizeBeforeUpdate = sitUserRepository.findAll().size();

        // Update the sitUser
        SitUser updatedSitUser = sitUserRepository.findOne(sitUser.getId());
        updatedSitUser
                .username(UPDATED_USERNAME)
                .email(UPDATED_EMAIL)
                .userType(UPDATED_USER_TYPE)
                .fitterIndicator(UPDATED_FITTER_INDICATOR)
                .managerApprovalCode(UPDATED_MANAGER_APPROVAL_CODE);
        SitUserDTO sitUserDTO = sitUserMapper.sitUserToSitUserDTO(updatedSitUser);

        restSitUserMockMvc.perform(put("/api/sit-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sitUserDTO)))
            .andExpect(status().isOk());

        // Validate the SitUser in the database
        List<SitUser> sitUserList = sitUserRepository.findAll();
        assertThat(sitUserList).hasSize(databaseSizeBeforeUpdate);
        SitUser testSitUser = sitUserList.get(sitUserList.size() - 1);
        assertThat(testSitUser.getUsername()).isEqualTo(UPDATED_USERNAME);
        assertThat(testSitUser.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testSitUser.getUserType()).isEqualTo(UPDATED_USER_TYPE);
        assertThat(testSitUser.isFitterIndicator()).isEqualTo(UPDATED_FITTER_INDICATOR);
        assertThat(testSitUser.getManagerApprovalCode()).isEqualTo(UPDATED_MANAGER_APPROVAL_CODE);
    }

    @Test
    @Transactional
    public void updateNonExistingSitUser() throws Exception {
        int databaseSizeBeforeUpdate = sitUserRepository.findAll().size();

        // Create the SitUser
        SitUserDTO sitUserDTO = sitUserMapper.sitUserToSitUserDTO(sitUser);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSitUserMockMvc.perform(put("/api/sit-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sitUserDTO)))
            .andExpect(status().isCreated());

        // Validate the SitUser in the database
        List<SitUser> sitUserList = sitUserRepository.findAll();
        assertThat(sitUserList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteSitUser() throws Exception {
        // Initialize the database
        sitUserRepository.saveAndFlush(sitUser);
        int databaseSizeBeforeDelete = sitUserRepository.findAll().size();

        // Get the sitUser
        restSitUserMockMvc.perform(delete("/api/sit-users/{id}", sitUser.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<SitUser> sitUserList = sitUserRepository.findAll();
        assertThat(sitUserList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
