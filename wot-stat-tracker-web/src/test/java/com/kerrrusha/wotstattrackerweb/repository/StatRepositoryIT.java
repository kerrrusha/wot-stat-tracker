package com.kerrrusha.wotstattrackerweb.repository;

import com.kerrrusha.wotstattrackerdomain.entity.Player;
import com.kerrrusha.wotstattrackerdomain.entity.Region;
import com.kerrrusha.wotstattrackerdomain.entity.Stat;
import com.kerrrusha.wotstattrackerdomain.repository.PlayerRepository;
import com.kerrrusha.wotstattrackerdomain.repository.StatRepository;
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

    static final String NICKNAME = "fsjdbfjsfjsdbfj";
    static final Region REGION = Region.EU;

    final StatRepository statRepository;
    final PlayerRepository playerRepository;

    Player examplePlayer;
    Stat exampleStat1;
    Stat exampleStat2;

    @BeforeEach
    void setUp() {
        examplePlayer = Player.builder().nickname(NICKNAME).region(REGION).accountId("12345").build();

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

        Stat actual = statRepository.findFirstByPlayer_NicknameAndPlayer_RegionLikeOrderByCreatedAtDesc(NICKNAME, REGION);

        assertEquals(exampleStat2, actual);
        assertEquals(exampleStat2.getCreatedAt(), actual.getCreatedAt());
    }

    @Test
    void findAllByPlayerNickname() {
        Pageable pageable = PageRequest.of(0, 15);
        List<Stat> stats = statRepository.findDistinctByPlayer_NicknameAndPlayer_RegionLikeOrderByCreatedAtDesc(
                NICKNAME, REGION, pageable);
        log.info("Found {} stats for {} nickname", stats.size(), NICKNAME);
        stats.forEach(stat -> log.info(stat.toString()));
    }

}