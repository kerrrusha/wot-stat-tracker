package com.kerrrusha.wotstattrackerweb.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kerrrusha.wotstattrackerdomain.entity.Region;
import com.kerrrusha.wotstattrackerdomain.entity.Stat;
import com.kerrrusha.wotstattrackerdomain.repository.StatRepository;
import com.kerrrusha.wotstattrackerweb.dto.request.PlayerRequestDto;
import com.kerrrusha.wotstattrackerweb.mapper.PlayerRequestMapper;
import com.kerrrusha.wotstattrackerweb.service.StatService;
import com.kerrrusha.wotstattrackerweb.mapper.StatGraphMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jms.core.JmsTemplate;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@Slf4j
@ExtendWith(MockitoExtension.class)
class StatServiceTest {

    final ObjectMapper objectMapper = new ObjectMapper();
    final StatGraphMapper statGraphMapper = new StatGraphMapper();
    final PlayerRequestMapper playerRequestMapper = new PlayerRequestMapper();

    @Mock
    JmsTemplate jmsTemplate;

    @Mock
    StatRepository statRepository;

    StatService statService;

    Stat stat1;
    Stat stat2;

    PlayerRequestDto playerRequestDto;

    @BeforeEach
    void setUp() {
        stat1 = Stat.builder().id(1L).battles(10).build();
        stat2 = Stat.builder().id(2L).battles(20).build();

        statService = new StatServiceImpl(jmsTemplate, objectMapper, playerRequestMapper, statRepository, statGraphMapper);
        playerRequestDto = PlayerRequestDto.buildPlayerRequestDto("any", Region.EU);
    }

    @Test
    void findCurrentStatByNickname() {
        when(statRepository.findFirstByPlayer_NicknameAndPlayer_RegionLikeOrderByCreatedAtDesc(anyString(), any()))
                .thenReturn(stat1);

        Stat actual = statService.findCurrentStatByPlayer(playerRequestDto);

        assertEquals(actual, stat1);
    }

    @Test
    @Disabled("Can't wire @Value private fields...")
    void findPreviousStatByNickname() {
        when(statRepository.findDistinctByPlayer_NicknameAndPlayer_RegionLikeOrderByCreatedAtDesc(anyString(), any(), any()))
                .thenReturn(List.of(stat1, stat2));

        Optional<Stat> actual = statService.findPreviousStatByNickname(playerRequestDto);

        assertEquals(actual.orElseThrow(), stat2);
    }

    @Test
    @Disabled
    void testExceptions() {
        try {
            produceNPE();
        } catch (NullPointerException e) {
            log.error("{}", e.getMessage());
        }
    }

    void produceNPE() {
        Integer empty = null;
        empty.doubleValue();
    }

}
