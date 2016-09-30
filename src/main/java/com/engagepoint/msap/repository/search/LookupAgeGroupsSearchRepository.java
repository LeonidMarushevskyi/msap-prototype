package com.engagepoint.msap.repository.search;

import com.engagepoint.msap.domain.LookupAgeGroups;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the LookupAgeGroups entity.
 */
public interface LookupAgeGroupsSearchRepository extends ElasticsearchRepository<LookupAgeGroups, Long> {
}
