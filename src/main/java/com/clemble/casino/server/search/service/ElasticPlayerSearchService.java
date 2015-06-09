package com.clemble.casino.server.search.service;

import com.clemble.casino.player.PlayerAware;
import com.clemble.casino.player.PlayerProfile;
import com.clemble.casino.search.PlayerSearch;
import com.google.common.collect.ImmutableMap;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Anton Oparin (antono@clemble.com)
 */
public class ElasticPlayerSearchService implements ServerPlayerSearchService {

    final private Client client;

    public ElasticPlayerSearchService(Client client) {
        this.client = client;
    }

    @Override
    public List<PlayerSearch> search(String fullName) {
        SearchResponse response = client.prepareSearch("clemble", "player").
            setSearchType(SearchType.DFS_QUERY_THEN_FETCH).
            setQuery(QueryBuilders.fuzzyQuery("fullName", fullName)).             // Query
            setFrom(0).
            setSize(10).
            execute().
            actionGet();

        List<PlayerSearch> playerSearches = new ArrayList<>();
        for(SearchHit hit: response.getHits().getHits()) {
            Map<String, Object> source = hit.sourceAsMap();
            PlayerSearch search = new PlayerSearch(source.get("player").toString(), source.get("fullName").toString());
            playerSearches.add(search);
        }

        return playerSearches;
    }

    public void save(PlayerProfile playerProfile) {
        // Step 1. Generating player presentation
        Map<String, Object> player = ImmutableMap.of(
                PlayerAware.PLAYER, playerProfile.getPlayer(),
                "fullName", playerProfile.getFirstName() + " " + playerProfile.getLastName()
        );
        // Step 2. Generating player presentation
        client.
            prepareIndex("clemble", "player", playerProfile.getPlayer()).
            setSource(player).
            execute().
            actionGet();
    }

}
