package com.engagepoint.msap.repository.search;

import com.engagepoint.msap.domain.LookupLicenseType;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the LookupLicenseType entity.
 */
public interface LookupLicenseTypeSearchRepository extends ElasticsearchRepository<LookupLicenseType, Long> {
}
