package com.kerrrusha.wotstattrackerprovider.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class AbstractMapper<T> {

    protected final ObjectMapper objectMapper;

    public abstract T map(String content);

}
