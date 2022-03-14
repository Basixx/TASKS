package com.crud.tasks.trello.validator;

import com.crud.tasks.domain.TrelloBoard;
import com.crud.tasks.domain.TrelloCard;
import com.crud.tasks.domain.TrelloList;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class TrelloValidatorTestSuite {

    @Autowired
    private TrelloValidator trelloValidator;

    @Test
    public void shouldValidateTrelloBoards() {
        //Given
        List<TrelloList> trelloLists = new ArrayList<>();
        trelloLists.add(new TrelloList("1", "name", true));
        List<TrelloBoard> trelloBoards = new ArrayList<>();
        trelloBoards.add(new TrelloBoard("1", "name", trelloLists));
        //When
        List<TrelloBoard> validateTrelloBoards = trelloValidator.validateTrelloBoards(trelloBoards);
        //Then
        assertEquals(1, validateTrelloBoards.size());
    }

    @Test
    public void shouldValidateCard(){
        //Given
        TrelloCard trelloCard = new TrelloCard("name", "description", "pos", "1");
        TrelloValidator trelloValidator = mock(TrelloValidator.class);
        //When
        trelloValidator.validateCard(trelloCard);
        //Then
        verify(trelloValidator, times(1));
    }
}
