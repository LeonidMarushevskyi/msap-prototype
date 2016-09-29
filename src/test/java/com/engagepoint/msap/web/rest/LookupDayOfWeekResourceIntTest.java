package com.engagepoint.msap.web.rest;

import com.engagepoint.msap.Application;
import com.engagepoint.msap.domain.LookupDayOfWeek;
import com.engagepoint.msap.repository.LookupDayOfWeekRepository;
import com.engagepoint.msap.repository.search.LookupDayOfWeekSearchRepository;

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
 * Test class for the LookupDayOfWeekResource REST controller.
 *
 * @see LookupDayOfWeekResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class LookupDayOfWeekResourceIntTest {


    private static final Integer DEFAULT_CODE = 1;
    private static final Integer UPDATED_CODE = 2;
    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";

    @Inject
    private LookupDayOfWeekRepository lookupDayOfWeekRepository;

    @Inject
    private LookupDayOfWeekSearchRepository lookupDayOfWeekSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restLookupDayOfWeekMockMvc;

    private LookupDayOfWeek lookupDayOfWeek;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        LookupDayOfWeekResource lookupDayOfWeekResource = new LookupDayOfWeekResource();
        ReflectionTestUtils.setField(lookupDayOfWeekResource, "lookupDayOfWeekSearchRepository", lookupDayOfWeekSearchRepository);
        ReflectionTestUtils.setField(lookupDayOfWeekResource, "lookupDayOfWeekRepository", lookupDayOfWeekRepository);
        this.restLookupDayOfWeekMockMvc = MockMvcBuilders.standaloneSetup(lookupDayOfWeekResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        lookupDayOfWeek = new LookupDayOfWeek();
        lookupDayOfWeek.setCode(DEFAULT_CODE);
        lookupDayOfWeek.setName(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createLookupDayOfWeek() throws Exception {
        int databaseSizeBeforeCreate = lookupDayOfWeekRepository.findAll().size();

        // Create the LookupDayOfWeek

        restLookupDayOfWeekMockMvc.perform(post("/api/lookupDayOfWeeks")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(lookupDayOfWeek)))
                .andExpect(status().isCreated());

        // Validate the LookupDayOfWeek in the database
        List<LookupDayOfWeek> lookupDayOfWeeks = lookupDayOfWeekRepository.findAll();
        assertThat(lookupDayOfWeeks).hasSize(databaseSizeBeforeCreate + 1);
        LookupDayOfWeek testLookupDayOfWeek = lookupDayOfWeeks.get(lookupDayOfWeeks.size() - 1);
        assertThat(testLookupDayOfWeek.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testLookupDayOfWeek.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = lookupDayOfWeekRepository.findAll().size();
        // set the field null
        lookupDayOfWeek.setCode(null);

        // Create the LookupDayOfWeek, which fails.

        restLookupDayOfWeekMockMvc.perform(post("/api/lookupDayOfWeeks")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(lookupDayOfWeek)))
                .andExpect(status().isBadRequest());

        List<LookupDayOfWeek> lookupDayOfWeeks = lookupDayOfWeekRepository.findAll();
        assertThat(lookupDayOfWeeks).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = lookupDayOfWeekRepository.findAll().size();
        // set the field null
        lookupDayOfWeek.setName(null);

        // Create the LookupDayOfWeek, which fails.

        restLookupDayOfWeekMockMvc.perform(post("/api/lookupDayOfWeeks")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(lookupDayOfWeek)))
                .andExpect(status().isBadRequest());

        List<LookupDayOfWeek> lookupDayOfWeeks = lookupDayOfWeekRepository.findAll();
        assertThat(lookupDayOfWeeks).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllLookupDayOfWeeks() throws Exception {
        // Initialize the database
        lookupDayOfWeekRepository.saveAndFlush(lookupDayOfWeek);

        // Get all the lookupDayOfWeeks
        restLookupDayOfWeekMockMvc.perform(get("/api/lookupDayOfWeeks?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(lookupDayOfWeek.getId().intValue())))
                .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }

    @Test
    @Transactional
    public void getLookupDayOfWeek() throws Exception {
        // Initialize the database
        lookupDayOfWeekRepository.saveAndFlush(lookupDayOfWeek);

        // Get the lookupDayOfWeek
        restLookupDayOfWeekMockMvc.perform(get("/api/lookupDayOfWeeks/{id}", lookupDayOfWeek.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(lookupDayOfWeek.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    public void getNonExistingLookupDayOfWeek() throws Exception {
        // Get the lookupDayOfWeek
        restLookupDayOfWeekMockMvc.perform(get("/api/lookupDayOfWeeks/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLookupDayOfWeek() throws Exception {
        // Initialize the database
        lookupDayOfWeekRepository.saveAndFlush(lookupDayOfWeek);

		int databaseSizeBeforeUpdate = lookupDayOfWeekRepository.findAll().size();

        // Update the lookupDayOfWeek
        lookupDayOfWeek.setCode(UPDATED_CODE);
        lookupDayOfWeek.setName(UPDATED_NAME);

        restLookupDayOfWeekMockMvc.perform(put("/api/lookupDayOfWeeks")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(lookupDayOfWeek)))
                .andExpect(status().isOk());

        // Validate the LookupDayOfWeek in the database
        List<LookupDayOfWeek> lookupDayOfWeeks = lookupDayOfWeekRepository.findAll();
        assertThat(lookupDayOfWeeks).hasSize(databaseSizeBeforeUpdate);
        LookupDayOfWeek testLookupDayOfWeek = lookupDayOfWeeks.get(lookupDayOfWeeks.size() - 1);
        assertThat(testLookupDayOfWeek.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testLookupDayOfWeek.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void deleteLookupDayOfWeek() throws Exception {
        // Initialize the database
        lookupDayOfWeekRepository.saveAndFlush(lookupDayOfWeek);

		int databaseSizeBeforeDelete = lookupDayOfWeekRepository.findAll().size();

        // Get the lookupDayOfWeek
        restLookupDayOfWeekMockMvc.perform(delete("/api/lookupDayOfWeeks/{id}", lookupDayOfWeek.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<LookupDayOfWeek> lookupDayOfWeeks = lookupDayOfWeekRepository.findAll();
        assertThat(lookupDayOfWeeks).hasSize(databaseSizeBeforeDelete - 1);
    }
}
