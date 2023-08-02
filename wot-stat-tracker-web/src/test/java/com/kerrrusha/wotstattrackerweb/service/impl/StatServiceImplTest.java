package com.kerrrusha.wotstattrackerweb.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kerrrusha.wotstattrackerweb.entity.Stat;
import com.kerrrusha.wotstattrackerweb.repository.StatRepository;
import com.kerrrusha.wotstattrackerweb.service.StatService;
import com.kerrrusha.wotstattrackerweb.service.mapper.PlayerMapper;
import com.kerrrusha.wotstattrackerweb.service.mapper.StatGraphMapper;
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

@ExtendWith(MockitoExtension.class)
class StatServiceImplTest {

    final PlayerMapper playerMapper = new PlayerMapper();
    final ObjectMapper objectMapper = new ObjectMapper();
    final StatGraphMapper statGraphMapper = new StatGraphMapper();

    @Mock
    JmsTemplate jmsTemplate;

    @Mock
    StatRepository statRepository;

    StatService statService;

    Stat stat1;
    Stat stat2;

    @BeforeEach
    void setUp() {
        stat1 = Stat.builder().id(1L).battles(10).build();
        stat2 = Stat.builder().id(2L).battles(20).build();

        statService = new StatServiceImpl(jmsTemplate, objectMapper, statRepository, playerMapper, statGraphMapper);
    }

    @Test
    void findCurrentStatByNickname() {
        when(statRepository.findFirstByPlayer_NicknameLikeOrderByCreatedAtDesc(anyString())).thenReturn(stat1);

        Stat actual = statService.findCurrentStatByNickname("any");

        assertEquals(actual, stat1);
    }

    @Test
    @Disabled("Can't wire @Value private fields...")
    void findPreviousStatByNickname() {
        when(statRepository.findDistinctByPlayer_NicknameLikeOrderByCreatedAtDesc(anyString(), any())).thenReturn(List.of(stat1, stat2));

        Optional<Stat> actual = statService.findPreviousStatByNickname("any");

        assertEquals(actual.orElseThrow(), stat2);
    }

}
