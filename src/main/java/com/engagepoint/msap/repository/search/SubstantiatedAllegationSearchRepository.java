package com.engagepoint.msap.repository.search;

import com.engagepoint.msap.domain.SubstantiatedAllegation;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the SubstantiatedAllegation entity.
 */
public interface SubstantiatedAllegationSearchRepository extends ElasticsearchRepository<SubstantiatedAllegation, Long> {
}
