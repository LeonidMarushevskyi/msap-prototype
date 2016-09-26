package com.engagepoint.msap.repository.search;

import com.engagepoint.msap.domain.LookupQualityRating;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the LookupQualityRating entity.
 */
public interface LookupQualityRatingSearchRepository extends ElasticsearchRepository<LookupQualityRating, Long> {
}
