package com.example.jobboard.util;

import com.example.jobboard.model.Job;
import com.example.jobboard.service.JobService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Component
public class JobDataLoader {

    private final JobService jobService;
    private final RestTemplate restTemplate;
    private final String apiUrl;

    public JobDataLoader(JobService jobService,
                         RestTemplate restTemplate,
                         @Value("${app.api-url}") String apiUrl) {
        this.jobService = jobService;
        this.restTemplate = restTemplate;
        this.apiUrl = apiUrl;
    }

    @Scheduled(fixedRateString = "${app.load-data-fixed-rate:3600000}")
    public void loadJobData() {
        for (int i = 1; i <= 5; i++) {
            Job[] jobs = restTemplate.getForObject(apiUrl + i, Job[].class);
            if (jobs != null) {
                jobService.saveAllJobs(Arrays.asList(jobs));
            }
        }
    }
}
