package com.kerrrusha.wotstattrackerweb.async;

import com.kerrrusha.wotstattrackerdomain.dto.request.PlayerRequestDto;
import com.kerrrusha.wotstattrackerdomain.dto.response.PlayerResponseDto;
import com.kerrrusha.wotstattrackerdomain.entity.Player;
import com.kerrrusha.wotstattrackerdomain.repository.PlayerRepository;
import com.kerrrusha.wotstattrackerweb.mapper.PlayerResponseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CollectedPlayerAwaitService extends AbstractSingleThreadAwaitService<PlayerRequestDto, PlayerResponseDto> {

    private final PlayerRepository playerRepository;
    private final PlayerResponseMapper playerResponseMapper;

    @Override
    protected Optional<PlayerResponseDto> lookupForResult(PlayerRequestDto input) {
        return playerExistsInDb(input)
                ? findPlayer(input)
                : Optional.empty();
    }

    private boolean playerExistsInDb(PlayerRequestDto input) {
        return findPlayer(input).isPresent();
    }

    private Optional<PlayerResponseDto> findPlayer(PlayerRequestDto input) {
        Optional<Player> playerOptional = playerRepository.findByNicknameAndRegion(input.getNickname(), input.getRegion());
        return playerOptional.map(playerResponseMapper::mapToDto);
    }

}
