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
    private AttachmentRepository attachmentRepository;

    @Inject
    private AttachmentSearchRepository attachmentSearchRepository;

    @Inject
    private DeletedRepository deletedRepository;

    @Inject
    private DeletedSearchRepository deletedSearchRepository;

    @Inject
    private DraftRepository draftRepository;

    @Inject
    private DraftSearchRepository draftSearchRepository;

    @Inject
    private InboxRepository inboxRepository;

    @Inject
    private InboxSearchRepository inboxSearchRepository;

    @Inject
    private LookupAgeGroupsRepository lookupAgeGroupsRepository;

    @Inject
    private LookupAgeGroupsSearchRepository lookupAgeGroupsSearchRepository;

    @Inject
    private LookupCountyRepository lookupCountyRepository;

    @Inject
    private LookupCountySearchRepository lookupCountySearchRepository;

    @Inject
    private LookupDayOfWeekRepository lookupDayOfWeekRepository;

    @Inject
    private LookupDayOfWeekSearchRepository lookupDayOfWeekSearchRepository;

    @Inject
    private LookupGenderRepository lookupGenderRepository;

    @Inject
    private LookupGenderSearchRepository lookupGenderSearchRepository;

    @Inject
    private LookupLanguageRepository lookupLanguageRepository;

    @Inject
    private LookupLanguageSearchRepository lookupLanguageSearchRepository;

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
    private LookupSpecialNeedGroupRepository lookupSpecialNeedGroupRepository;

    @Inject
    private LookupSpecialNeedGroupSearchRepository lookupSpecialNeedGroupSearchRepository;

    @Inject
    private LookupSpecialNeedTypeRepository lookupSpecialNeedTypeRepository;

    @Inject
    private LookupSpecialNeedTypeSearchRepository lookupSpecialNeedTypeSearchRepository;

    @Inject
    private LookupStateRepository lookupStateRepository;

    @Inject
    private LookupStateSearchRepository lookupStateSearchRepository;

    @Inject
    private MailBoxRepository mailBoxRepository;

    @Inject
    private MailBoxSearchRepository mailBoxSearchRepository;

    @Inject
    private MessageRepository messageRepository;

    @Inject
    private MessageSearchRepository messageSearchRepository;

    @Inject
    private OpenSlotRepository openSlotRepository;

    @Inject
    private OpenSlotSearchRepository openSlotSearchRepository;

    @Inject
    private OutboxRepository outboxRepository;

    @Inject
    private OutboxSearchRepository outboxSearchRepository;

    @Inject
    private PlaceRepository placeRepository;

    @Inject
    private PlaceSearchRepository placeSearchRepository;

    @Inject
    private PriceRepository priceRepository;

    @Inject
    private PriceSearchRepository priceSearchRepository;

    @Inject
    private ProviderRepository providerRepository;

    @Inject
    private ProviderSearchRepository providerSearchRepository;

    @Inject
    private ReviewRepository reviewRepository;

    @Inject
    private ReviewSearchRepository reviewSearchRepository;

    @Inject
    private ScheduleRepository scheduleRepository;

    @Inject
    private ScheduleSearchRepository scheduleSearchRepository;

    @Inject
    private SubstantiatedAllegationRepository substantiatedAllegationRepository;

    @Inject
    private SubstantiatedAllegationSearchRepository substantiatedAllegationSearchRepository;

    @Inject
    private SupportedLanguageRepository supportedLanguageRepository;

    @Inject
    private SupportedLanguageSearchRepository supportedLanguageSearchRepository;

    @Inject
    private SupportedSpecialNeedRepository supportedSpecialNeedRepository;

    @Inject
    private SupportedSpecialNeedSearchRepository supportedSpecialNeedSearchRepository;

    @Inject
    private UserRepository userRepository;

    @Inject
    private UserSearchRepository userSearchRepository;

    @Inject
    private ElasticsearchTemplate elasticsearchTemplate;

    @Async
    @Timed
    public void reindexAll() {
        elasticsearchTemplate.deleteIndex(Attachment.class);
        if (attachmentRepository.count() > 0) {
            attachmentSearchRepository.save(attachmentRepository.findAll());
        }
        log.info("Elasticsearch: Indexed all attachments");

        elasticsearchTemplate.deleteIndex(Deleted.class);
        if (deletedRepository.count() > 0) {
            deletedSearchRepository.save(deletedRepository.findAll());
        }
        log.info("Elasticsearch: Indexed all deleteds");

        elasticsearchTemplate.deleteIndex(Draft.class);
        if (draftRepository.count() > 0) {
            draftSearchRepository.save(draftRepository.findAll());
        }
        log.info("Elasticsearch: Indexed all drafts");

        elasticsearchTemplate.deleteIndex(Inbox.class);
        if (inboxRepository.count() > 0) {
            inboxSearchRepository.save(inboxRepository.findAll());
        }
        log.info("Elasticsearch: Indexed all inboxs");

        elasticsearchTemplate.deleteIndex(LookupAgeGroups.class);
        if (lookupAgeGroupsRepository.count() > 0) {
            lookupAgeGroupsSearchRepository.save(lookupAgeGroupsRepository.findAll());
        }
        log.info("Elasticsearch: Indexed all lookupAgeGroupss");

        elasticsearchTemplate.deleteIndex(LookupCounty.class);
        if (lookupCountyRepository.count() > 0) {
            lookupCountySearchRepository.save(lookupCountyRepository.findAll());
        }
        log.info("Elasticsearch: Indexed all lookupCountys");

        elasticsearchTemplate.deleteIndex(LookupDayOfWeek.class);
        if (lookupDayOfWeekRepository.count() > 0) {
            lookupDayOfWeekSearchRepository.save(lookupDayOfWeekRepository.findAll());
        }
        log.info("Elasticsearch: Indexed all lookupDayOfWeeks");

        elasticsearchTemplate.deleteIndex(LookupGender.class);
        if (lookupGenderRepository.count() > 0) {
            lookupGenderSearchRepository.save(lookupGenderRepository.findAll());
        }
        log.info("Elasticsearch: Indexed all lookupGenders");

        elasticsearchTemplate.deleteIndex(LookupLanguage.class);
        if (lookupLanguageRepository.count() > 0) {
            lookupLanguageSearchRepository.save(lookupLanguageRepository.findAll());
        }
        log.info("Elasticsearch: Indexed all lookupLanguages");

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

        elasticsearchTemplate.deleteIndex(LookupSpecialNeedGroup.class);
        if (lookupSpecialNeedGroupRepository.count() > 0) {
            lookupSpecialNeedGroupSearchRepository.save(lookupSpecialNeedGroupRepository.findAll());
        }
        log.info("Elasticsearch: Indexed all lookupSpecialNeedGroups");

        elasticsearchTemplate.deleteIndex(LookupSpecialNeedType.class);
        if (lookupSpecialNeedTypeRepository.count() > 0) {
            lookupSpecialNeedTypeSearchRepository.save(lookupSpecialNeedTypeRepository.findAll());
        }
        log.info("Elasticsearch: Indexed all lookupSpecialNeedTypes");

        elasticsearchTemplate.deleteIndex(LookupState.class);
        if (lookupStateRepository.count() > 0) {
            lookupStateSearchRepository.save(lookupStateRepository.findAll());
        }
        log.info("Elasticsearch: Indexed all lookupStates");

        elasticsearchTemplate.deleteIndex(MailBox.class);
        if (mailBoxRepository.count() > 0) {
            mailBoxSearchRepository.save(mailBoxRepository.findAll());
        }
        log.info("Elasticsearch: Indexed all mailBoxs");

        elasticsearchTemplate.deleteIndex(Message.class);
        if (messageRepository.count() > 0) {
            messageSearchRepository.save(messageRepository.findAll());
        }
        log.info("Elasticsearch: Indexed all messages");

        elasticsearchTemplate.deleteIndex(OpenSlot.class);
        if (openSlotRepository.count() > 0) {
            openSlotSearchRepository.save(openSlotRepository.findAll());
        }
        log.info("Elasticsearch: Indexed all openSlots");

        elasticsearchTemplate.deleteIndex(Outbox.class);
        if (outboxRepository.count() > 0) {
            outboxSearchRepository.save(outboxRepository.findAll());
        }
        log.info("Elasticsearch: Indexed all outboxs");

        elasticsearchTemplate.deleteIndex(Place.class);
        if (placeRepository.count() > 0) {
            placeSearchRepository.save(placeRepository.findAll());
        }
        log.info("Elasticsearch: Indexed all places");

        elasticsearchTemplate.deleteIndex(Price.class);
        if (priceRepository.count() > 0) {
            priceSearchRepository.save(priceRepository.findAll());
        }
        log.info("Elasticsearch: Indexed all prices");

        elasticsearchTemplate.deleteIndex(Provider.class);
        if (providerRepository.count() > 0) {
            providerSearchRepository.save(providerRepository.findAll());
        }
        log.info("Elasticsearch: Indexed all providers");

        elasticsearchTemplate.deleteIndex(Review.class);
        if (reviewRepository.count() > 0) {
            reviewSearchRepository.save(reviewRepository.findAll());
        }
        log.info("Elasticsearch: Indexed all reviews");

        elasticsearchTemplate.deleteIndex(Schedule.class);
        if (scheduleRepository.count() > 0) {
            scheduleSearchRepository.save(scheduleRepository.findAll());
        }
        log.info("Elasticsearch: Indexed all schedules");

        elasticsearchTemplate.deleteIndex(SubstantiatedAllegation.class);
        if (substantiatedAllegationRepository.count() > 0) {
            substantiatedAllegationSearchRepository.save(substantiatedAllegationRepository.findAll());
        }
        log.info("Elasticsearch: Indexed all substantiatedAllegations");

        elasticsearchTemplate.deleteIndex(SupportedLanguage.class);
        if (supportedLanguageRepository.count() > 0) {
            supportedLanguageSearchRepository.save(supportedLanguageRepository.findAll());
        }
        log.info("Elasticsearch: Indexed all supportedLanguages");

        elasticsearchTemplate.deleteIndex(SupportedSpecialNeed.class);
        if (supportedSpecialNeedRepository.count() > 0) {
            supportedSpecialNeedSearchRepository.save(supportedSpecialNeedRepository.findAll());
        }
        log.info("Elasticsearch: Indexed all supportedSpecialNeeds");

        elasticsearchTemplate.deleteIndex(User.class);
        if (userRepository.count() > 0) {
            userSearchRepository.save(userRepository.findAll());
        }
        log.info("Elasticsearch: Indexed all users");

        log.info("Elasticsearch: Successfully performed reindexing");
    }
}
