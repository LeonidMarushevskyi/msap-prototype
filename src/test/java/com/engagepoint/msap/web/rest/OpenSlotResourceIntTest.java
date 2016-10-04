package com.engagepoint.msap.web.rest;

import com.engagepoint.msap.Application;
import com.engagepoint.msap.domain.OpenSlot;
import com.engagepoint.msap.repository.OpenSlotRepository;
import com.engagepoint.msap.repository.search.OpenSlotSearchRepository;

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
 * Test class for the OpenSlotResource REST controller.
 *
 * @see OpenSlotResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class OpenSlotResourceIntTest {


    private static final Integer DEFAULT_OPEN_SLOTS = 1;
    private static final Integer UPDATED_OPEN_SLOTS = 2;

    @Inject
    private OpenSlotRepository openSlotRepository;

    @Inject
    private OpenSlotSearchRepository openSlotSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restOpenSlotMockMvc;

    private OpenSlot openSlot;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        OpenSlotResource openSlotResource = new OpenSlotResource();
        ReflectionTestUtils.setField(openSlotResource, "openSlotSearchRepository", openSlotSearchRepository);
        ReflectionTestUtils.setField(openSlotResource, "openSlotRepository", openSlotRepository);
        this.restOpenSlotMockMvc = MockMvcBuilders.standaloneSetup(openSlotResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        openSlot = new OpenSlot();
        openSlot.setOpenSlots(DEFAULT_OPEN_SLOTS);
    }

    @Test
    @Transactional
    public void createOpenSlot() throws Exception {
        int databaseSizeBeforeCreate = openSlotRepository.findAll().size();

        // Create the OpenSlot

        restOpenSlotMockMvc.perform(post("/api/openSlots")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(openSlot)))
                .andExpect(status().isCreated());

        // Validate the OpenSlot in the database
        List<OpenSlot> openSlots = openSlotRepository.findAll();
        assertThat(openSlots).hasSize(databaseSizeBeforeCreate + 1);
        OpenSlot testOpenSlot = openSlots.get(openSlots.size() - 1);
        assertThat(testOpenSlot.getOpenSlots()).isEqualTo(DEFAULT_OPEN_SLOTS);
    }

    @Test
    @Transactional
    public void getAllOpenSlots() throws Exception {
        // Initialize the database
        openSlotRepository.saveAndFlush(openSlot);

        // Get all the openSlots
        restOpenSlotMockMvc.perform(get("/api/openSlots?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(openSlot.getId().intValue())))
                .andExpect(jsonPath("$.[*].openSlots").value(hasItem(DEFAULT_OPEN_SLOTS)));
    }

    @Test
    @Transactional
    public void getOpenSlot() throws Exception {
        // Initialize the database
        openSlotRepository.saveAndFlush(openSlot);

        // Get the openSlot
        restOpenSlotMockMvc.perform(get("/api/openSlots/{id}", openSlot.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(openSlot.getId().intValue()))
            .andExpect(jsonPath("$.openSlots").value(DEFAULT_OPEN_SLOTS));
    }

    @Test
    @Transactional
    public void getNonExistingOpenSlot() throws Exception {
        // Get the openSlot
        restOpenSlotMockMvc.perform(get("/api/openSlots/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOpenSlot() throws Exception {
        // Initialize the database
        openSlotRepository.saveAndFlush(openSlot);

		int databaseSizeBeforeUpdate = openSlotRepository.findAll().size();

        // Update the openSlot
        openSlot.setOpenSlots(UPDATED_OPEN_SLOTS);

        restOpenSlotMockMvc.perform(put("/api/openSlots")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(openSlot)))
                .andExpect(status().isOk());

        // Validate the OpenSlot in the database
        List<OpenSlot> openSlots = openSlotRepository.findAll();
        assertThat(openSlots).hasSize(databaseSizeBeforeUpdate);
        OpenSlot testOpenSlot = openSlots.get(openSlots.size() - 1);
        assertThat(testOpenSlot.getOpenSlots()).isEqualTo(UPDATED_OPEN_SLOTS);
    }

    @Test
    @Transactional
    public void deleteOpenSlot() throws Exception {
        // Initialize the database
        openSlotRepository.saveAndFlush(openSlot);

		int databaseSizeBeforeDelete = openSlotRepository.findAll().size();

        // Get the openSlot
        restOpenSlotMockMvc.perform(delete("/api/openSlots/{id}", openSlot.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<OpenSlot> openSlots = openSlotRepository.findAll();
        assertThat(openSlots).hasSize(databaseSizeBeforeDelete - 1);
    }
}
