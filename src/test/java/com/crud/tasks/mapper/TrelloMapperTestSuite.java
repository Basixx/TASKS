package com.crud.tasks.mapper;

import com.crud.tasks.domain.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class TrelloMapperTestSuite {
    @Autowired
    private TrelloMapper trelloMapper;

    @Test
    public void testMapToBoard(){
        //Given
        TrelloBoardDto trelloBoardDto = new TrelloBoardDto("1", "BoardDto1", new ArrayList<>());
        List<TrelloBoardDto> trelloBoardsDto = new ArrayList<>();
        trelloBoardsDto.add(trelloBoardDto);

        //When
        List<TrelloBoard> trelloBoards = trelloMapper.mapToBoards(trelloBoardsDto);

        //Then
        assertEquals(1, trelloBoards.size());
        assertEquals("1", trelloBoards.get(0).getId());
        assertEquals("BoardDto1", trelloBoards.get(0).getName());
        assertEquals(0, trelloBoards.get(0).getLists().size());
    }

    @Test
    public void testMapToBoardDto(){
        //Given
        TrelloBoard trelloBoard = new TrelloBoard("2", "Board2", new ArrayList<>());
        List<TrelloBoard> trelloBoards = new ArrayList<>();
        trelloBoards.add(trelloBoard);

        //When
        List<TrelloBoardDto> trelloBoardsDto = trelloMapper.mapToBoardsDto(trelloBoards);

        //Then
        assertEquals(1, trelloBoardsDto.size());
        assertEquals("2", trelloBoardsDto.get(0).getId());
        assertEquals("Board2", trelloBoardsDto.get(0).getName());
        assertEquals(0, trelloBoards.get(0).getLists().size());
    }

    @Test
    public void testMapToList(){
        //Given
        TrelloListDto trelloListDto = new TrelloListDto("3", "TrelloListDto1", true);
        List<TrelloListDto> listDto = new ArrayList<>();
        listDto.add(trelloListDto);

        //When
        List<TrelloList> list = trelloMapper.mapToList(listDto);

        //Then
        assertEquals(1, list.size());
        assertEquals("3", list.get(0).getId());
        assertEquals("TrelloListDto1", list.get(0).getName());
        assertTrue(list.get(0).isClosed());
    }

    @Test
    public void testMapToListDto(){
        //Given
        TrelloList trelloList = new TrelloList("4", "TrelloList1", true);
        List<TrelloList> list = new ArrayList<>();
        list.add(trelloList);

        //When
        List<TrelloListDto> listDto = trelloMapper.mapToListDto(list);

        //Then
        assertEquals(1, listDto.size());
        assertEquals("4", listDto.get(0).getId());
        assertEquals("TrelloList1", listDto.get(0).getName());
        assertTrue(listDto.get(0).isClosed());
    }

    @Test
    public void testMapToCardDto(){
        //Given
        TrelloCard trelloCard = new TrelloCard("TrelloCard", "TrelloCardDescription", "pos", "5");

        //When
        TrelloCardDto trelloCardDto = trelloMapper.mapToCardDto(trelloCard);

        //Then
        assertEquals("TrelloCard", trelloCardDto.getName());
        assertEquals("TrelloCardDescription", trelloCardDto.getDescription());
        assertEquals("pos", trelloCardDto.getPos());
        assertEquals("5", trelloCardDto.getListId());
    }

    @Test
    public void testMapToCard(){
        //Given
        TrelloCardDto trelloCardDto = new TrelloCardDto("TrelloCardDto", "TrelloCardDtoDescription", "posDto", "6");

        //When
        TrelloCard trelloCard = trelloMapper.mapToCard(trelloCardDto);

        //Then
        assertEquals("TrelloCardDto", trelloCard.getName());
        assertEquals("TrelloCardDtoDescription", trelloCard.getDescription());
        assertEquals("posDto", trelloCard.getPos());
        assertEquals("6", trelloCard.getListId());
    }
}
