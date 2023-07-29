package com.kerrrusha.wotstattrackerweb.repository;

import com.kerrrusha.wotstattrackerweb.entity.Player;
import com.kerrrusha.wotstattrackerweb.entity.Stat;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
@SpringBootTest
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
class StatRepositoryIT {

    static final String NICKNAME = "Jove";

    final StatRepository statRepository;
    final PlayerRepository playerRepository;

    Player examplePlayer;
    Stat exampleStat1;
    Stat exampleStat2;

    @BeforeEach
    void setUp() {
        examplePlayer = Player.builder().nickname(NICKNAME).accountId("12345").build();

        exampleStat1 = Stat.builder().player(examplePlayer).createdAt(LocalDateTime.now().minus(5, ChronoUnit.MINUTES)).build();
        exampleStat2 = Stat.builder().player(examplePlayer).createdAt(LocalDateTime.now()).build();
    }

    @Test
    @Rollback
    @Transactional
    void findFirstByPlayer_NicknameLikeOrderByCreatedAtDesc() {
        playerRepository.save(examplePlayer);
        statRepository.save(exampleStat1);
        statRepository.save(exampleStat2);

        Stat actual = statRepository.findFirstByPlayer_NicknameLikeOrderByCreatedAtDesc(NICKNAME);

        assertEquals(exampleStat2, actual);
        assertEquals(exampleStat2.getCreatedAt(), actual.getCreatedAt());
    }

    @Test
    void findAllByPlayerNickname() {
        Pageable pageable = PageRequest.of(0, 15);
        List<Stat> stats = statRepository.findDistinctByPlayer_NicknameLikeOrderByCreatedAtDesc(NICKNAME, pageable);
        log.info("Found {} stats for {} nickname", stats.size(), NICKNAME);
        stats.forEach(stat -> log.info(stat.toString()));
    }

}