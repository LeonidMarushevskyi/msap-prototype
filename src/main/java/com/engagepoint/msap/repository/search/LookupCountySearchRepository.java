package com.engagepoint.msap.repository.search;

import com.engagepoint.msap.domain.LookupCounty;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the LookupCounty entity.
 */
public interface LookupCountySearchRepository extends ElasticsearchRepository<LookupCounty, Long> {
}
