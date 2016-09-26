package com.engagepoint.msap.web.rest;

import com.engagepoint.msap.Application;
import com.engagepoint.msap.domain.LookupProviderType;
import com.engagepoint.msap.repository.LookupProviderTypeRepository;
import com.engagepoint.msap.repository.search.LookupProviderTypeSearchRepository;

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
 * Test class for the LookupProviderTypeResource REST controller.
 *
 * @see LookupProviderTypeResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class LookupProviderTypeResourceIntTest {


    private static final Integer DEFAULT_CODE = 1;
    private static final Integer UPDATED_CODE = 2;
    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";

    @Inject
    private LookupProviderTypeRepository lookupProviderTypeRepository;

    @Inject
    private LookupProviderTypeSearchRepository lookupProviderTypeSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restLookupProviderTypeMockMvc;

    private LookupProviderType lookupProviderType;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        LookupProviderTypeResource lookupProviderTypeResource = new LookupProviderTypeResource();
        ReflectionTestUtils.setField(lookupProviderTypeResource, "lookupProviderTypeSearchRepository", lookupProviderTypeSearchRepository);
        ReflectionTestUtils.setField(lookupProviderTypeResource, "lookupProviderTypeRepository", lookupProviderTypeRepository);
        this.restLookupProviderTypeMockMvc = MockMvcBuilders.standaloneSetup(lookupProviderTypeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        lookupProviderType = new LookupProviderType();
        lookupProviderType.setCode(DEFAULT_CODE);
        lookupProviderType.setName(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createLookupProviderType() throws Exception {
        int databaseSizeBeforeCreate = lookupProviderTypeRepository.findAll().size();

        // Create the LookupProviderType

        restLookupProviderTypeMockMvc.perform(post("/api/lookupProviderTypes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(lookupProviderType)))
                .andExpect(status().isCreated());

        // Validate the LookupProviderType in the database
        List<LookupProviderType> lookupProviderTypes = lookupProviderTypeRepository.findAll();
        assertThat(lookupProviderTypes).hasSize(databaseSizeBeforeCreate + 1);
        LookupProviderType testLookupProviderType = lookupProviderTypes.get(lookupProviderTypes.size() - 1);
        assertThat(testLookupProviderType.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testLookupProviderType.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = lookupProviderTypeRepository.findAll().size();
        // set the field null
        lookupProviderType.setCode(null);

        // Create the LookupProviderType, which fails.

        restLookupProviderTypeMockMvc.perform(post("/api/lookupProviderTypes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(lookupProviderType)))
                .andExpect(status().isBadRequest());

        List<LookupProviderType> lookupProviderTypes = lookupProviderTypeRepository.findAll();
        assertThat(lookupProviderTypes).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = lookupProviderTypeRepository.findAll().size();
        // set the field null
        lookupProviderType.setName(null);

        // Create the LookupProviderType, which fails.

        restLookupProviderTypeMockMvc.perform(post("/api/lookupProviderTypes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(lookupProviderType)))
                .andExpect(status().isBadRequest());

        List<LookupProviderType> lookupProviderTypes = lookupProviderTypeRepository.findAll();
        assertThat(lookupProviderTypes).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllLookupProviderTypes() throws Exception {
        // Initialize the database
        lookupProviderTypeRepository.saveAndFlush(lookupProviderType);

        // Get all the lookupProviderTypes
        restLookupProviderTypeMockMvc.perform(get("/api/lookupProviderTypes?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(lookupProviderType.getId().intValue())))
                .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getLookupProviderType() throws Exception {
        // Initialize the database
        lookupProviderTypeRepository.saveAndFlush(lookupProviderType);

        // Get the lookupProviderType
        restLookupProviderTypeMockMvc.perform(get("/api/lookupProviderTypes/{id}", lookupProviderType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(lookupProviderType.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingLookupProviderType() throws Exception {
        // Get the lookupProviderType
        restLookupProviderTypeMockMvc.perform(get("/api/lookupProviderTypes/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLookupProviderType() throws Exception {
        // Initialize the database
        lookupProviderTypeRepository.saveAndFlush(lookupProviderType);

		int databaseSizeBeforeUpdate = lookupProviderTypeRepository.findAll().size();

        // Update the lookupProviderType
        lookupProviderType.setCode(UPDATED_CODE);
        lookupProviderType.setName(UPDATED_NAME);

        restLookupProviderTypeMockMvc.perform(put("/api/lookupProviderTypes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(lookupProviderType)))
                .andExpect(status().isOk());

        // Validate the LookupProviderType in the database
        List<LookupProviderType> lookupProviderTypes = lookupProviderTypeRepository.findAll();
        assertThat(lookupProviderTypes).hasSize(databaseSizeBeforeUpdate);
        LookupProviderType testLookupProviderType = lookupProviderTypes.get(lookupProviderTypes.size() - 1);
        assertThat(testLookupProviderType.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testLookupProviderType.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void deleteLookupProviderType() throws Exception {
        // Initialize the database
        lookupProviderTypeRepository.saveAndFlush(lookupProviderType);

		int databaseSizeBeforeDelete = lookupProviderTypeRepository.findAll().size();

        // Get the lookupProviderType
        restLookupProviderTypeMockMvc.perform(delete("/api/lookupProviderTypes/{id}", lookupProviderType.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<LookupProviderType> lookupProviderTypes = lookupProviderTypeRepository.findAll();
        assertThat(lookupProviderTypes).hasSize(databaseSizeBeforeDelete - 1);
    }
}
