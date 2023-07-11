package com.kerrrusha.wotstattrackerdata.repository;

import com.kerrrusha.wotstattrackerdata.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerRepository extends JpaRepository<Player, Long> {
}
