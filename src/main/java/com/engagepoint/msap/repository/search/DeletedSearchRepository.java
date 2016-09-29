package com.engagepoint.msap.repository.search;

import com.engagepoint.msap.domain.Deleted;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Deleted entity.
 */
public interface DeletedSearchRepository extends ElasticsearchRepository<Deleted, Long> {
}
