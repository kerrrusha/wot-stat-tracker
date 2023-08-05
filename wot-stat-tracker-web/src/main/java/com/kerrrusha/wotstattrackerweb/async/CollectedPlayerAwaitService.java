package com.kerrrusha.wotstattrackerweb.async;

import com.kerrrusha.wotstattrackerweb.dto.request.PlayerRequestDto;
import com.kerrrusha.wotstattrackerweb.dto.response.PlayerResponseDto;
import com.kerrrusha.wotstattrackerweb.entity.Player;
import com.kerrrusha.wotstattrackerweb.mapper.PlayerResponseMapper;
import com.kerrrusha.wotstattrackerweb.repository.PlayerRepository;
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
