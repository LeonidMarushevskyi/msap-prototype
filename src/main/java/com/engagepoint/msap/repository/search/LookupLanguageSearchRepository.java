package com.engagepoint.msap.repository.search;

import com.engagepoint.msap.domain.LookupLanguage;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the LookupLanguage entity.
 */
public interface LookupLanguageSearchRepository extends ElasticsearchRepository<LookupLanguage, Long> {
}
