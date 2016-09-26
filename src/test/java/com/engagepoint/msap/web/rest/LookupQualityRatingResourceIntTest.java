package com.engagepoint.msap.web.rest;

import com.engagepoint.msap.Application;
import com.engagepoint.msap.domain.LookupQualityRating;
import com.engagepoint.msap.repository.LookupQualityRatingRepository;
import com.engagepoint.msap.repository.search.LookupQualityRatingSearchRepository;

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
 * Test class for the LookupQualityRatingResource REST controller.
 *
 * @see LookupQualityRatingResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class LookupQualityRatingResourceIntTest {


    private static final Integer DEFAULT_CODE = 1;
    private static final Integer UPDATED_CODE = 2;
    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";

    @Inject
    private LookupQualityRatingRepository lookupQualityRatingRepository;

    @Inject
    private LookupQualityRatingSearchRepository lookupQualityRatingSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restLookupQualityRatingMockMvc;

    private LookupQualityRating lookupQualityRating;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        LookupQualityRatingResource lookupQualityRatingResource = new LookupQualityRatingResource();
        ReflectionTestUtils.setField(lookupQualityRatingResource, "lookupQualityRatingSearchRepository", lookupQualityRatingSearchRepository);
        ReflectionTestUtils.setField(lookupQualityRatingResource, "lookupQualityRatingRepository", lookupQualityRatingRepository);
        this.restLookupQualityRatingMockMvc = MockMvcBuilders.standaloneSetup(lookupQualityRatingResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        lookupQualityRating = new LookupQualityRating();
        lookupQualityRating.setCode(DEFAULT_CODE);
        lookupQualityRating.setName(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createLookupQualityRating() throws Exception {
        int databaseSizeBeforeCreate = lookupQualityRatingRepository.findAll().size();

        // Create the LookupQualityRating

        restLookupQualityRatingMockMvc.perform(post("/api/lookupQualityRatings")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(lookupQualityRating)))
                .andExpect(status().isCreated());

        // Validate the LookupQualityRating in the database
        List<LookupQualityRating> lookupQualityRatings = lookupQualityRatingRepository.findAll();
        assertThat(lookupQualityRatings).hasSize(databaseSizeBeforeCreate + 1);
        LookupQualityRating testLookupQualityRating = lookupQualityRatings.get(lookupQualityRatings.size() - 1);
        assertThat(testLookupQualityRating.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testLookupQualityRating.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = lookupQualityRatingRepository.findAll().size();
        // set the field null
        lookupQualityRating.setCode(null);

        // Create the LookupQualityRating, which fails.

        restLookupQualityRatingMockMvc.perform(post("/api/lookupQualityRatings")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(lookupQualityRating)))
                .andExpect(status().isBadRequest());

        List<LookupQualityRating> lookupQualityRatings = lookupQualityRatingRepository.findAll();
        assertThat(lookupQualityRatings).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = lookupQualityRatingRepository.findAll().size();
        // set the field null
        lookupQualityRating.setName(null);

        // Create the LookupQualityRating, which fails.

        restLookupQualityRatingMockMvc.perform(post("/api/lookupQualityRatings")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(lookupQualityRating)))
                .andExpect(status().isBadRequest());

        List<LookupQualityRating> lookupQualityRatings = lookupQualityRatingRepository.findAll();
        assertThat(lookupQualityRatings).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllLookupQualityRatings() throws Exception {
        // Initialize the database
        lookupQualityRatingRepository.saveAndFlush(lookupQualityRating);

        // Get all the lookupQualityRatings
        restLookupQualityRatingMockMvc.perform(get("/api/lookupQualityRatings?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(lookupQualityRating.getId().intValue())))
                .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getLookupQualityRating() throws Exception {
        // Initialize the database
        lookupQualityRatingRepository.saveAndFlush(lookupQualityRating);

        // Get the lookupQualityRating
        restLookupQualityRatingMockMvc.perform(get("/api/lookupQualityRatings/{id}", lookupQualityRating.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(lookupQualityRating.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingLookupQualityRating() throws Exception {
        // Get the lookupQualityRating
        restLookupQualityRatingMockMvc.perform(get("/api/lookupQualityRatings/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLookupQualityRating() throws Exception {
        // Initialize the database
        lookupQualityRatingRepository.saveAndFlush(lookupQualityRating);

		int databaseSizeBeforeUpdate = lookupQualityRatingRepository.findAll().size();

        // Update the lookupQualityRating
        lookupQualityRating.setCode(UPDATED_CODE);
        lookupQualityRating.setName(UPDATED_NAME);

        restLookupQualityRatingMockMvc.perform(put("/api/lookupQualityRatings")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(lookupQualityRating)))
                .andExpect(status().isOk());

        // Validate the LookupQualityRating in the database
        List<LookupQualityRating> lookupQualityRatings = lookupQualityRatingRepository.findAll();
        assertThat(lookupQualityRatings).hasSize(databaseSizeBeforeUpdate);
        LookupQualityRating testLookupQualityRating = lookupQualityRatings.get(lookupQualityRatings.size() - 1);
        assertThat(testLookupQualityRating.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testLookupQualityRating.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void deleteLookupQualityRating() throws Exception {
        // Initialize the database
        lookupQualityRatingRepository.saveAndFlush(lookupQualityRating);

		int databaseSizeBeforeDelete = lookupQualityRatingRepository.findAll().size();

        // Get the lookupQualityRating
        restLookupQualityRatingMockMvc.perform(delete("/api/lookupQualityRatings/{id}", lookupQualityRating.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<LookupQualityRating> lookupQualityRatings = lookupQualityRatingRepository.findAll();
        assertThat(lookupQualityRatings).hasSize(databaseSizeBeforeDelete - 1);
    }
}
