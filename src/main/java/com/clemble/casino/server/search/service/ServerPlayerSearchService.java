package com.clemble.casino.server.search.service;

import com.clemble.casino.player.PlayerProfile;
import com.clemble.casino.search.service.PlayerSearchService;

/**
 * @author Anton Oparin (antono@clemble.com)
 */
public interface ServerPlayerSearchService extends PlayerSearchService {

    void save(PlayerProfile profile);

}
