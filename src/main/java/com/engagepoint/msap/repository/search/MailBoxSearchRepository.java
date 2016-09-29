package com.engagepoint.msap.repository.search;

import com.engagepoint.msap.domain.MailBox;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the MailBox entity.
 */
public interface MailBoxSearchRepository extends ElasticsearchRepository<MailBox, Long> {
}
