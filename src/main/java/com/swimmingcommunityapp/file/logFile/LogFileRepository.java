package com.swimmingcommunityapp.file.logFile;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogFileRepository extends JpaRepository<LogFile, Long> {
}
