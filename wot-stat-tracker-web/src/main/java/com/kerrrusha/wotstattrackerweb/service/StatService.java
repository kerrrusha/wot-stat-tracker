package com.kerrrusha.wotstattrackerweb.service;

import com.kerrrusha.wotstattrackerweb.entity.Stat;

import java.util.List;

public interface StatService {

    List<Stat> findByNickname(String nickname);

    Stat findCurrentStatByNickname(String nickname);

}
