package com.example.jobboard.controller;

import com.example.jobboard.model.Job;
import com.example.jobboard.service.JobService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/jobs")
@RequiredArgsConstructor
public class JobController {

    private final JobService jobService;

    @GetMapping
    public ResponseEntity<Page<Job>> getAllJobs(Pageable pageable) {
        return ResponseEntity.ok(jobService.getAllJobs(pageable));
    }

    @GetMapping("/top10")
    public ResponseEntity<List<Job>> getTop10Jobs() {
        return ResponseEntity.ok(jobService.getTop10Jobs());
    }

    @GetMapping("/location-stats")
    public ResponseEntity<List<Map<String, Object>>> getLocationStatistics() {
        return ResponseEntity.ok(jobService.getLocationStatistics());
    }
}
