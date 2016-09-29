package com.engagepoint.msap.repository.search;

import com.engagepoint.msap.domain.Inbox;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Inbox entity.
 */
public interface InboxSearchRepository extends ElasticsearchRepository<Inbox, Long> {
}
