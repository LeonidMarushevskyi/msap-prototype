package com.engagepoint.msap.web.rest;

import com.engagepoint.msap.Application;
import com.engagepoint.msap.domain.LookupSpecialNeedType;
import com.engagepoint.msap.repository.LookupSpecialNeedTypeRepository;
import com.engagepoint.msap.repository.search.LookupSpecialNeedTypeSearchRepository;

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
 * Test class for the LookupSpecialNeedTypeResource REST controller.
 *
 * @see LookupSpecialNeedTypeResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class LookupSpecialNeedTypeResourceIntTest {


    private static final Integer DEFAULT_CODE = 1;
    private static final Integer UPDATED_CODE = 2;
    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";

    @Inject
    private LookupSpecialNeedTypeRepository lookupSpecialNeedTypeRepository;

    @Inject
    private LookupSpecialNeedTypeSearchRepository lookupSpecialNeedTypeSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restLookupSpecialNeedTypeMockMvc;

    private LookupSpecialNeedType lookupSpecialNeedType;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        LookupSpecialNeedTypeResource lookupSpecialNeedTypeResource = new LookupSpecialNeedTypeResource();
        ReflectionTestUtils.setField(lookupSpecialNeedTypeResource, "lookupSpecialNeedTypeSearchRepository", lookupSpecialNeedTypeSearchRepository);
        ReflectionTestUtils.setField(lookupSpecialNeedTypeResource, "lookupSpecialNeedTypeRepository", lookupSpecialNeedTypeRepository);
        this.restLookupSpecialNeedTypeMockMvc = MockMvcBuilders.standaloneSetup(lookupSpecialNeedTypeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        lookupSpecialNeedType = new LookupSpecialNeedType();
        lookupSpecialNeedType.setCode(DEFAULT_CODE);
        lookupSpecialNeedType.setName(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createLookupSpecialNeedType() throws Exception {
        int databaseSizeBeforeCreate = lookupSpecialNeedTypeRepository.findAll().size();

        // Create the LookupSpecialNeedType

        restLookupSpecialNeedTypeMockMvc.perform(post("/api/lookupSpecialNeedTypes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(lookupSpecialNeedType)))
                .andExpect(status().isCreated());

        // Validate the LookupSpecialNeedType in the database
        List<LookupSpecialNeedType> lookupSpecialNeedTypes = lookupSpecialNeedTypeRepository.findAll();
        assertThat(lookupSpecialNeedTypes).hasSize(databaseSizeBeforeCreate + 1);
        LookupSpecialNeedType testLookupSpecialNeedType = lookupSpecialNeedTypes.get(lookupSpecialNeedTypes.size() - 1);
        assertThat(testLookupSpecialNeedType.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testLookupSpecialNeedType.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = lookupSpecialNeedTypeRepository.findAll().size();
        // set the field null
        lookupSpecialNeedType.setCode(null);

        // Create the LookupSpecialNeedType, which fails.

        restLookupSpecialNeedTypeMockMvc.perform(post("/api/lookupSpecialNeedTypes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(lookupSpecialNeedType)))
                .andExpect(status().isBadRequest());

        List<LookupSpecialNeedType> lookupSpecialNeedTypes = lookupSpecialNeedTypeRepository.findAll();
        assertThat(lookupSpecialNeedTypes).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = lookupSpecialNeedTypeRepository.findAll().size();
        // set the field null
        lookupSpecialNeedType.setName(null);

        // Create the LookupSpecialNeedType, which fails.

        restLookupSpecialNeedTypeMockMvc.perform(post("/api/lookupSpecialNeedTypes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(lookupSpecialNeedType)))
                .andExpect(status().isBadRequest());

        List<LookupSpecialNeedType> lookupSpecialNeedTypes = lookupSpecialNeedTypeRepository.findAll();
        assertThat(lookupSpecialNeedTypes).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllLookupSpecialNeedTypes() throws Exception {
        // Initialize the database
        lookupSpecialNeedTypeRepository.saveAndFlush(lookupSpecialNeedType);

        // Get all the lookupSpecialNeedTypes
        restLookupSpecialNeedTypeMockMvc.perform(get("/api/lookupSpecialNeedTypes?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(lookupSpecialNeedType.getId().intValue())))
                .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }

    @Test
    @Transactional
    public void getLookupSpecialNeedType() throws Exception {
        // Initialize the database
        lookupSpecialNeedTypeRepository.saveAndFlush(lookupSpecialNeedType);

        // Get the lookupSpecialNeedType
        restLookupSpecialNeedTypeMockMvc.perform(get("/api/lookupSpecialNeedTypes/{id}", lookupSpecialNeedType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(lookupSpecialNeedType.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    public void getNonExistingLookupSpecialNeedType() throws Exception {
        // Get the lookupSpecialNeedType
        restLookupSpecialNeedTypeMockMvc.perform(get("/api/lookupSpecialNeedTypes/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLookupSpecialNeedType() throws Exception {
        // Initialize the database
        lookupSpecialNeedTypeRepository.saveAndFlush(lookupSpecialNeedType);

		int databaseSizeBeforeUpdate = lookupSpecialNeedTypeRepository.findAll().size();

        // Update the lookupSpecialNeedType
        lookupSpecialNeedType.setCode(UPDATED_CODE);
        lookupSpecialNeedType.setName(UPDATED_NAME);

        restLookupSpecialNeedTypeMockMvc.perform(put("/api/lookupSpecialNeedTypes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(lookupSpecialNeedType)))
                .andExpect(status().isOk());

        // Validate the LookupSpecialNeedType in the database
        List<LookupSpecialNeedType> lookupSpecialNeedTypes = lookupSpecialNeedTypeRepository.findAll();
        assertThat(lookupSpecialNeedTypes).hasSize(databaseSizeBeforeUpdate);
        LookupSpecialNeedType testLookupSpecialNeedType = lookupSpecialNeedTypes.get(lookupSpecialNeedTypes.size() - 1);
        assertThat(testLookupSpecialNeedType.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testLookupSpecialNeedType.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void deleteLookupSpecialNeedType() throws Exception {
        // Initialize the database
        lookupSpecialNeedTypeRepository.saveAndFlush(lookupSpecialNeedType);

		int databaseSizeBeforeDelete = lookupSpecialNeedTypeRepository.findAll().size();

        // Get the lookupSpecialNeedType
        restLookupSpecialNeedTypeMockMvc.perform(delete("/api/lookupSpecialNeedTypes/{id}", lookupSpecialNeedType.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<LookupSpecialNeedType> lookupSpecialNeedTypes = lookupSpecialNeedTypeRepository.findAll();
        assertThat(lookupSpecialNeedTypes).hasSize(databaseSizeBeforeDelete - 1);
    }
}
