package com.kerrrusha.wotstattrackerweb.async;

import com.kerrrusha.wotstattrackerdomain.entity.Stat;
import com.kerrrusha.wotstattrackerdomain.repository.StatRepository;
import com.kerrrusha.wotstattrackerweb.dto.request.PlayerRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

import static java.util.Objects.isNull;

@Slf4j
@Service
@RequiredArgsConstructor
public class LatestStatAwaitService extends AbstractSingleThreadAwaitService<PlayerRequestDto, Stat> {

    private final StatRepository statRepository;

    @Value("${data.update.cache.expiration.minutes}")
    private Integer cacheExpirationMinutes;

    @Override
    protected Optional<Stat> lookupForResult(PlayerRequestDto input) {
        Stat stat = findCurrentStatByNickname(input);

        if (isNull(stat)) {
            return Optional.empty();
        }
        LocalDateTime acceptedFromTime = LocalDateTime.now().minusMinutes(cacheExpirationMinutes);
        LocalDateTime lastStatTime = stat.getCreatedAt();
        log.debug("Accepted from time: {}", acceptedFromTime);
        log.debug("Last stat time: {}", lastStatTime);
        return lastStatTime.isAfter(acceptedFromTime)
                ? Optional.of(stat)
                : Optional.empty();
    }

    private Stat findCurrentStatByNickname(PlayerRequestDto input) {
        return statRepository.findFirstByPlayer_NicknameAndPlayer_RegionLikeOrderByCreatedAtDesc(input.getNickname(), input.getRegion());
    }

}
