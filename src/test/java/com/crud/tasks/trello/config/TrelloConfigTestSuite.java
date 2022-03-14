package com.crud.tasks.trello.config;

import com.crud.tasks.config.TrelloConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class TrelloConfigTestSuite {
    @Autowired
    private TrelloConfig trelloConfig;

    @Test
    public void testGetConfigTrello(){
        //Given
        //When
        String enpoint = trelloConfig.getTrelloApiEndpoint();
        String key = trelloConfig.getTrelloAppKey();
        String token = trelloConfig.getTrelloToken();
        String user = trelloConfig.getTrelloUser();

        //Then
        assertEquals("https://api.trello.com/1", enpoint);
        assertEquals("6d00a09c35cc8173dccdef5530727dd5", key);
        assertEquals("c7100c9b36ca3f3a6ddcb3dda47b318cbeea88c7d61e512ba6854c797a73df2c", token);
        assertEquals("barbararutkowska3", user);
    }
}
