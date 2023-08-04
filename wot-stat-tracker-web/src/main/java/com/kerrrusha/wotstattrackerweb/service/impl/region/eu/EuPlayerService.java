package com.kerrrusha.wotstattrackerweb.service.impl.region.eu;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kerrrusha.wotstattrackerweb.dto.response.PlayerResponseDto;
import com.kerrrusha.wotstattrackerweb.dto.response.PlayerExistsInGameDto;
import com.kerrrusha.wotstattrackerweb.entity.Player;
import com.kerrrusha.wotstattrackerweb.repository.PlayerRepository;
import com.kerrrusha.wotstattrackerweb.service.PlayerService;
import com.kerrrusha.wotstattrackerweb.service.mapper.PlayerMapper;
import jakarta.jms.*;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.apache.commons.lang3.StringUtils.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class EuPlayerService implements PlayerService {

    private static final String PLAYER_DOES_NOT_EXISTS = "Such player does not exists on WoT EU server. Please, check your input.";

    private final JmsTemplate jmsTemplate;
    private final PlayerRepository playerRepository;
    private final PlayerMapper playerMapper;
    private final ObjectMapper objectMapper;

    @Value("${data.update.post.timeout.seconds}")
    private Integer dataUpdateTimeoutSeconds;

    @Value("${activemq.queue.players-to-collect-player-info}")
    private String playersToCollectInfoQueue;

    @Value("${activemq.queue.nicknames-to-check-if-exists}")
    private String playersToCheckIfExistsInGameQueue;

    @Value("${activemq.queue.check-if-nickname-exists-results}")
    private String playersToCheckIfExistsInGameResultQueue;

    @Value("${data.update.post.check.millis}")
    private Integer checkUpdatedDataEveryMillis;

    @Override
    public PlayerResponseDto getPlayer(String nickname) {
        if (playerExistsInDb(nickname)) {
            return findByNickname(nickname);
        }
        sendMessage(playersToCollectInfoQueue, nickname);
        return awaitCollectedPlayer(nickname);
    }

    private PlayerResponseDto findByNickname(String nickname) {
        Player player = playerRepository.findByNickname(nickname).orElseThrow(() -> new IllegalArgumentException(PLAYER_DOES_NOT_EXISTS));
        return playerMapper.mapToDto(player);
    }

    @Override
    public boolean playerExistsInDb(String nickname) {
        return playerRepository.findByNickname(nickname).isPresent();
    }

    @Override
    public boolean playerExistsInGame(String nickname) {
        sendMessage(playersToCheckIfExistsInGameQueue, nickname);
        return waitForExistInGameResultMessage(nickname);
    }

    private boolean waitForExistInGameResultMessage(String nickname) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        CompletableFuture<PlayerExistsInGameDto> future = new CompletableFuture<>();

        executor.submit(() -> {
            while (!future.isDone()) {
                String playerExistsDtoJson = jmsTemplate.browse(playersToCheckIfExistsInGameResultQueue, (session, browser) -> {
                    MessageConsumer consumer = session.createConsumer((Queue) () -> playersToCheckIfExistsInGameResultQueue);

                    log.debug("Started messages checking...");
                    Message message = consumer.receive();
                    String content = message.getBody(String.class);

                    log.debug("Checking message: {}", content);
                    if (containsIgnoreCase(content, nickname)) {
                        message.acknowledge();
                        acknowledgeMessage(message.getJMSMessageID());
                        return content;
                    }

                    return EMPTY;
                });
                if (isNotBlank(playerExistsDtoJson)) {
                    try {
                        future.complete(objectMapper.readValue(playerExistsDtoJson, PlayerExistsInGameDto.class));
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                }

                try {
                    TimeUnit.MILLISECONDS.sleep(checkUpdatedDataEveryMillis);
                } catch (InterruptedException ignored) {}
            }
        });

        boolean result = false;
        try {
            result = future.get(dataUpdateTimeoutSeconds, TimeUnit.SECONDS).isExists();
        } catch (Exception e) {
            log.error("#waitForResultMessage - ", e);
        } finally {
            executor.shutdownNow();
        }

        log.debug("#waitForResultMessage - awaited such player existing: {}", result);
        return result;
    }

    private void acknowledgeMessage(String messageId) {
        jmsTemplate.execute(session -> {
            try {
                Message message = session.createTextMessage();
                message.setJMSMessageID(messageId);
                message.acknowledge();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        });
    }

    @SneakyThrows
    private void sendMessage(String queue, String message) {
        log.info("Sending to {} message: {}", queue, message);
        jmsTemplate.convertAndSend(queue, message);
    }

    private PlayerResponseDto awaitCollectedPlayer(String nickname) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        CompletableFuture<PlayerResponseDto> future = new CompletableFuture<>();

        executor.submit(() -> {
            while (!future.isDone()) {
                if (playerExistsInDb(nickname)) {
                    future.complete(findByNickname(nickname));
                    break;
                }

                try {
                    TimeUnit.MILLISECONDS.sleep(checkUpdatedDataEveryMillis);
                } catch (InterruptedException ignored) {}
            }
        });

        PlayerResponseDto result = null;
        try {
            result = future.get(dataUpdateTimeoutSeconds, TimeUnit.SECONDS);
        } catch (Exception e) {
            log.error("#awaitCollectedPlayer - ", e);
        } finally {
            executor.shutdownNow();
        }

        log.debug("#awaitCollectedPlayer - awaited such player: {}", result);
        return result;
    }

}
