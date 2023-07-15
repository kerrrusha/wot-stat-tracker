package com.kerrrusha.wotstattrackerweb.repository;

import com.kerrrusha.wotstattrackerweb.entity.Stat;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@Slf4j
@SpringBootTest
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
class StatRepositoryIT {

    private static final String NICKNAME = "He_Cm0Tpu_CTaTucTuKy";
    final StatRepository statRepository;

    @Test
    void findFirstByPlayer_NicknameLikeOrderByCreatedAtDesc() {
        Stat stat = statRepository.findFirstByPlayer_NicknameLikeOrderByCreatedAtDesc(NICKNAME);
        log.info(stat.getLastBattleTime().toString());
    }

    @Test
    void findAllByPlayerNickname() {
        List<Stat> stats = statRepository.findAllByPlayerNicknameOrderByCreatedAtDesc(NICKNAME);
        log.info("Found {} stats for {} nickname", stats.size(), NICKNAME);
        stats.forEach(stat -> log.info(stat.getLastBattleTime().toString()));
    }

}