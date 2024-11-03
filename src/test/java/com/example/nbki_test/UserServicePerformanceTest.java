package com.example.nbki_test;

import com.example.nbki_test.entity.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@AutoConfigureMockMvc
//@SpringBootTest
//public class UserServicePerformanceTest {
//private final static String URL = "/start/get/{id}";
//
//    @Autowired
//    public MockMvc mockMvc;
//
//    private final static ObjectMapper OBJECT_MAPPER = new ObjectMapper();
//
//    @Test
//    public void testFetch1MillionUsers() throws InterruptedException, JsonProcessingException {
//
//        User user = new User();
//
//        final var userInBytes = OBJECT_MAPPER.writeValueAsBytes(user);
//
//        int totalRequests = 1_000_000;
//        int threadCount = 100;
//
//        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
//
//        List<Future<Long>> futures = new ArrayList<>();
//
//        long startTime = System.currentTimeMillis();
//
//        for (int i = 0; i < totalRequests; i++) {
//            final int userId = 1;
//            futures.add(executor.submit(() -> {
//                long start = System.currentTimeMillis();
//                mockMvc.perform(get(URL, userId)
//                                .contentType(MediaType.APPLICATION_JSON)
//                                .content(userInBytes));
//                return System.currentTimeMillis() - start;
//            }));
//        }
//
//        executor.shutdown();
//        executor.awaitTermination(1, TimeUnit.MINUTES);
//
//        List<Long> responseTimes = new ArrayList<>();
//        for (Future<Long> future : futures) {
//            try {
//                responseTimes.add(future.get());
//            } catch (ExecutionException e) {
//                e.printStackTrace();
//            }
//        }
//
//        long endTime = System.currentTimeMillis();
//        long totalDuration = endTime - startTime;
//
//        // Сбор статистики
//        double median = calculatePercentile(responseTimes, 50);
//        double p95 = calculatePercentile(responseTimes, 95);
//        double p99 = calculatePercentile(responseTimes, 99);
//
//        System.out.println("Total Time: " + totalDuration + " ms");
//        System.out.println("Median Response Time: " + median + " ms");
//        System.out.println("95th Percentile Response Time: " + p95 + " ms");
//        System.out.println("99th Percentile Response Time: " + p99 + " ms");
//    }
//
//    private double calculatePercentile(List<Long> responseTimes, double percentile) {
//        List<Long> sortedResponseTimes = responseTimes.stream()
//                .sorted()
//                .collect(Collectors.toList());
//        int index = (int) Math.ceil(percentile / 100.0 * sortedResponseTimes.size()) - 1;
//        return sortedResponseTimes.get(index);
//    }
//}
