package com.kerrrusha.wotstattrackerdata.dto.mapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class AbstractMapper<D, T> {

    public abstract T map(D object);

}
