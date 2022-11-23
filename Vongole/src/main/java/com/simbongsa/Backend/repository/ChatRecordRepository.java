package com.simbongsa.Backend.repository;

import com.simbongsa.Backend.entity.ChatRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRecordRepository extends JpaRepository<ChatRecord, Long> {
}
