package com.clemble.casino.server.search;

import com.clemble.casino.player.PlayerProfile;
import com.clemble.casino.search.PlayerSearch;
import com.clemble.casino.server.search.service.ServerPlayerSearchService;
import com.clemble.casino.server.search.spring.SearchServiceSpringConfiguration;
import com.clemble.test.random.ObjectGenerator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import static org.junit.Assert.*;

/**
 * @author Anton Oparin (antono@clemble.com)
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SearchServiceSpringConfiguration.class)
public class PlayerSearchServiceTest {

    @Autowired
    public ServerPlayerSearchService searchService;

    @Test
    public void testPlayerFind(){
        // Step 1. Creating simple profile
        PlayerProfile profile = ObjectGenerator.
            generate(PlayerProfile.class).
            setPlayer("AntonOparin").
            setFirstName("Anton").
            setLastName("Oparin");
        searchService.save(profile);
        // Step 2. Searching by name
        List<PlayerSearch> playerSearch = searchService.search("Anton");
        assertTrue(playerSearch.size() >= 1);
        assertEquals(playerSearch.get(0).getFullName(), "Anton Oparin");
        assertEquals(playerSearch.get(0).getPlayer(), profile.getPlayer());
    }

    @Test
    public void testPartialSearch(){
        // Step 1. Creating simple profile
        PlayerProfile profile = ObjectGenerator.
            generate(PlayerProfile.class).
            setPlayer("MadMax").
            setFirstName("Maximilian").
            setLastName("Cesarius");
        searchService.save(profile);
        // Step 2. Searching by name
        List<PlayerSearch> playerSearch = searchService.search("Maximilian");
        assertTrue(playerSearch.size() >= 1);
        assertEquals(playerSearch.get(0).getFullName(), "Maximilian Cesarius");
        assertEquals(playerSearch.get(0).getPlayer(), profile.getPlayer());
        // Step 2. Searching by surname
        List<PlayerSearch> playerSearch1 = searchService.search("Cesarius");
        assertTrue(playerSearch1.size() >= 1);
        assertEquals(playerSearch1.get(0).getFullName(), "Maximilian Cesarius");
        assertEquals(playerSearch1.get(0).getPlayer(), profile.getPlayer());
        // Step 2. Searching by part of name
        List<PlayerSearch> playerSearch2 = searchService.search("Maximilia");
        assertTrue(playerSearch2.size() >= 1);
        assertEquals(playerSearch2.get(0).getFullName(), "Maximilian Cesarius");
        assertEquals(playerSearch2.get(0).getPlayer(), profile.getPlayer());
        // Step 2. Searching by part of surname
        List<PlayerSearch> playerSearch3 = searchService.search("Cesariu");
        assertTrue(playerSearch3.size() >= 1);
        assertEquals(playerSearch3.get(0).getFullName(), "Maximilian Cesarius");
        assertEquals(playerSearch3.get(0).getPlayer(), profile.getPlayer());
    }

}
