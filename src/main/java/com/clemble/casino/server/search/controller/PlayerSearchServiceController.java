package com.clemble.casino.server.search.controller;

import com.clemble.casino.WebMapping;
import com.clemble.casino.server.search.service.ServerPlayerSearchService;
import com.clemble.casino.search.PlayerSearch;
import com.clemble.casino.search.service.PlayerSearchService;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.clemble.casino.search.SearchWebMapping.SEARCH_PLAYER;

/**
 * @author Anton Oparin (antono@clemble.com)
 */
@RestController
public class PlayerSearchServiceController implements PlayerSearchService {

    final private ServerPlayerSearchService delegate;

    public PlayerSearchServiceController(ServerPlayerSearchService delegate) {
        this.delegate = delegate;
    }

    @Override
    @RequestMapping(method = RequestMethod.GET, value = SEARCH_PLAYER, produces = WebMapping.PRODUCES)
    public List<PlayerSearch> search(@RequestParam("query") String query) {
        return delegate.search(query);
    }

}
