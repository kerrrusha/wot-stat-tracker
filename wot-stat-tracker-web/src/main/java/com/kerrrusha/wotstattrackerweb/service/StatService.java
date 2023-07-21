package com.kerrrusha.wotstattrackerweb.service;

import com.kerrrusha.wotstattrackerweb.dto.response.StatDeltaResponseDto;
import com.kerrrusha.wotstattrackerweb.entity.Player;
import com.kerrrusha.wotstattrackerweb.entity.Stat;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface StatService {

    List<Stat> findMostRecentByNickname(String nickname);

    Stat findCurrentStatByNickname(String nickname);

    Optional<Stat> findPreviousStatByNickname(String nickname);

    Optional<StatDeltaResponseDto> getDeltas(Stat playerCurrentStat);

    void updateDataIfOutdated(Player player);

    LocalDateTime getNextDataUpdateTime(Player player);

    boolean dataIsUpToDate(Stat currentStat);

}
