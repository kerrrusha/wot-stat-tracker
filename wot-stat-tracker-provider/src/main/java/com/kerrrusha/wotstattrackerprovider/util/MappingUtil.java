package com.kerrrusha.wotstattrackerprovider.util;

public class MappingUtil {

    private MappingUtil() {
        throw new UnsupportedOperationException("Util class");
    }

    public static Double mapToDouble(String doubleStr) {
        return Double.valueOf(doubleStr.replace(",", "."));
    }

    public static Integer mapToInteger(String integerStr) {
        return Integer.valueOf(integerStr.replace(" ", ""));
    }

}
