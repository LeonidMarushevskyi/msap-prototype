package com.engagepoint.msap.repository.search;

import com.engagepoint.msap.domain.SupportedSpecialNeed;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the SupportedSpecialNeed entity.
 */
public interface SupportedSpecialNeedSearchRepository extends ElasticsearchRepository<SupportedSpecialNeed, Long> {
}
