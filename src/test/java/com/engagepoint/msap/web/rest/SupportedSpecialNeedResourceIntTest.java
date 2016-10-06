package com.engagepoint.msap.web.rest;

import com.engagepoint.msap.Application;
import com.engagepoint.msap.domain.SupportedSpecialNeed;
import com.engagepoint.msap.repository.SupportedSpecialNeedRepository;
import com.engagepoint.msap.repository.search.SupportedSpecialNeedSearchRepository;

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
 * Test class for the SupportedSpecialNeedResource REST controller.
 *
 * @see SupportedSpecialNeedResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class SupportedSpecialNeedResourceIntTest {


    @Inject
    private SupportedSpecialNeedRepository supportedSpecialNeedRepository;

    @Inject
    private SupportedSpecialNeedSearchRepository supportedSpecialNeedSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restSupportedSpecialNeedMockMvc;

    private SupportedSpecialNeed supportedSpecialNeed;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SupportedSpecialNeedResource supportedSpecialNeedResource = new SupportedSpecialNeedResource();
        ReflectionTestUtils.setField(supportedSpecialNeedResource, "supportedSpecialNeedSearchRepository", supportedSpecialNeedSearchRepository);
        ReflectionTestUtils.setField(supportedSpecialNeedResource, "supportedSpecialNeedRepository", supportedSpecialNeedRepository);
        this.restSupportedSpecialNeedMockMvc = MockMvcBuilders.standaloneSetup(supportedSpecialNeedResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        supportedSpecialNeed = new SupportedSpecialNeed();
    }

    @Test
    @Transactional
    public void createSupportedSpecialNeed() throws Exception {
        int databaseSizeBeforeCreate = supportedSpecialNeedRepository.findAll().size();

        // Create the SupportedSpecialNeed

        restSupportedSpecialNeedMockMvc.perform(post("/api/supportedSpecialNeeds")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(supportedSpecialNeed)))
                .andExpect(status().isCreated());

        // Validate the SupportedSpecialNeed in the database
        List<SupportedSpecialNeed> supportedSpecialNeeds = supportedSpecialNeedRepository.findAll();
        assertThat(supportedSpecialNeeds).hasSize(databaseSizeBeforeCreate + 1);
        SupportedSpecialNeed testSupportedSpecialNeed = supportedSpecialNeeds.get(supportedSpecialNeeds.size() - 1);
    }

    @Test
    @Transactional
    public void getAllSupportedSpecialNeeds() throws Exception {
        // Initialize the database
        supportedSpecialNeedRepository.saveAndFlush(supportedSpecialNeed);

        // Get all the supportedSpecialNeeds
        restSupportedSpecialNeedMockMvc.perform(get("/api/supportedSpecialNeeds?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(supportedSpecialNeed.getId().intValue())));
    }

    @Test
    @Transactional
    public void getSupportedSpecialNeed() throws Exception {
        // Initialize the database
        supportedSpecialNeedRepository.saveAndFlush(supportedSpecialNeed);

        // Get the supportedSpecialNeed
        restSupportedSpecialNeedMockMvc.perform(get("/api/supportedSpecialNeeds/{id}", supportedSpecialNeed.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(supportedSpecialNeed.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingSupportedSpecialNeed() throws Exception {
        // Get the supportedSpecialNeed
        restSupportedSpecialNeedMockMvc.perform(get("/api/supportedSpecialNeeds/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSupportedSpecialNeed() throws Exception {
        // Initialize the database
        supportedSpecialNeedRepository.saveAndFlush(supportedSpecialNeed);

		int databaseSizeBeforeUpdate = supportedSpecialNeedRepository.findAll().size();

        // Update the supportedSpecialNeed

        restSupportedSpecialNeedMockMvc.perform(put("/api/supportedSpecialNeeds")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(supportedSpecialNeed)))
                .andExpect(status().isOk());

        // Validate the SupportedSpecialNeed in the database
        List<SupportedSpecialNeed> supportedSpecialNeeds = supportedSpecialNeedRepository.findAll();
        assertThat(supportedSpecialNeeds).hasSize(databaseSizeBeforeUpdate);
        SupportedSpecialNeed testSupportedSpecialNeed = supportedSpecialNeeds.get(supportedSpecialNeeds.size() - 1);
    }

    @Test
    @Transactional
    public void deleteSupportedSpecialNeed() throws Exception {
        // Initialize the database
        supportedSpecialNeedRepository.saveAndFlush(supportedSpecialNeed);

		int databaseSizeBeforeDelete = supportedSpecialNeedRepository.findAll().size();

        // Get the supportedSpecialNeed
        restSupportedSpecialNeedMockMvc.perform(delete("/api/supportedSpecialNeeds/{id}", supportedSpecialNeed.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<SupportedSpecialNeed> supportedSpecialNeeds = supportedSpecialNeedRepository.findAll();
        assertThat(supportedSpecialNeeds).hasSize(databaseSizeBeforeDelete - 1);
    }
}
