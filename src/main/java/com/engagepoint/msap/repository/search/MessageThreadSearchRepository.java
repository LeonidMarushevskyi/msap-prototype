package com.engagepoint.msap.repository.search;

import com.engagepoint.msap.domain.MessageThread;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface MessageThreadSearchRepository extends ElasticsearchRepository<MessageThread, Long> {

}
