package com.engagepoint.msap.repository.search;

import com.engagepoint.msap.domain.OpenSlot;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the OpenSlot entity.
 */
public interface OpenSlotSearchRepository extends ElasticsearchRepository<OpenSlot, Long> {
}
