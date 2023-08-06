package com.kerrrusha.wotstattrackerweb.mapper;

import com.kerrrusha.wotstattrackerdomain.entity.Stat;
import com.kerrrusha.wotstattrackerweb.dto.response.StatGraphResponseDto;
import com.kerrrusha.wotstattrackerweb.dto.response.StatGraphsResponseDto;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.LinkedHashMap;

import static com.kerrrusha.wotstattrackerweb.util.MappingUtil.toDouble;
import static java.util.stream.Collectors.toMap;

@Component
public class StatGraphMapper implements DtoMapper<StatGraphsResponseDto, List<Stat>> {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM");

    @Override
    public StatGraphsResponseDto mapToDto(List<Stat> stats) {
        StatGraphsResponseDto responseDto = new StatGraphsResponseDto();

        responseDto.setWn8Graph(new StatGraphResponseDto(getWn8Map(stats)));
        responseDto.setWn7Graph(new StatGraphResponseDto(getWn7Map(stats)));
        responseDto.setWgrGraph(new StatGraphResponseDto(getWgrMap(stats)));
        responseDto.setWtrGraph(new StatGraphResponseDto(getWtrMap(stats)));
        responseDto.setWinrateGraph(new StatGraphResponseDto(getWinrateMap(stats)));

        return responseDto;
    }

    private Map<String, Double> getWn8Map(List<Stat> stats) {
        return stats.stream().collect(toMap(stat -> formatDate(stat.getCreatedAt()), Stat::getWN8, (first, second) -> first, LinkedHashMap::new));
    }

    private Map<String, Double> getWn7Map(List<Stat> stats) {
        return stats.stream().collect(toMap(stat -> formatDate(stat.getCreatedAt()), Stat::getWN7, (first, second) -> first, LinkedHashMap::new));
    }

    private Map<String, Double> getWgrMap(List<Stat> stats) {
        return stats.stream().collect(toMap(stat -> formatDate(stat.getCreatedAt()), stat -> toDouble(stat.getWgr()), (first, second) -> first, LinkedHashMap::new));
    }

    private Map<String, Double> getWtrMap(List<Stat> stats) {
        return stats.stream().collect(toMap(stat -> formatDate(stat.getCreatedAt()), stat -> toDouble(stat.getWtr()), (first, second) -> first, LinkedHashMap::new));
    }

    private Map<String, Double> getWinrateMap(List<Stat> stats) {
        return stats.stream().collect(toMap(stat -> formatDate(stat.getCreatedAt()), Stat::getWinrate, (first, second) -> first, LinkedHashMap::new));
    }

    private String formatDate(LocalDateTime dateTime) {
        return dateTime.format(formatter);
    }

}
