package com.kerrrusha.wotstattrackerweb.entity;

public enum Region {

    EU, NA;

    public static Region parseRegion(String value) {
        for (Region region : values()) {
            if (region.name().equalsIgnoreCase(value)) {
                return region;
            }
        }
        throw new InvalidServerNameException();
    }

    public static class InvalidServerNameException extends RuntimeException {}

}
