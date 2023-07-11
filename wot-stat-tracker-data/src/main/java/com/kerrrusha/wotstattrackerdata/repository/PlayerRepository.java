package com.kerrrusha.wotstattrackerdata.repository;

import com.kerrrusha.wotstattrackerdata.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PlayerRepository extends JpaRepository<Player, Long> {

    Optional<Player> findByAccountId(String accountId);

}
