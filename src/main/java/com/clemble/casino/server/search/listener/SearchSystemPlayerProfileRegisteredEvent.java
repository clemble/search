package com.clemble.casino.server.search.listener;

import com.clemble.casino.player.PlayerProfile;
import com.clemble.casino.server.event.player.SystemPlayerProfileRegisteredEvent;
import com.clemble.casino.server.player.notification.SystemEventListener;
import com.clemble.casino.server.search.service.ServerPlayerSearchService;

import javax.validation.Valid;

/**
 * @author Anton Oparin (antono@clemble.com)
 */
public class SearchSystemPlayerProfileRegisteredEvent implements SystemEventListener<SystemPlayerProfileRegisteredEvent> {

    final private ServerPlayerSearchService playerSearchService;

    public SearchSystemPlayerProfileRegisteredEvent(
        ServerPlayerSearchService playerSearchService
    ) {
        this.playerSearchService = playerSearchService;
    }

    @Override
    public void onEvent(@Valid SystemPlayerProfileRegisteredEvent event) {
        // Step 1. Extract player profile
        PlayerProfile playerProfile = event.getPlayerProfile();
        // Step 2. Adding player profile for search
        playerSearchService.save(playerProfile);
    }

    @Override
    public String getChannel() {
        return SystemPlayerProfileRegisteredEvent.CHANNEL;
    }

    @Override
    public String getQueueName() {
        return SystemPlayerProfileRegisteredEvent.CHANNEL + " > search";
    }

}
