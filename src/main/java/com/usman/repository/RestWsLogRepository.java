package com.usman.repository;

import com.usman.model.RestWsLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestWsLogRepository extends JpaRepository<RestWsLog, Long> {
}
