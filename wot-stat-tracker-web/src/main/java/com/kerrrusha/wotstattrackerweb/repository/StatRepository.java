package com.kerrrusha.wotstattrackerweb.repository;

import com.kerrrusha.wotstattrackerweb.entity.Region;
import com.kerrrusha.wotstattrackerweb.entity.Stat;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StatRepository extends JpaRepository<Stat, Long> {

    List<Stat> findDistinctByPlayer_NicknameAndPlayer_RegionLikeOrderByCreatedAtDesc(String nickname, Region region, Pageable pageable);

    Stat findFirstByPlayer_NicknameAndPlayer_RegionLikeOrderByCreatedAtDesc(String nickname, Region region);

}
