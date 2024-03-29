package com.crud.tasks.trello.client;

import com.crud.tasks.config.TrelloConfig;
import com.crud.tasks.domain.CreatedTrelloCardDto;
import com.crud.tasks.domain.TrelloBoardDto;
import com.crud.tasks.domain.TrelloCardDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;
import static java.util.Optional.ofNullable;

@Component
@RequiredArgsConstructor
public class TrelloClient {

    private final RestTemplate restTemplate;
    private static final Logger LOGGER = LoggerFactory.getLogger(TrelloClient.class);
    private final TrelloConfig trelloConfig;

    public List<TrelloBoardDto> getTrelloBoards(){

        URI url = getUrl(
                trelloConfig.getTrelloApiEndpoint(),
                trelloConfig.getTrelloAppKey(),
                trelloConfig.getTrelloToken(),
                trelloConfig.getTrelloUser());

        try{
            TrelloBoardDto[] boardsResponse = restTemplate.getForObject(
                    url,
                    TrelloBoardDto[].class
            );

            return Arrays.asList(ofNullable(boardsResponse).orElse(new TrelloBoardDto[0]));
        } catch (RestClientException e){
            LOGGER.error(e.getMessage(), e);
            return Collections.emptyList();
        }
    }

    public List<TrelloBoardDto> getFilteredTrelloBoards(){

        URI url = getUrl(
                trelloConfig.getTrelloApiEndpoint(),
                trelloConfig.getTrelloAppKey(),
                trelloConfig.getTrelloToken(),
                trelloConfig.getTrelloUser());

        try{
            TrelloBoardDto[] boardsResponse = restTemplate.getForObject(
                    url,
                    TrelloBoardDto[].class
            );

            return Optional.ofNullable(boardsResponse)
                    .map(Arrays::asList)
                    .orElse(Collections.emptyList())
                    .stream()
                    .filter(p -> Objects.nonNull(p.getId()) && Objects.nonNull(p.getName()))
                    .filter(p -> p.getName().contains("Kodilla"))
                    .collect(Collectors.toList());
        } catch (RestClientException e){
            LOGGER.error(e.getMessage(), e);
            return Collections.emptyList();
        }
    }

    private URI getUrl (String trelloApiEndpoint, String trelloAppKey, String trelloToken, String trelloUser){
        return UriComponentsBuilder.fromHttpUrl(trelloApiEndpoint + "/members/" + trelloUser + "/boards")
                .queryParam("key", trelloAppKey)
                .queryParam("token", trelloToken)
                .queryParam("fields", "name,id")
                .queryParam("lists", "all")
                .build()
                .encode()
                .toUri();
    }

    public CreatedTrelloCardDto createNewCard(TrelloCardDto trelloCardDto){
        URI url = UriComponentsBuilder.fromHttpUrl(trelloConfig.getTrelloApiEndpoint() + "/cards")
                .queryParam("key", trelloConfig.getTrelloAppKey())
                .queryParam("token", trelloConfig.getTrelloToken())
                .queryParam("name", trelloCardDto.getName())
                .queryParam("desc", trelloCardDto.getDescription())
                .queryParam("pos", trelloCardDto.getPos())
                .queryParam("idList", trelloCardDto.getListId())
                .build()
                .encode()
                .toUri();

        return restTemplate.postForObject(url, null, CreatedTrelloCardDto.class);
    }
}
