package com.engagepoint.msap.web.rest;

import com.engagepoint.msap.Application;
import com.engagepoint.msap.domain.LookupLanguage;
import com.engagepoint.msap.repository.LookupLanguageRepository;
import com.engagepoint.msap.repository.search.LookupLanguageSearchRepository;

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
 * Test class for the LookupLanguageResource REST controller.
 *
 * @see LookupLanguageResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class LookupLanguageResourceIntTest {

    private static final String DEFAULT_CODE = "AA";
    private static final String UPDATED_CODE = "BB";
    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";

    @Inject
    private LookupLanguageRepository lookupLanguageRepository;

    @Inject
    private LookupLanguageSearchRepository lookupLanguageSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restLookupLanguageMockMvc;

    private LookupLanguage lookupLanguage;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        LookupLanguageResource lookupLanguageResource = new LookupLanguageResource();
        ReflectionTestUtils.setField(lookupLanguageResource, "lookupLanguageSearchRepository", lookupLanguageSearchRepository);
        ReflectionTestUtils.setField(lookupLanguageResource, "lookupLanguageRepository", lookupLanguageRepository);
        this.restLookupLanguageMockMvc = MockMvcBuilders.standaloneSetup(lookupLanguageResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        lookupLanguage = new LookupLanguage();
        lookupLanguage.setCode(DEFAULT_CODE);
        lookupLanguage.setName(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createLookupLanguage() throws Exception {
        int databaseSizeBeforeCreate = lookupLanguageRepository.findAll().size();

        // Create the LookupLanguage

        restLookupLanguageMockMvc.perform(post("/api/lookupLanguages")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(lookupLanguage)))
                .andExpect(status().isCreated());

        // Validate the LookupLanguage in the database
        List<LookupLanguage> lookupLanguages = lookupLanguageRepository.findAll();
        assertThat(lookupLanguages).hasSize(databaseSizeBeforeCreate + 1);
        LookupLanguage testLookupLanguage = lookupLanguages.get(lookupLanguages.size() - 1);
        assertThat(testLookupLanguage.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testLookupLanguage.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = lookupLanguageRepository.findAll().size();
        // set the field null
        lookupLanguage.setCode(null);

        // Create the LookupLanguage, which fails.

        restLookupLanguageMockMvc.perform(post("/api/lookupLanguages")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(lookupLanguage)))
                .andExpect(status().isBadRequest());

        List<LookupLanguage> lookupLanguages = lookupLanguageRepository.findAll();
        assertThat(lookupLanguages).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = lookupLanguageRepository.findAll().size();
        // set the field null
        lookupLanguage.setName(null);

        // Create the LookupLanguage, which fails.

        restLookupLanguageMockMvc.perform(post("/api/lookupLanguages")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(lookupLanguage)))
                .andExpect(status().isBadRequest());

        List<LookupLanguage> lookupLanguages = lookupLanguageRepository.findAll();
        assertThat(lookupLanguages).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllLookupLanguages() throws Exception {
        // Initialize the database
        lookupLanguageRepository.saveAndFlush(lookupLanguage);

        // Get all the lookupLanguages
        restLookupLanguageMockMvc.perform(get("/api/lookupLanguages?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(lookupLanguage.getId().intValue())))
                .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getLookupLanguage() throws Exception {
        // Initialize the database
        lookupLanguageRepository.saveAndFlush(lookupLanguage);

        // Get the lookupLanguage
        restLookupLanguageMockMvc.perform(get("/api/lookupLanguages/{id}", lookupLanguage.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(lookupLanguage.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingLookupLanguage() throws Exception {
        // Get the lookupLanguage
        restLookupLanguageMockMvc.perform(get("/api/lookupLanguages/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLookupLanguage() throws Exception {
        // Initialize the database
        lookupLanguageRepository.saveAndFlush(lookupLanguage);

		int databaseSizeBeforeUpdate = lookupLanguageRepository.findAll().size();

        // Update the lookupLanguage
        lookupLanguage.setCode(UPDATED_CODE);
        lookupLanguage.setName(UPDATED_NAME);

        restLookupLanguageMockMvc.perform(put("/api/lookupLanguages")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(lookupLanguage)))
                .andExpect(status().isOk());

        // Validate the LookupLanguage in the database
        List<LookupLanguage> lookupLanguages = lookupLanguageRepository.findAll();
        assertThat(lookupLanguages).hasSize(databaseSizeBeforeUpdate);
        LookupLanguage testLookupLanguage = lookupLanguages.get(lookupLanguages.size() - 1);
        assertThat(testLookupLanguage.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testLookupLanguage.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void deleteLookupLanguage() throws Exception {
        // Initialize the database
        lookupLanguageRepository.saveAndFlush(lookupLanguage);

		int databaseSizeBeforeDelete = lookupLanguageRepository.findAll().size();

        // Get the lookupLanguage
        restLookupLanguageMockMvc.perform(delete("/api/lookupLanguages/{id}", lookupLanguage.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<LookupLanguage> lookupLanguages = lookupLanguageRepository.findAll();
        assertThat(lookupLanguages).hasSize(databaseSizeBeforeDelete - 1);
    }
}
