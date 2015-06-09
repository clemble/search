package com.clemble.casino.server.search.spring;

import com.clemble.casino.server.search.controller.PlayerSearchServiceController;
import com.clemble.casino.server.search.listener.SearchSystemPlayerProfileRegisteredEvent;
import com.clemble.casino.server.search.service.ElasticPlayerSearchService;
import com.clemble.casino.server.search.service.ServerPlayerSearchService;
import com.clemble.casino.server.spring.common.CommonSpringConfiguration;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author Anton Oparin (antono@clemble.com)
 */
@Configuration
@Import(value = { CommonSpringConfiguration.class })
public class SearchServiceSpringConfiguration {

    @Bean
    public PlayerSearchServiceController searchServiceController(ServerPlayerSearchService searchService){
        return new PlayerSearchServiceController(searchService);
    }

    @Bean
    public ServerPlayerSearchService serverPlayerSearchService(Client client) {
        return new ElasticPlayerSearchService(client);
    }

    @Bean
    public SearchSystemPlayerProfileRegisteredEvent searchSystemPlayerProfileRegisteredEvent(ServerPlayerSearchService searchService) {
        return new SearchSystemPlayerProfileRegisteredEvent(searchService);
    }

    @Bean
    public Client client(@Value("${clemble.elasticsearch.host}") String host) {
        return new TransportClient().
            addTransportAddress(new InetSocketTransportAddress(host, 9300));
    }

}
