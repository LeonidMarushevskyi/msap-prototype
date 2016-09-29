package com.engagepoint.msap.repository.search;

import com.engagepoint.msap.domain.Outbox;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Outbox entity.
 */
public interface OutboxSearchRepository extends ElasticsearchRepository<Outbox, Long> {
}
