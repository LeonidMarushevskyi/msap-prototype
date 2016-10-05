package com.engagepoint.msap.web.rest;

import com.engagepoint.msap.Application;
import com.engagepoint.msap.domain.LookupLicenseType;
import com.engagepoint.msap.repository.LookupLicenseTypeRepository;
import com.engagepoint.msap.repository.search.LookupLicenseTypeSearchRepository;

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
 * Test class for the LookupLicenseTypeResource REST controller.
 *
 * @see LookupLicenseTypeResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class LookupLicenseTypeResourceIntTest {


    private static final Integer DEFAULT_CODE = 1;
    private static final Integer UPDATED_CODE = 2;
    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";

    @Inject
    private LookupLicenseTypeRepository lookupLicenseTypeRepository;

    @Inject
    private LookupLicenseTypeSearchRepository lookupLicenseTypeSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restLookupLicenseTypeMockMvc;

    private LookupLicenseType lookupLicenseType;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        LookupLicenseTypeResource lookupLicenseTypeResource = new LookupLicenseTypeResource();
        ReflectionTestUtils.setField(lookupLicenseTypeResource, "lookupLicenseTypeSearchRepository", lookupLicenseTypeSearchRepository);
        ReflectionTestUtils.setField(lookupLicenseTypeResource, "lookupLicenseTypeRepository", lookupLicenseTypeRepository);
        this.restLookupLicenseTypeMockMvc = MockMvcBuilders.standaloneSetup(lookupLicenseTypeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        lookupLicenseType = new LookupLicenseType();
        lookupLicenseType.setCode(DEFAULT_CODE);
        lookupLicenseType.setName(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createLookupLicenseType() throws Exception {
        int databaseSizeBeforeCreate = lookupLicenseTypeRepository.findAll().size();

        // Create the LookupLicenseType

        restLookupLicenseTypeMockMvc.perform(post("/api/lookupLicenseTypes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(lookupLicenseType)))
                .andExpect(status().isCreated());

        // Validate the LookupLicenseType in the database
        List<LookupLicenseType> lookupLicenseTypes = lookupLicenseTypeRepository.findAll();
        assertThat(lookupLicenseTypes).hasSize(databaseSizeBeforeCreate + 1);
        LookupLicenseType testLookupLicenseType = lookupLicenseTypes.get(lookupLicenseTypes.size() - 1);
        assertThat(testLookupLicenseType.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testLookupLicenseType.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = lookupLicenseTypeRepository.findAll().size();
        // set the field null
        lookupLicenseType.setCode(null);

        // Create the LookupLicenseType, which fails.

        restLookupLicenseTypeMockMvc.perform(post("/api/lookupLicenseTypes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(lookupLicenseType)))
                .andExpect(status().isBadRequest());

        List<LookupLicenseType> lookupLicenseTypes = lookupLicenseTypeRepository.findAll();
        assertThat(lookupLicenseTypes).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = lookupLicenseTypeRepository.findAll().size();
        // set the field null
        lookupLicenseType.setName(null);

        // Create the LookupLicenseType, which fails.

        restLookupLicenseTypeMockMvc.perform(post("/api/lookupLicenseTypes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(lookupLicenseType)))
                .andExpect(status().isBadRequest());

        List<LookupLicenseType> lookupLicenseTypes = lookupLicenseTypeRepository.findAll();
        assertThat(lookupLicenseTypes).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllLookupLicenseTypes() throws Exception {
        // Initialize the database
        lookupLicenseTypeRepository.saveAndFlush(lookupLicenseType);

        // Get all the lookupLicenseTypes
        restLookupLicenseTypeMockMvc.perform(get("/api/lookupLicenseTypes?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(lookupLicenseType.getId().intValue())))
                .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }

    @Test
    @Transactional
    public void getLookupLicenseType() throws Exception {
        // Initialize the database
        lookupLicenseTypeRepository.saveAndFlush(lookupLicenseType);

        // Get the lookupLicenseType
        restLookupLicenseTypeMockMvc.perform(get("/api/lookupLicenseTypes/{id}", lookupLicenseType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(lookupLicenseType.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    public void getNonExistingLookupLicenseType() throws Exception {
        // Get the lookupLicenseType
        restLookupLicenseTypeMockMvc.perform(get("/api/lookupLicenseTypes/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLookupLicenseType() throws Exception {
        // Initialize the database
        lookupLicenseTypeRepository.saveAndFlush(lookupLicenseType);

		int databaseSizeBeforeUpdate = lookupLicenseTypeRepository.findAll().size();

        // Update the lookupLicenseType
        lookupLicenseType.setCode(UPDATED_CODE);
        lookupLicenseType.setName(UPDATED_NAME);

        restLookupLicenseTypeMockMvc.perform(put("/api/lookupLicenseTypes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(lookupLicenseType)))
                .andExpect(status().isOk());

        // Validate the LookupLicenseType in the database
        List<LookupLicenseType> lookupLicenseTypes = lookupLicenseTypeRepository.findAll();
        assertThat(lookupLicenseTypes).hasSize(databaseSizeBeforeUpdate);
        LookupLicenseType testLookupLicenseType = lookupLicenseTypes.get(lookupLicenseTypes.size() - 1);
        assertThat(testLookupLicenseType.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testLookupLicenseType.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void deleteLookupLicenseType() throws Exception {
        // Initialize the database
        lookupLicenseTypeRepository.saveAndFlush(lookupLicenseType);

		int databaseSizeBeforeDelete = lookupLicenseTypeRepository.findAll().size();

        // Get the lookupLicenseType
        restLookupLicenseTypeMockMvc.perform(delete("/api/lookupLicenseTypes/{id}", lookupLicenseType.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<LookupLicenseType> lookupLicenseTypes = lookupLicenseTypeRepository.findAll();
        assertThat(lookupLicenseTypes).hasSize(databaseSizeBeforeDelete - 1);
    }
}
