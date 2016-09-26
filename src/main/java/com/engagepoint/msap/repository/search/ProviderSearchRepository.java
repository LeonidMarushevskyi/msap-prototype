package com.engagepoint.msap.repository.search;

import com.engagepoint.msap.domain.Provider;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Provider entity.
 */
public interface ProviderSearchRepository extends ElasticsearchRepository<Provider, Long> {
}
