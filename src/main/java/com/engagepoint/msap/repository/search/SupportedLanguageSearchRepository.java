package com.engagepoint.msap.repository.search;

import com.engagepoint.msap.domain.SupportedLanguage;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the SupportedLanguage entity.
 */
public interface SupportedLanguageSearchRepository extends ElasticsearchRepository<SupportedLanguage, Long> {
}
