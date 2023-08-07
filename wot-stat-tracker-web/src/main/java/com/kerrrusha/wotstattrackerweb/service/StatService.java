package com.kerrrusha.wotstattrackerweb.service;

import com.kerrrusha.wotstattrackerdomain.dto.request.PlayerRequestDto;
import com.kerrrusha.wotstattrackerdomain.entity.Stat;
import com.kerrrusha.wotstattrackerweb.dto.response.StatDeltaResponseDto;
import com.kerrrusha.wotstattrackerweb.dto.response.StatGraphsResponseDto;
import com.kerrrusha.wotstattrackerweb.dto.response.StatResponseDto;

import java.util.List;
import java.util.Optional;

public interface StatService {

    List<Stat> findMostRecentByPlayer(PlayerRequestDto playerRequestDto);

    Stat findCurrentStatByPlayer(PlayerRequestDto playerRequestDto);

    Optional<Stat> findPreviousStatByPlayer(PlayerRequestDto playerRequestDto);

    Optional<StatDeltaResponseDto> getDeltas(Stat playerCurrentStatDto);

    StatGraphsResponseDto getStatGraphs(PlayerRequestDto playerRequestDto);

    void updateDataIfOutdated(PlayerRequestDto player);

    boolean dataIsUpToDate(Stat currentStat);

    List<StatResponseDto> findMostRecent();

}
