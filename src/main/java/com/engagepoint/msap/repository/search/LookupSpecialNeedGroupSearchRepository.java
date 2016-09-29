package com.engagepoint.msap.repository.search;

import com.engagepoint.msap.domain.LookupSpecialNeedGroup;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the LookupSpecialNeedGroup entity.
 */
public interface LookupSpecialNeedGroupSearchRepository extends ElasticsearchRepository<LookupSpecialNeedGroup, Long> {
}
