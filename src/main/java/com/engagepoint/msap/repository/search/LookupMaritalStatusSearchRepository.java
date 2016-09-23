package com.engagepoint.msap.repository.search;

import com.engagepoint.msap.domain.LookupMaritalStatus;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the LookupMaritalStatus entity.
 */
public interface LookupMaritalStatusSearchRepository extends ElasticsearchRepository<LookupMaritalStatus, Long> {
}
