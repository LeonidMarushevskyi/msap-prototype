package com.engagepoint.msap.service;

import com.codahale.metrics.annotation.Timed;
import com.engagepoint.msap.domain.*;
import com.engagepoint.msap.repository.*;
import com.engagepoint.msap.repository.search.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

@Service
@Transactional
public class ElasticsearchIndexService {

    private final Logger log = LoggerFactory.getLogger(ElasticsearchIndexService.class);

    @Inject
    private LookupCountyRepository lookupCountyRepository;

    @Inject
    private LookupCountySearchRepository lookupCountySearchRepository;

    @Inject
    private LookupGenderRepository lookupGenderRepository;

    @Inject
    private LookupGenderSearchRepository lookupGenderSearchRepository;

    @Inject
    private LookupLicenseTypeRepository lookupLicenseTypeRepository;

    @Inject
    private LookupLicenseTypeSearchRepository lookupLicenseTypeSearchRepository;

    @Inject
    private LookupMaritalStatusRepository lookupMaritalStatusRepository;

    @Inject
    private LookupMaritalStatusSearchRepository lookupMaritalStatusSearchRepository;

    @Inject
    private LookupProviderTypeRepository lookupProviderTypeRepository;

    @Inject
    private LookupProviderTypeSearchRepository lookupProviderTypeSearchRepository;

    @Inject
    private LookupQualityRatingRepository lookupQualityRatingRepository;

    @Inject
    private LookupQualityRatingSearchRepository lookupQualityRatingSearchRepository;

    @Inject
    private LookupStateRepository lookupStateRepository;

    @Inject
    private LookupStateSearchRepository lookupStateSearchRepository;

    @Inject
    private PlaceRepository placeRepository;

    @Inject
    private PlaceSearchRepository placeSearchRepository;

    @Inject
    private ProviderRepository providerRepository;

    @Inject
    private ProviderSearchRepository providerSearchRepository;

    @Inject
    private UserRepository userRepository;

    @Inject
    private UserSearchRepository userSearchRepository;

    @Inject
    private ElasticsearchTemplate elasticsearchTemplate;

    @Async
    @Timed
    public void reindexAll() {
        elasticsearchTemplate.deleteIndex(LookupCounty.class);
        if (lookupCountyRepository.count() > 0) {
            lookupCountySearchRepository.save(lookupCountyRepository.findAll());
        }
        log.info("Elasticsearch: Indexed all lookupCountys");

        elasticsearchTemplate.deleteIndex(LookupGender.class);
        if (lookupGenderRepository.count() > 0) {
            lookupGenderSearchRepository.save(lookupGenderRepository.findAll());
        }
        log.info("Elasticsearch: Indexed all lookupGenders");

        elasticsearchTemplate.deleteIndex(LookupLicenseType.class);
        if (lookupLicenseTypeRepository.count() > 0) {
            lookupLicenseTypeSearchRepository.save(lookupLicenseTypeRepository.findAll());
        }
        log.info("Elasticsearch: Indexed all lookupLicenseTypes");

        elasticsearchTemplate.deleteIndex(LookupMaritalStatus.class);
        if (lookupMaritalStatusRepository.count() > 0) {
            lookupMaritalStatusSearchRepository.save(lookupMaritalStatusRepository.findAll());
        }
        log.info("Elasticsearch: Indexed all lookupMaritalStatuss");

        elasticsearchTemplate.deleteIndex(LookupProviderType.class);
        if (lookupProviderTypeRepository.count() > 0) {
            lookupProviderTypeSearchRepository.save(lookupProviderTypeRepository.findAll());
        }
        log.info("Elasticsearch: Indexed all lookupProviderTypes");

        elasticsearchTemplate.deleteIndex(LookupQualityRating.class);
        if (lookupQualityRatingRepository.count() > 0) {
            lookupQualityRatingSearchRepository.save(lookupQualityRatingRepository.findAll());
        }
        log.info("Elasticsearch: Indexed all lookupQualityRatings");

        elasticsearchTemplate.deleteIndex(LookupState.class);
        if (lookupStateRepository.count() > 0) {
            lookupStateSearchRepository.save(lookupStateRepository.findAll());
        }
        log.info("Elasticsearch: Indexed all lookupStates");

        elasticsearchTemplate.deleteIndex(Place.class);
        if (placeRepository.count() > 0) {
            placeSearchRepository.save(placeRepository.findAll());
        }
        log.info("Elasticsearch: Indexed all places");

        elasticsearchTemplate.deleteIndex(Provider.class);
        if (providerRepository.count() > 0) {
            providerSearchRepository.save(providerRepository.findAll());
        }
        log.info("Elasticsearch: Indexed all providers");

        elasticsearchTemplate.deleteIndex(User.class);
        if (userRepository.count() > 0) {
            userSearchRepository.save(userRepository.findAll());
        }
        log.info("Elasticsearch: Indexed all users");

        log.info("Elasticsearch: Successfully performed reindexing");
    }
}
