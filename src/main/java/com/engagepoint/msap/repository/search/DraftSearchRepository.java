package com.engagepoint.msap.repository.search;

import com.engagepoint.msap.domain.Draft;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Draft entity.
 */
public interface DraftSearchRepository extends ElasticsearchRepository<Draft, Long> {
}
