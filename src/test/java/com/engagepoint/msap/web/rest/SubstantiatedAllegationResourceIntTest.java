package com.engagepoint.msap.web.rest;

import com.engagepoint.msap.Application;
import com.engagepoint.msap.domain.SubstantiatedAllegation;
import com.engagepoint.msap.repository.SubstantiatedAllegationRepository;
import com.engagepoint.msap.repository.search.SubstantiatedAllegationSearchRepository;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the SubstantiatedAllegationResource REST controller.
 *
 * @see SubstantiatedAllegationResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class SubstantiatedAllegationResourceIntTest {

    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";
    private static final String DEFAULT_SEVERITY = "AAAAA";
    private static final String UPDATED_SEVERITY = "BBBBB";

    private static final LocalDate DEFAULT_ALLEGATION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ALLEGATION_DATE = LocalDate.now(ZoneId.systemDefault());

    @Inject
    private SubstantiatedAllegationRepository substantiatedAllegationRepository;

    @Inject
    private SubstantiatedAllegationSearchRepository substantiatedAllegationSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restSubstantiatedAllegationMockMvc;

    private SubstantiatedAllegation substantiatedAllegation;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SubstantiatedAllegationResource substantiatedAllegationResource = new SubstantiatedAllegationResource();
        ReflectionTestUtils.setField(substantiatedAllegationResource, "substantiatedAllegationSearchRepository", substantiatedAllegationSearchRepository);
        ReflectionTestUtils.setField(substantiatedAllegationResource, "substantiatedAllegationRepository", substantiatedAllegationRepository);
        this.restSubstantiatedAllegationMockMvc = MockMvcBuilders.standaloneSetup(substantiatedAllegationResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        substantiatedAllegation = new SubstantiatedAllegation();
        substantiatedAllegation.setDescription(DEFAULT_DESCRIPTION);
        substantiatedAllegation.setSeverity(DEFAULT_SEVERITY);
        substantiatedAllegation.setAllegationDate(DEFAULT_ALLEGATION_DATE);
    }

    @Test
    @Transactional
    public void createSubstantiatedAllegation() throws Exception {
        int databaseSizeBeforeCreate = substantiatedAllegationRepository.findAll().size();

        // Create the SubstantiatedAllegation

        restSubstantiatedAllegationMockMvc.perform(post("/api/substantiatedAllegations")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(substantiatedAllegation)))
                .andExpect(status().isCreated());

        // Validate the SubstantiatedAllegation in the database
        List<SubstantiatedAllegation> substantiatedAllegations = substantiatedAllegationRepository.findAll();
        assertThat(substantiatedAllegations).hasSize(databaseSizeBeforeCreate + 1);
        SubstantiatedAllegation testSubstantiatedAllegation = substantiatedAllegations.get(substantiatedAllegations.size() - 1);
        assertThat(testSubstantiatedAllegation.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testSubstantiatedAllegation.getSeverity()).isEqualTo(DEFAULT_SEVERITY);
        assertThat(testSubstantiatedAllegation.getAllegationDate()).isEqualTo(DEFAULT_ALLEGATION_DATE);
    }

    @Test
    @Transactional
    public void getAllSubstantiatedAllegations() throws Exception {
        // Initialize the database
        substantiatedAllegationRepository.saveAndFlush(substantiatedAllegation);

        // Get all the substantiatedAllegations
        restSubstantiatedAllegationMockMvc.perform(get("/api/substantiatedAllegations?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(substantiatedAllegation.getId().intValue())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
                .andExpect(jsonPath("$.[*].severity").value(hasItem(DEFAULT_SEVERITY)))
                .andExpect(jsonPath("$.[*].allegationDate").value(hasItem(DEFAULT_ALLEGATION_DATE.toString())));
    }

    @Test
    @Transactional
    public void getSubstantiatedAllegation() throws Exception {
        // Initialize the database
        substantiatedAllegationRepository.saveAndFlush(substantiatedAllegation);

        // Get the substantiatedAllegation
        restSubstantiatedAllegationMockMvc.perform(get("/api/substantiatedAllegations/{id}", substantiatedAllegation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(substantiatedAllegation.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.severity").value(DEFAULT_SEVERITY))
            .andExpect(jsonPath("$.allegationDate").value(DEFAULT_ALLEGATION_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSubstantiatedAllegation() throws Exception {
        // Get the substantiatedAllegation
        restSubstantiatedAllegationMockMvc.perform(get("/api/substantiatedAllegations/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSubstantiatedAllegation() throws Exception {
        // Initialize the database
        substantiatedAllegationRepository.saveAndFlush(substantiatedAllegation);

		int databaseSizeBeforeUpdate = substantiatedAllegationRepository.findAll().size();

        // Update the substantiatedAllegation
        substantiatedAllegation.setDescription(UPDATED_DESCRIPTION);
        substantiatedAllegation.setSeverity(UPDATED_SEVERITY);
        substantiatedAllegation.setAllegationDate(UPDATED_ALLEGATION_DATE);

        restSubstantiatedAllegationMockMvc.perform(put("/api/substantiatedAllegations")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(substantiatedAllegation)))
                .andExpect(status().isOk());

        // Validate the SubstantiatedAllegation in the database
        List<SubstantiatedAllegation> substantiatedAllegations = substantiatedAllegationRepository.findAll();
        assertThat(substantiatedAllegations).hasSize(databaseSizeBeforeUpdate);
        SubstantiatedAllegation testSubstantiatedAllegation = substantiatedAllegations.get(substantiatedAllegations.size() - 1);
        assertThat(testSubstantiatedAllegation.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testSubstantiatedAllegation.getSeverity()).isEqualTo(UPDATED_SEVERITY);
        assertThat(testSubstantiatedAllegation.getAllegationDate()).isEqualTo(UPDATED_ALLEGATION_DATE);
    }

    @Test
    @Transactional
    public void deleteSubstantiatedAllegation() throws Exception {
        // Initialize the database
        substantiatedAllegationRepository.saveAndFlush(substantiatedAllegation);

		int databaseSizeBeforeDelete = substantiatedAllegationRepository.findAll().size();

        // Get the substantiatedAllegation
        restSubstantiatedAllegationMockMvc.perform(delete("/api/substantiatedAllegations/{id}", substantiatedAllegation.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<SubstantiatedAllegation> substantiatedAllegations = substantiatedAllegationRepository.findAll();
        assertThat(substantiatedAllegations).hasSize(databaseSizeBeforeDelete - 1);
    }
}
