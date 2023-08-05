package com.kerrrusha.wotstattrackerweb.async;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kerrrusha.wotstattrackerweb.dto.request.PlayerRequestDto;
import com.kerrrusha.wotstattrackerweb.dto.response.PlayerExistsInGameResponseDto;
import com.kerrrusha.wotstattrackerweb.entity.Region;
import jakarta.jms.Message;
import jakarta.jms.MessageConsumer;
import jakarta.jms.Queue;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static org.apache.commons.lang3.StringUtils.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExistInGameResultMessageAwaitService extends AbstractSingleThreadAwaitService<PlayerRequestDto, Boolean> {

    private final JmsTemplate jmsTemplate;
    private final ObjectMapper objectMapper;

    @Value("${activemq.queue.check-if-nickname-exists-results}")
    private String playersToCheckIfExistsInGameResultQueue;

    @Override
    protected Optional<Boolean> lookupForResult(PlayerRequestDto input) {
        String playerExistsDtoJson = jmsTemplate.browse(playersToCheckIfExistsInGameResultQueue, (session, browser) -> {
            MessageConsumer consumer = session.createConsumer((Queue) () -> playersToCheckIfExistsInGameResultQueue);

            log.debug("Started messages checking...");
            Message message = consumer.receive();
            String content = message.getBody(String.class);

            log.debug("Checking message: {}", content);
            if (isRequestedMessage(content, input.getNickname(), input.getRegion())) {
                message.acknowledge();
                return content;
            }

            return EMPTY;
        });
        try {
            return (isNotBlank(playerExistsDtoJson))
                    ? Optional.of(objectMapper.readValue(playerExistsDtoJson, PlayerExistsInGameResponseDto.class).isExists())
                    : Optional.empty();
        } catch (JsonProcessingException e) {
            log.error("#lookupForResult - ", e);
            return Optional.empty();
        }
    }

    private boolean isRequestedMessage(String content, String nickname, Region region) {
        return containsIgnoreCase(content, nickname) && containsIgnoreCase(content, region.name());
    }

}
