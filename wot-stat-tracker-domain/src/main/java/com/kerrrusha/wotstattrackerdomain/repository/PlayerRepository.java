package com.kerrrusha.wotstattrackerdomain.repository;

import com.kerrrusha.wotstattrackerdomain.entity.Player;
import com.kerrrusha.wotstattrackerdomain.entity.Region;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PlayerRepository extends JpaRepository<Player, Long> {

    Optional<Player> findByNicknameAndRegion(String nickname, Region region);
    Optional<Player> findByAccountId(String accountId);

}
