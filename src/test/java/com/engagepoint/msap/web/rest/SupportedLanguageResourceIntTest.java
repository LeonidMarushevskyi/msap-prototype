package com.engagepoint.msap.web.rest;

import com.engagepoint.msap.Application;
import com.engagepoint.msap.domain.SupportedLanguage;
import com.engagepoint.msap.repository.SupportedLanguageRepository;
import com.engagepoint.msap.repository.search.SupportedLanguageSearchRepository;

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
 * Test class for the SupportedLanguageResource REST controller.
 *
 * @see SupportedLanguageResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class SupportedLanguageResourceIntTest {


    @Inject
    private SupportedLanguageRepository supportedLanguageRepository;

    @Inject
    private SupportedLanguageSearchRepository supportedLanguageSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restSupportedLanguageMockMvc;

    private SupportedLanguage supportedLanguage;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SupportedLanguageResource supportedLanguageResource = new SupportedLanguageResource();
        ReflectionTestUtils.setField(supportedLanguageResource, "supportedLanguageSearchRepository", supportedLanguageSearchRepository);
        ReflectionTestUtils.setField(supportedLanguageResource, "supportedLanguageRepository", supportedLanguageRepository);
        this.restSupportedLanguageMockMvc = MockMvcBuilders.standaloneSetup(supportedLanguageResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        supportedLanguage = new SupportedLanguage();
    }

    @Test
    @Transactional
    public void createSupportedLanguage() throws Exception {
        int databaseSizeBeforeCreate = supportedLanguageRepository.findAll().size();

        // Create the SupportedLanguage

        restSupportedLanguageMockMvc.perform(post("/api/supportedLanguages")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(supportedLanguage)))
                .andExpect(status().isCreated());

        // Validate the SupportedLanguage in the database
        List<SupportedLanguage> supportedLanguages = supportedLanguageRepository.findAll();
        assertThat(supportedLanguages).hasSize(databaseSizeBeforeCreate + 1);
        SupportedLanguage testSupportedLanguage = supportedLanguages.get(supportedLanguages.size() - 1);
    }

    @Test
    @Transactional
    public void getAllSupportedLanguages() throws Exception {
        // Initialize the database
        supportedLanguageRepository.saveAndFlush(supportedLanguage);

        // Get all the supportedLanguages
        restSupportedLanguageMockMvc.perform(get("/api/supportedLanguages?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(supportedLanguage.getId().intValue())));
    }

    @Test
    @Transactional
    public void getSupportedLanguage() throws Exception {
        // Initialize the database
        supportedLanguageRepository.saveAndFlush(supportedLanguage);

        // Get the supportedLanguage
        restSupportedLanguageMockMvc.perform(get("/api/supportedLanguages/{id}", supportedLanguage.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(supportedLanguage.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingSupportedLanguage() throws Exception {
        // Get the supportedLanguage
        restSupportedLanguageMockMvc.perform(get("/api/supportedLanguages/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSupportedLanguage() throws Exception {
        // Initialize the database
        supportedLanguageRepository.saveAndFlush(supportedLanguage);

		int databaseSizeBeforeUpdate = supportedLanguageRepository.findAll().size();

        // Update the supportedLanguage

        restSupportedLanguageMockMvc.perform(put("/api/supportedLanguages")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(supportedLanguage)))
                .andExpect(status().isOk());

        // Validate the SupportedLanguage in the database
        List<SupportedLanguage> supportedLanguages = supportedLanguageRepository.findAll();
        assertThat(supportedLanguages).hasSize(databaseSizeBeforeUpdate);
        SupportedLanguage testSupportedLanguage = supportedLanguages.get(supportedLanguages.size() - 1);
    }

    @Test
    @Transactional
    public void deleteSupportedLanguage() throws Exception {
        // Initialize the database
        supportedLanguageRepository.saveAndFlush(supportedLanguage);

		int databaseSizeBeforeDelete = supportedLanguageRepository.findAll().size();

        // Get the supportedLanguage
        restSupportedLanguageMockMvc.perform(delete("/api/supportedLanguages/{id}", supportedLanguage.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<SupportedLanguage> supportedLanguages = supportedLanguageRepository.findAll();
        assertThat(supportedLanguages).hasSize(databaseSizeBeforeDelete - 1);
    }
}
