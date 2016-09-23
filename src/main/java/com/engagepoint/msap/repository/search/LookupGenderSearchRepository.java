package com.engagepoint.msap.repository.search;

import com.engagepoint.msap.domain.LookupGender;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the LookupGender entity.
 */
public interface LookupGenderSearchRepository extends ElasticsearchRepository<LookupGender, Long> {
}
