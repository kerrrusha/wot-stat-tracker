package com.kerrrusha.wotstattrackerdata.repository;

import com.kerrrusha.wotstattrackerdata.entity.Stat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatRepository extends JpaRepository<Stat, Long> {
}
