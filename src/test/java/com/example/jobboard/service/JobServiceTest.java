package com.example.jobboard.service;

import com.example.jobboard.model.Job;
import com.example.jobboard.repository.JobRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class JobServiceTest {

    @Mock
    private JobRepository jobRepository;

    @InjectMocks
    private JobService jobService;

    public JobServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllJobs() {
        Job job1 = new Job();
        job1.setTitle("Title1");
        job1.setLocation("Location1");

        Job job2 = new Job();
        job2.setTitle("Title2");
        job2.setLocation("Location2");

        List<Job> jobs = Arrays.asList(job1, job2);
        Pageable pageable = PageRequest.of(0, 10, Sort.by("title"));
        Page<Job> jobPage = new PageImpl<>(jobs, pageable, jobs.size());

        when(jobRepository.findAll(pageable)).thenReturn(jobPage);

        Page<Job> result = jobService.getAllJobs(pageable);
        assertEquals(2, result.getTotalElements());
        assertEquals("Title1", result.getContent().get(0).getTitle());
    }

    @Test
    public void testGetTop10Jobs() {
        Job job = new Job();
        job.setTitle("Title1");
        List<Job> jobs = Arrays.asList(job);

        when(jobRepository.findAll(PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "id"))))
                .thenReturn(new PageImpl<>(jobs));

        List<Job> result = jobService.getTop10Jobs();
        assertEquals(1, result.size());
        assertEquals("Title1", result.get(0).getTitle());
    }

    @Test
    public void testGetLocationStatistics() {
        List<Map<String, Object>> mockStatistics = new ArrayList<>();
        Map<String, Object> locationStat1 = new HashMap<>();
        locationStat1.put("location", "Kyiv");
        locationStat1.put("count", 10);
        mockStatistics.add(locationStat1);

        Map<String, Object> locationStat2 = new HashMap<>();
        locationStat2.put("location", "Lviv");
        locationStat2.put("count", 5);
        mockStatistics.add(locationStat2);

        when(jobRepository.findLocationStatistics()).thenReturn(mockStatistics);

        List<Map<String, Object>> result = jobService.getLocationStatistics();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Kyiv", result.get(0).get("location"));
        assertEquals(10, result.get(0).get("count"));
        assertEquals("Lviv", result.get(1).get("location"));
        assertEquals(5, result.get(1).get("count"));

        verify(jobRepository, times(1)).findLocationStatistics();
    }
}
