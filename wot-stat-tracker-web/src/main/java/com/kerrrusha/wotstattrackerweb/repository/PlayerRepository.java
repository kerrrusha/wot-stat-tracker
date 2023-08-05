package com.kerrrusha.wotstattrackerweb.repository;

import com.kerrrusha.wotstattrackerweb.entity.Player;
import com.kerrrusha.wotstattrackerweb.entity.Region;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PlayerRepository extends JpaRepository<Player, Long> {

    Optional<Player> findByNicknameAndRegion(String nickname, Region region);

}
