package com.kerrrusha.wotstattrackerweb.service;

import com.kerrrusha.wotstattrackerweb.dto.request.PlayerRequestDto;
import com.kerrrusha.wotstattrackerweb.dto.response.PlayerResponseDto;
import com.kerrrusha.wotstattrackerweb.dto.response.StatDeltaResponseDto;
import com.kerrrusha.wotstattrackerweb.dto.response.StatGraphsResponseDto;
import com.kerrrusha.wotstattrackerweb.entity.Stat;

import java.util.List;
import java.util.Optional;

public interface StatService {

    List<Stat> findMostRecentByNickname(PlayerRequestDto playerRequestDto);

    Stat findCurrentStatByPlayer(PlayerRequestDto playerRequestDto);

    Optional<Stat> findPreviousStatByNickname(PlayerRequestDto playerRequestDto);

    Optional<StatDeltaResponseDto> getDeltas(Stat playerCurrentStatDto);

    StatGraphsResponseDto getStatGraphs(PlayerRequestDto playerRequestDto);

    void updateDataIfOutdated(PlayerResponseDto player);

    boolean dataIsUpToDate(Stat currentStat);

}
