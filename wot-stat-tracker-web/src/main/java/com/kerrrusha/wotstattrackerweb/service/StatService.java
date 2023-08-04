package com.kerrrusha.wotstattrackerweb.service;

import com.kerrrusha.wotstattrackerweb.dto.response.PlayerResponseDto;
import com.kerrrusha.wotstattrackerweb.dto.response.StatDeltaResponseDto;
import com.kerrrusha.wotstattrackerweb.dto.response.StatGraphsResponseDto;
import com.kerrrusha.wotstattrackerweb.entity.Stat;

import java.util.List;
import java.util.Optional;

public interface StatService {

    List<Stat> findMostRecentByNickname(String nickname);

    Stat findCurrentStatByNickname(String nickname);

    Optional<Stat> findPreviousStatByNickname(String nickname);

    Optional<StatDeltaResponseDto> getDeltas(Stat playerCurrentStatDto);

    StatGraphsResponseDto getStatGraphs(String nickname);

    void updateDataIfOutdated(PlayerResponseDto player);

    boolean dataIsUpToDate(Stat currentStat);

}
