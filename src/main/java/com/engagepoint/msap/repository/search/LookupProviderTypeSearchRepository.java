package com.engagepoint.msap.repository.search;

import com.engagepoint.msap.domain.LookupProviderType;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the LookupProviderType entity.
 */
public interface LookupProviderTypeSearchRepository extends ElasticsearchRepository<LookupProviderType, Long> {
}
