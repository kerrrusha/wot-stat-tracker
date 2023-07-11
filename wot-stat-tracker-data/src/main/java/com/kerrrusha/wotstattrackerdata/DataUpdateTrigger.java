package com.kerrrusha.wotstattrackerdata;

import com.kerrrusha.wotstattrackerdata.entity.Player;
import com.kerrrusha.wotstattrackerdata.entity.Stat;
import com.kerrrusha.wotstattrackerdata.mapper.StatMapper;
import com.kerrrusha.wotstattrackerdata.repository.PlayerRepository;
import com.kerrrusha.wotstattrackerdata.repository.StatRepository;
import com.kerrrusha.wotstattrackerprovider.dto.wargaming.WargamingPlayerPersonalDataDto;
import com.kerrrusha.wotstattrackerprovider.dto.wotlife.WotLifePlayerStatDto;
import com.kerrrusha.wotstattrackerprovider.provider.PlayerPersonalDataProvider;
import com.kerrrusha.wotstattrackerprovider.provider.PlayerStatProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataUpdateTrigger {

    private final PlayerPersonalDataProvider playerPersonalDataProvider;
    private final PlayerStatProvider playerStatProvider;

    private final PlayerRepository playerRepository;
    private final StatRepository statRepository;

    private final StatMapper mapper;

    @Scheduled(fixedRateString = "${dataupdate.every.milliseconds}")
    public void update() {
        log.info("Data update started...");

        List<Player> players = playerRepository.findAll();
        log.info("Found {} players", players.size());

        players.forEach(this::updatePlayerData);

        log.info("Data update finished.");
    }

    private void updatePlayerData(Player player) {
        WargamingPlayerPersonalDataDto wargamingPlayerPersonalDataDto = playerPersonalDataProvider.findByAccountId(player.getAccountId());
        WotLifePlayerStatDto wotLifePlayerStatDto = playerStatProvider.findByNickname(player.getNickname());

        Stat newStat = mapper.map(wargamingPlayerPersonalDataDto, wotLifePlayerStatDto);
        statRepository.save(newStat);
    }

}
