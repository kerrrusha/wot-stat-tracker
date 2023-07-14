package com.kerrrusha.wotstattrackerweb.service.mapper;

public interface ResponseDtoMapper<D, T> {

    D mapToDto(T t);

}
