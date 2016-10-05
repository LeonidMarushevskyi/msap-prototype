package com.engagepoint.msap.web.rest;

import com.engagepoint.msap.Application;
import com.engagepoint.msap.domain.LookupSpecialNeedGroup;
import com.engagepoint.msap.repository.LookupSpecialNeedGroupRepository;
import com.engagepoint.msap.repository.search.LookupSpecialNeedGroupSearchRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the LookupSpecialNeedGroupResource REST controller.
 *
 * @see LookupSpecialNeedGroupResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class LookupSpecialNeedGroupResourceIntTest {


    private static final Integer DEFAULT_CODE = 1;
    private static final Integer UPDATED_CODE = 2;
    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";

    @Inject
    private LookupSpecialNeedGroupRepository lookupSpecialNeedGroupRepository;

    @Inject
    private LookupSpecialNeedGroupSearchRepository lookupSpecialNeedGroupSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restLookupSpecialNeedGroupMockMvc;

    private LookupSpecialNeedGroup lookupSpecialNeedGroup;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        LookupSpecialNeedGroupResource lookupSpecialNeedGroupResource = new LookupSpecialNeedGroupResource();
        ReflectionTestUtils.setField(lookupSpecialNeedGroupResource, "lookupSpecialNeedGroupSearchRepository", lookupSpecialNeedGroupSearchRepository);
        ReflectionTestUtils.setField(lookupSpecialNeedGroupResource, "lookupSpecialNeedGroupRepository", lookupSpecialNeedGroupRepository);
        this.restLookupSpecialNeedGroupMockMvc = MockMvcBuilders.standaloneSetup(lookupSpecialNeedGroupResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        lookupSpecialNeedGroup = new LookupSpecialNeedGroup();
        lookupSpecialNeedGroup.setCode(DEFAULT_CODE);
        lookupSpecialNeedGroup.setName(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createLookupSpecialNeedGroup() throws Exception {
        int databaseSizeBeforeCreate = lookupSpecialNeedGroupRepository.findAll().size();

        // Create the LookupSpecialNeedGroup

        restLookupSpecialNeedGroupMockMvc.perform(post("/api/lookupSpecialNeedGroups")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(lookupSpecialNeedGroup)))
                .andExpect(status().isCreated());

        // Validate the LookupSpecialNeedGroup in the database
        List<LookupSpecialNeedGroup> lookupSpecialNeedGroups = lookupSpecialNeedGroupRepository.findAll();
        assertThat(lookupSpecialNeedGroups).hasSize(databaseSizeBeforeCreate + 1);
        LookupSpecialNeedGroup testLookupSpecialNeedGroup = lookupSpecialNeedGroups.get(lookupSpecialNeedGroups.size() - 1);
        assertThat(testLookupSpecialNeedGroup.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testLookupSpecialNeedGroup.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = lookupSpecialNeedGroupRepository.findAll().size();
        // set the field null
        lookupSpecialNeedGroup.setCode(null);

        // Create the LookupSpecialNeedGroup, which fails.

        restLookupSpecialNeedGroupMockMvc.perform(post("/api/lookupSpecialNeedGroups")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(lookupSpecialNeedGroup)))
                .andExpect(status().isBadRequest());

        List<LookupSpecialNeedGroup> lookupSpecialNeedGroups = lookupSpecialNeedGroupRepository.findAll();
        assertThat(lookupSpecialNeedGroups).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = lookupSpecialNeedGroupRepository.findAll().size();
        // set the field null
        lookupSpecialNeedGroup.setName(null);

        // Create the LookupSpecialNeedGroup, which fails.

        restLookupSpecialNeedGroupMockMvc.perform(post("/api/lookupSpecialNeedGroups")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(lookupSpecialNeedGroup)))
                .andExpect(status().isBadRequest());

        List<LookupSpecialNeedGroup> lookupSpecialNeedGroups = lookupSpecialNeedGroupRepository.findAll();
        assertThat(lookupSpecialNeedGroups).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllLookupSpecialNeedGroups() throws Exception {
        // Initialize the database
        lookupSpecialNeedGroupRepository.saveAndFlush(lookupSpecialNeedGroup);

        // Get all the lookupSpecialNeedGroups
        restLookupSpecialNeedGroupMockMvc.perform(get("/api/lookupSpecialNeedGroups?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(lookupSpecialNeedGroup.getId().intValue())))
                .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }

    @Test
    @Transactional
    public void getLookupSpecialNeedGroup() throws Exception {
        // Initialize the database
        lookupSpecialNeedGroupRepository.saveAndFlush(lookupSpecialNeedGroup);

        // Get the lookupSpecialNeedGroup
        restLookupSpecialNeedGroupMockMvc.perform(get("/api/lookupSpecialNeedGroups/{id}", lookupSpecialNeedGroup.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(lookupSpecialNeedGroup.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    public void getNonExistingLookupSpecialNeedGroup() throws Exception {
        // Get the lookupSpecialNeedGroup
        restLookupSpecialNeedGroupMockMvc.perform(get("/api/lookupSpecialNeedGroups/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLookupSpecialNeedGroup() throws Exception {
        // Initialize the database
        lookupSpecialNeedGroupRepository.saveAndFlush(lookupSpecialNeedGroup);

		int databaseSizeBeforeUpdate = lookupSpecialNeedGroupRepository.findAll().size();

        // Update the lookupSpecialNeedGroup
        lookupSpecialNeedGroup.setCode(UPDATED_CODE);
        lookupSpecialNeedGroup.setName(UPDATED_NAME);

        restLookupSpecialNeedGroupMockMvc.perform(put("/api/lookupSpecialNeedGroups")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(lookupSpecialNeedGroup)))
                .andExpect(status().isOk());

        // Validate the LookupSpecialNeedGroup in the database
        List<LookupSpecialNeedGroup> lookupSpecialNeedGroups = lookupSpecialNeedGroupRepository.findAll();
        assertThat(lookupSpecialNeedGroups).hasSize(databaseSizeBeforeUpdate);
        LookupSpecialNeedGroup testLookupSpecialNeedGroup = lookupSpecialNeedGroups.get(lookupSpecialNeedGroups.size() - 1);
        assertThat(testLookupSpecialNeedGroup.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testLookupSpecialNeedGroup.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void deleteLookupSpecialNeedGroup() throws Exception {
        // Initialize the database
        lookupSpecialNeedGroupRepository.saveAndFlush(lookupSpecialNeedGroup);

		int databaseSizeBeforeDelete = lookupSpecialNeedGroupRepository.findAll().size();

        // Get the lookupSpecialNeedGroup
        restLookupSpecialNeedGroupMockMvc.perform(delete("/api/lookupSpecialNeedGroups/{id}", lookupSpecialNeedGroup.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<LookupSpecialNeedGroup> lookupSpecialNeedGroups = lookupSpecialNeedGroupRepository.findAll();
        assertThat(lookupSpecialNeedGroups).hasSize(databaseSizeBeforeDelete - 1);
    }
}
