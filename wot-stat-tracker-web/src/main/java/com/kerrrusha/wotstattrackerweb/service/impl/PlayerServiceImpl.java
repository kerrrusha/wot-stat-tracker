package com.kerrrusha.wotstattrackerweb.service.impl;

import com.kerrrusha.wotstattrackerweb.entity.Player;
import com.kerrrusha.wotstattrackerweb.repository.PlayerRepository;
import com.kerrrusha.wotstattrackerweb.service.PlayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PlayerServiceImpl implements PlayerService {

    private final PlayerRepository playerRepository;

    @Override
    public boolean playerExists(String nickname) {
        return playerRepository.findByNickname(nickname).isPresent();
    }

    @Override
    public Player findByNickname(String nickname) {
        return playerRepository.findByNickname(nickname).orElseThrow();
    }

}
