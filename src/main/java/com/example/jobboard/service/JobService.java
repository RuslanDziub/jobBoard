package com.example.jobboard.service;

import com.example.jobboard.model.Job;
import com.example.jobboard.repository.JobRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class JobService {

    private final JobRepository jobRepository;

    public Page<Job> getAllJobs(Pageable pageable) {
        return jobRepository.findAll(pageable);
    }

    public List<Job> getTop10Jobs() {
        return jobRepository.findAll(PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "id"))).getContent();
    }

    public List<Map<String, Object>> getLocationStatistics() {
        return jobRepository.findLocationStatistics();
    }

    public void saveAllJobs(List<Job> jobs) {
        jobRepository.saveAll(jobs);
    }
}
