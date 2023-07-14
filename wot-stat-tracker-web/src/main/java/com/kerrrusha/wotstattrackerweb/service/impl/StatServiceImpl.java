package com.kerrrusha.wotstattrackerweb.service.impl;

import com.kerrrusha.wotstattrackerweb.repository.StatRepository;
import com.kerrrusha.wotstattrackerweb.service.StatService;
import com.kerrrusha.wotstattrackerweb.entity.Stat;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StatServiceImpl implements StatService {

    private final StatRepository statRepository;

    @Override
    public List<Stat> findByNickname(String nickname) {
        return statRepository.findAllByPlayerNickname(nickname);
    }

}
