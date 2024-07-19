package com.example.jobboard.repository;

import com.example.jobboard.model.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {

    @Query("SELECT j.location AS location, COUNT(j) AS count FROM Job j GROUP BY j.location")
    List<Map<String, Object>> findLocationStatistics();
}
