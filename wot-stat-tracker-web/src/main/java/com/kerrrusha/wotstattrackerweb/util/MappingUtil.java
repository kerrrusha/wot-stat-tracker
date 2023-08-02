package com.kerrrusha.wotstattrackerweb.util;

import lombok.NonNull;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static java.util.Objects.isNull;

public class MappingUtil {

    public static Double toDouble(Integer integer) {
        return isNull(integer) ? Double.NaN : integer.doubleValue();
    }

    public static LocalDate toLocalDate(@NonNull LocalDateTime localDateTime) {
        return localDateTime.toLocalDate();
    }

}
