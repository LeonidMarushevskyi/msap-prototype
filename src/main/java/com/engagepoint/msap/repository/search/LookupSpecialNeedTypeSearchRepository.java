package com.engagepoint.msap.repository.search;

import com.engagepoint.msap.domain.LookupSpecialNeedType;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the LookupSpecialNeedType entity.
 */
public interface LookupSpecialNeedTypeSearchRepository extends ElasticsearchRepository<LookupSpecialNeedType, Long> {
}
