package com.engagepoint.msap.web.rest;

import com.engagepoint.msap.Application;
import com.engagepoint.msap.domain.LookupAgeGroups;
import com.engagepoint.msap.repository.LookupAgeGroupsRepository;
import com.engagepoint.msap.repository.search.LookupAgeGroupsSearchRepository;

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
 * Test class for the LookupAgeGroupsResource REST controller.
 *
 * @see LookupAgeGroupsResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class LookupAgeGroupsResourceIntTest {


    private static final Integer DEFAULT_CODE = 1;
    private static final Integer UPDATED_CODE = 2;
    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";

    @Inject
    private LookupAgeGroupsRepository lookupAgeGroupsRepository;

    @Inject
    private LookupAgeGroupsSearchRepository lookupAgeGroupsSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restLookupAgeGroupsMockMvc;

    private LookupAgeGroups lookupAgeGroups;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        LookupAgeGroupsResource lookupAgeGroupsResource = new LookupAgeGroupsResource();
        ReflectionTestUtils.setField(lookupAgeGroupsResource, "lookupAgeGroupsSearchRepository", lookupAgeGroupsSearchRepository);
        ReflectionTestUtils.setField(lookupAgeGroupsResource, "lookupAgeGroupsRepository", lookupAgeGroupsRepository);
        this.restLookupAgeGroupsMockMvc = MockMvcBuilders.standaloneSetup(lookupAgeGroupsResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        lookupAgeGroups = new LookupAgeGroups();
        lookupAgeGroups.setCode(DEFAULT_CODE);
        lookupAgeGroups.setName(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createLookupAgeGroups() throws Exception {
        int databaseSizeBeforeCreate = lookupAgeGroupsRepository.findAll().size();

        // Create the LookupAgeGroups

        restLookupAgeGroupsMockMvc.perform(post("/api/lookupAgeGroupss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(lookupAgeGroups)))
                .andExpect(status().isCreated());

        // Validate the LookupAgeGroups in the database
        List<LookupAgeGroups> lookupAgeGroupss = lookupAgeGroupsRepository.findAll();
        assertThat(lookupAgeGroupss).hasSize(databaseSizeBeforeCreate + 1);
        LookupAgeGroups testLookupAgeGroups = lookupAgeGroupss.get(lookupAgeGroupss.size() - 1);
        assertThat(testLookupAgeGroups.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testLookupAgeGroups.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = lookupAgeGroupsRepository.findAll().size();
        // set the field null
        lookupAgeGroups.setCode(null);

        // Create the LookupAgeGroups, which fails.

        restLookupAgeGroupsMockMvc.perform(post("/api/lookupAgeGroupss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(lookupAgeGroups)))
                .andExpect(status().isBadRequest());

        List<LookupAgeGroups> lookupAgeGroupss = lookupAgeGroupsRepository.findAll();
        assertThat(lookupAgeGroupss).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = lookupAgeGroupsRepository.findAll().size();
        // set the field null
        lookupAgeGroups.setName(null);

        // Create the LookupAgeGroups, which fails.

        restLookupAgeGroupsMockMvc.perform(post("/api/lookupAgeGroupss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(lookupAgeGroups)))
                .andExpect(status().isBadRequest());

        List<LookupAgeGroups> lookupAgeGroupss = lookupAgeGroupsRepository.findAll();
        assertThat(lookupAgeGroupss).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllLookupAgeGroupss() throws Exception {
        // Initialize the database
        lookupAgeGroupsRepository.saveAndFlush(lookupAgeGroups);

        // Get all the lookupAgeGroupss
        restLookupAgeGroupsMockMvc.perform(get("/api/lookupAgeGroupss?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(lookupAgeGroups.getId().intValue())))
                .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }

    @Test
    @Transactional
    public void getLookupAgeGroups() throws Exception {
        // Initialize the database
        lookupAgeGroupsRepository.saveAndFlush(lookupAgeGroups);

        // Get the lookupAgeGroups
        restLookupAgeGroupsMockMvc.perform(get("/api/lookupAgeGroupss/{id}", lookupAgeGroups.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(lookupAgeGroups.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    public void getNonExistingLookupAgeGroups() throws Exception {
        // Get the lookupAgeGroups
        restLookupAgeGroupsMockMvc.perform(get("/api/lookupAgeGroupss/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLookupAgeGroups() throws Exception {
        // Initialize the database
        lookupAgeGroupsRepository.saveAndFlush(lookupAgeGroups);

		int databaseSizeBeforeUpdate = lookupAgeGroupsRepository.findAll().size();

        // Update the lookupAgeGroups
        lookupAgeGroups.setCode(UPDATED_CODE);
        lookupAgeGroups.setName(UPDATED_NAME);

        restLookupAgeGroupsMockMvc.perform(put("/api/lookupAgeGroupss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(lookupAgeGroups)))
                .andExpect(status().isOk());

        // Validate the LookupAgeGroups in the database
        List<LookupAgeGroups> lookupAgeGroupss = lookupAgeGroupsRepository.findAll();
        assertThat(lookupAgeGroupss).hasSize(databaseSizeBeforeUpdate);
        LookupAgeGroups testLookupAgeGroups = lookupAgeGroupss.get(lookupAgeGroupss.size() - 1);
        assertThat(testLookupAgeGroups.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testLookupAgeGroups.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void deleteLookupAgeGroups() throws Exception {
        // Initialize the database
        lookupAgeGroupsRepository.saveAndFlush(lookupAgeGroups);

		int databaseSizeBeforeDelete = lookupAgeGroupsRepository.findAll().size();

        // Get the lookupAgeGroups
        restLookupAgeGroupsMockMvc.perform(delete("/api/lookupAgeGroupss/{id}", lookupAgeGroups.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<LookupAgeGroups> lookupAgeGroupss = lookupAgeGroupsRepository.findAll();
        assertThat(lookupAgeGroupss).hasSize(databaseSizeBeforeDelete - 1);
    }
}
