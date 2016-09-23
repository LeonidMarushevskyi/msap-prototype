package com.engagepoint.msap.repository.search;

import com.engagepoint.msap.domain.LookupState;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the LookupState entity.
 */
public interface LookupStateSearchRepository extends ElasticsearchRepository<LookupState, Long> {
}
