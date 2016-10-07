package com.engagepoint.msap.web.rest;

import com.engagepoint.msap.Application;
import com.engagepoint.msap.domain.Provider;
import com.engagepoint.msap.repository.ProviderRepository;
import com.engagepoint.msap.repository.search.ProviderSearchRepository;

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
 * Test class for the ProviderResource REST controller.
 *
 * @see ProviderResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class ProviderResourceIntTest {


    private static final Integer DEFAULT_PROVIDER_CAPACITY = 1;
    private static final Integer UPDATED_PROVIDER_CAPACITY = 2;
    private static final String DEFAULT_PROVIDER_NAME = "AAAAA";
    private static final String UPDATED_PROVIDER_NAME = "BBBBB";
    private static final String DEFAULT_PHONE_NUMBER = "AAAAA";
    private static final String UPDATED_PHONE_NUMBER = "BBBBB";

    private static final Boolean DEFAULT_IS_OPEN_OVERNIGHT = false;
    private static final Boolean UPDATED_IS_OPEN_OVERNIGHT = true;

    private static final Integer DEFAULT_NUMBER_OF_COMPLAINS = 1;
    private static final Integer UPDATED_NUMBER_OF_COMPLAINS = 2;

    private static final Integer DEFAULT_NUMBER_OF_VISITS = 1;
    private static final Integer UPDATED_NUMBER_OF_VISITS = 2;

    private static final LocalDate DEFAULT_LAST_VISIT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_LAST_VISIT = LocalDate.now(ZoneId.systemDefault());
    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";

    private static final Boolean DEFAULT_IS_FULL_DAY = false;
    private static final Boolean UPDATED_IS_FULL_DAY = true;

    private static final Boolean DEFAULT_IS_AFTER_SCHOOL = false;
    private static final Boolean UPDATED_IS_AFTER_SCHOOL = true;

    private static final Boolean DEFAULT_IS_BEFORE_SCHOOL = false;
    private static final Boolean UPDATED_IS_BEFORE_SCHOOL = true;

    private static final Boolean DEFAULT_IS_WEEKEND_CARE = false;
    private static final Boolean UPDATED_IS_WEEKEND_CARE = true;

    private static final Boolean DEFAULT_IS_RESPITE_CARE = false;
    private static final Boolean UPDATED_IS_RESPITE_CARE = true;

    private static final Boolean DEFAULT_IS_SECOND_SHIFT = false;
    private static final Boolean UPDATED_IS_SECOND_SHIFT = true;

    @Inject
    private ProviderRepository providerRepository;

    @Inject
    private ProviderSearchRepository providerSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restProviderMockMvc;

    private Provider provider;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ProviderResource providerResource = new ProviderResource();
        ReflectionTestUtils.setField(providerResource, "providerSearchRepository", providerSearchRepository);
        ReflectionTestUtils.setField(providerResource, "providerRepository", providerRepository);
        this.restProviderMockMvc = MockMvcBuilders.standaloneSetup(providerResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        provider = new Provider();
        provider.setProviderCapacity(DEFAULT_PROVIDER_CAPACITY);
        provider.setProviderName(DEFAULT_PROVIDER_NAME);
        provider.setPhoneNumber(DEFAULT_PHONE_NUMBER);
        provider.setIsOpenOvernight(DEFAULT_IS_OPEN_OVERNIGHT);
        provider.setNumberOfComplains(DEFAULT_NUMBER_OF_COMPLAINS);
        provider.setNumberOfVisits(DEFAULT_NUMBER_OF_VISITS);
        provider.setLastVisit(DEFAULT_LAST_VISIT);
        provider.setDescription(DEFAULT_DESCRIPTION);
        provider.setIsFullDay(DEFAULT_IS_FULL_DAY);
        provider.setIsAfterSchool(DEFAULT_IS_AFTER_SCHOOL);
        provider.setIsBeforeSchool(DEFAULT_IS_BEFORE_SCHOOL);
        provider.setIsWeekendCare(DEFAULT_IS_WEEKEND_CARE);
        provider.setIsRespiteCare(DEFAULT_IS_RESPITE_CARE);
        provider.setIsSecondShift(DEFAULT_IS_SECOND_SHIFT);
    }

    @Test
    @Transactional
    public void createProvider() throws Exception {
        int databaseSizeBeforeCreate = providerRepository.findAll().size();

        // Create the Provider

        restProviderMockMvc.perform(post("/api/providers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(provider)))
                .andExpect(status().isCreated());

        // Validate the Provider in the database
        List<Provider> providers = providerRepository.findAll();
        assertThat(providers).hasSize(databaseSizeBeforeCreate + 1);
        Provider testProvider = providers.get(providers.size() - 1);
        assertThat(testProvider.getProviderCapacity()).isEqualTo(DEFAULT_PROVIDER_CAPACITY);
        assertThat(testProvider.getProviderName()).isEqualTo(DEFAULT_PROVIDER_NAME);
        assertThat(testProvider.getPhoneNumber()).isEqualTo(DEFAULT_PHONE_NUMBER);
        assertThat(testProvider.getIsOpenOvernight()).isEqualTo(DEFAULT_IS_OPEN_OVERNIGHT);
        assertThat(testProvider.getNumberOfComplains()).isEqualTo(DEFAULT_NUMBER_OF_COMPLAINS);
        assertThat(testProvider.getNumberOfVisits()).isEqualTo(DEFAULT_NUMBER_OF_VISITS);
        assertThat(testProvider.getLastVisit()).isEqualTo(DEFAULT_LAST_VISIT);
        assertThat(testProvider.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testProvider.getIsFullDay()).isEqualTo(DEFAULT_IS_FULL_DAY);
        assertThat(testProvider.getIsAfterSchool()).isEqualTo(DEFAULT_IS_AFTER_SCHOOL);
        assertThat(testProvider.getIsBeforeSchool()).isEqualTo(DEFAULT_IS_BEFORE_SCHOOL);
        assertThat(testProvider.getIsWeekendCare()).isEqualTo(DEFAULT_IS_WEEKEND_CARE);
        assertThat(testProvider.getIsRespiteCare()).isEqualTo(DEFAULT_IS_RESPITE_CARE);
        assertThat(testProvider.getIsSecondShift()).isEqualTo(DEFAULT_IS_SECOND_SHIFT);
    }

    @Test
    @Transactional
    public void getAllProviders() throws Exception {
        // Initialize the database
        providerRepository.saveAndFlush(provider);

        // Get all the providers
        restProviderMockMvc.perform(get("/api/providers?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(provider.getId().intValue())))
                .andExpect(jsonPath("$.[*].providerCapacity").value(hasItem(DEFAULT_PROVIDER_CAPACITY)))
                .andExpect(jsonPath("$.[*].providerName").value(hasItem(DEFAULT_PROVIDER_NAME.toString())))
                .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER.toString())))
                .andExpect(jsonPath("$.[*].isOpenOvernight").value(hasItem(DEFAULT_IS_OPEN_OVERNIGHT.booleanValue())))
                .andExpect(jsonPath("$.[*].numberOfComplains").value(hasItem(DEFAULT_NUMBER_OF_COMPLAINS)))
                .andExpect(jsonPath("$.[*].numberOfVisits").value(hasItem(DEFAULT_NUMBER_OF_VISITS)))
                .andExpect(jsonPath("$.[*].lastVisit").value(hasItem(DEFAULT_LAST_VISIT.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].isFullDay").value(hasItem(DEFAULT_IS_FULL_DAY.booleanValue())))
                .andExpect(jsonPath("$.[*].isAfterSchool").value(hasItem(DEFAULT_IS_AFTER_SCHOOL.booleanValue())))
                .andExpect(jsonPath("$.[*].isBeforeSchool").value(hasItem(DEFAULT_IS_BEFORE_SCHOOL.booleanValue())))
                .andExpect(jsonPath("$.[*].isWeekendCare").value(hasItem(DEFAULT_IS_WEEKEND_CARE.booleanValue())))
                .andExpect(jsonPath("$.[*].isRespiteCare").value(hasItem(DEFAULT_IS_RESPITE_CARE.booleanValue())))
                .andExpect(jsonPath("$.[*].isSecondShift").value(hasItem(DEFAULT_IS_SECOND_SHIFT.booleanValue())));
    }

    @Test
    @Transactional
    public void getProvider() throws Exception {
        // Initialize the database
        providerRepository.saveAndFlush(provider);

        // Get the provider
        restProviderMockMvc.perform(get("/api/providers/{id}", provider.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(provider.getId().intValue()))
            .andExpect(jsonPath("$.providerCapacity").value(DEFAULT_PROVIDER_CAPACITY))
            .andExpect(jsonPath("$.providerName").value(DEFAULT_PROVIDER_NAME.toString()))
            .andExpect(jsonPath("$.phoneNumber").value(DEFAULT_PHONE_NUMBER.toString()))
            .andExpect(jsonPath("$.isOpenOvernight").value(DEFAULT_IS_OPEN_OVERNIGHT.booleanValue()))
            .andExpect(jsonPath("$.numberOfComplains").value(DEFAULT_NUMBER_OF_COMPLAINS))
            .andExpect(jsonPath("$.numberOfVisits").value(DEFAULT_NUMBER_OF_VISITS))
            .andExpect(jsonPath("$.lastVisit").value(DEFAULT_LAST_VISIT.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.isFullDay").value(DEFAULT_IS_FULL_DAY.booleanValue()))
            .andExpect(jsonPath("$.isAfterSchool").value(DEFAULT_IS_AFTER_SCHOOL.booleanValue()))
            .andExpect(jsonPath("$.isBeforeSchool").value(DEFAULT_IS_BEFORE_SCHOOL.booleanValue()))
            .andExpect(jsonPath("$.isWeekendCare").value(DEFAULT_IS_WEEKEND_CARE.booleanValue()))
            .andExpect(jsonPath("$.isRespiteCare").value(DEFAULT_IS_RESPITE_CARE.booleanValue()))
            .andExpect(jsonPath("$.isSecondShift").value(DEFAULT_IS_SECOND_SHIFT.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingProvider() throws Exception {
        // Get the provider
        restProviderMockMvc.perform(get("/api/providers/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProvider() throws Exception {
        // Initialize the database
        providerRepository.saveAndFlush(provider);

		int databaseSizeBeforeUpdate = providerRepository.findAll().size();

        // Update the provider
        provider.setProviderCapacity(UPDATED_PROVIDER_CAPACITY);
        provider.setProviderName(UPDATED_PROVIDER_NAME);
        provider.setPhoneNumber(UPDATED_PHONE_NUMBER);
        provider.setIsOpenOvernight(UPDATED_IS_OPEN_OVERNIGHT);
        provider.setNumberOfComplains(UPDATED_NUMBER_OF_COMPLAINS);
        provider.setNumberOfVisits(UPDATED_NUMBER_OF_VISITS);
        provider.setLastVisit(UPDATED_LAST_VISIT);
        provider.setDescription(UPDATED_DESCRIPTION);
        provider.setIsFullDay(UPDATED_IS_FULL_DAY);
        provider.setIsAfterSchool(UPDATED_IS_AFTER_SCHOOL);
        provider.setIsBeforeSchool(UPDATED_IS_BEFORE_SCHOOL);
        provider.setIsWeekendCare(UPDATED_IS_WEEKEND_CARE);
        provider.setIsRespiteCare(UPDATED_IS_RESPITE_CARE);
        provider.setIsSecondShift(UPDATED_IS_SECOND_SHIFT);

        restProviderMockMvc.perform(put("/api/providers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(provider)))
                .andExpect(status().isOk());

        // Validate the Provider in the database
        List<Provider> providers = providerRepository.findAll();
        assertThat(providers).hasSize(databaseSizeBeforeUpdate);
        Provider testProvider = providers.get(providers.size() - 1);
        assertThat(testProvider.getProviderCapacity()).isEqualTo(UPDATED_PROVIDER_CAPACITY);
        assertThat(testProvider.getProviderName()).isEqualTo(UPDATED_PROVIDER_NAME);
        assertThat(testProvider.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
        assertThat(testProvider.getIsOpenOvernight()).isEqualTo(UPDATED_IS_OPEN_OVERNIGHT);
        assertThat(testProvider.getNumberOfComplains()).isEqualTo(UPDATED_NUMBER_OF_COMPLAINS);
        assertThat(testProvider.getNumberOfVisits()).isEqualTo(UPDATED_NUMBER_OF_VISITS);
        assertThat(testProvider.getLastVisit()).isEqualTo(UPDATED_LAST_VISIT);
        assertThat(testProvider.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testProvider.getIsFullDay()).isEqualTo(UPDATED_IS_FULL_DAY);
        assertThat(testProvider.getIsAfterSchool()).isEqualTo(UPDATED_IS_AFTER_SCHOOL);
        assertThat(testProvider.getIsBeforeSchool()).isEqualTo(UPDATED_IS_BEFORE_SCHOOL);
        assertThat(testProvider.getIsWeekendCare()).isEqualTo(UPDATED_IS_WEEKEND_CARE);
        assertThat(testProvider.getIsRespiteCare()).isEqualTo(UPDATED_IS_RESPITE_CARE);
        assertThat(testProvider.getIsSecondShift()).isEqualTo(UPDATED_IS_SECOND_SHIFT);
    }

    @Test
    @Transactional
    public void deleteProvider() throws Exception {
        // Initialize the database
        providerRepository.saveAndFlush(provider);

		int databaseSizeBeforeDelete = providerRepository.findAll().size();

        // Get the provider
        restProviderMockMvc.perform(delete("/api/providers/{id}", provider.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Provider> providers = providerRepository.findAll();
        assertThat(providers).hasSize(databaseSizeBeforeDelete - 1);
    }
}
