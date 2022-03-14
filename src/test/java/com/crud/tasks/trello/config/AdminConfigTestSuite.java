package com.crud.tasks.trello.config;

import com.crud.tasks.config.AdminConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class AdminConfigTestSuite {
    @Autowired
    private AdminConfig adminConfig;

    @Test
    public void testGetConfigAdmin(){
        //Given
        //When
        String mail = adminConfig.getAdminMail();
        //Then
        assertEquals("basienkarutkowska@gmail.com", mail);
    }
}
