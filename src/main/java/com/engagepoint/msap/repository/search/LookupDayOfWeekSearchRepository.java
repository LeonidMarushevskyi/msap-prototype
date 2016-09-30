package com.engagepoint.msap.repository.search;

import com.engagepoint.msap.domain.LookupDayOfWeek;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the LookupDayOfWeek entity.
 */
public interface LookupDayOfWeekSearchRepository extends ElasticsearchRepository<LookupDayOfWeek, Long> {
}
