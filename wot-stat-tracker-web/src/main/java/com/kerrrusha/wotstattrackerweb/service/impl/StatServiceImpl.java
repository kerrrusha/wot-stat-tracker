package com.kerrrusha.wotstattrackerweb.service.impl;

import com.kerrrusha.wotstattrackerweb.dto.response.StatDeltaResponseDto;
import com.kerrrusha.wotstattrackerweb.repository.StatRepository;
import com.kerrrusha.wotstattrackerweb.service.StatService;
import com.kerrrusha.wotstattrackerweb.entity.Stat;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StatServiceImpl implements StatService {

    private final StatRepository statRepository;

    @Override
    public List<Stat> findByNickname(String nickname) {
        List<Stat> rawStats = statRepository.findAllByPlayerNicknameOrderByCreatedAtDesc(nickname);
        return rawStats.stream()
                .distinct()
                .sorted(Comparator.comparing(Stat::getCreatedAt).reversed())
                .toList();
    }

    @Override
    public Stat findCurrentStatByNickname(String nickname) {
        return statRepository.findFirstByPlayer_NicknameLikeOrderByCreatedAtDesc(nickname);
    }

    @Override
    public Optional<Stat> findPreviousStatByNickname(String nickname) {
        List<Stat> playerStats = findByNickname(nickname);
        return playerStats.size() > 1 ? Optional.of(playerStats.get(1)) : Optional.empty();
    }

    @Override
    public StatDeltaResponseDto getDeltas(Stat current, Stat previous) {
        StatDeltaResponseDto result = new StatDeltaResponseDto();

        result.setBattlesDelta(current.getBattles() - previous.getBattles());
        result.setAvgDamageDelta(current.getAvgDamage() - previous.getAvgDamage());
        result.setRatingDelta(current.getGlobalRating() - previous.getGlobalRating());
        result.setWinrateDeltaFormatted(current.getWinrate() - previous.getWinrate());
        result.setAvgExperienceDelta(current.getAvgExperience() - previous.getAvgExperience());
        result.setWn7Delta(current.getWN7() - previous.getWN7());
        result.setWn8Delta(current.getWN8() - previous.getWN8());
        result.setTreesCutDelta(current.getTreesCut() - previous.getTreesCut());

        result.setPreviousStatCreationTime(previous.getCreatedAt());

        return result;
    }

}
