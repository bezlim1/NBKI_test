package com.example.nbki_test;

import com.example.nbki_test.entity.User;
import com.example.nbki_test.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.util.StopWatch;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserServiceIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testCreate100kUsers() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        int batchSize = 1000; // Используем пакеты для обработки

        for (int i = 0; i < 100000; i += batchSize) {
            User user = new User();
            user.setName("User " + i);
            restTemplate.postForEntity("/start", user, User.class);
        }

        stopWatch.stop();
        System.out.println("Время, затраченное на создание 100 тысяч пользователей: " +
                stopWatch.getTotalTimeMillis() + " ms");
    }


    @Test
    public void testSelect1MUsersWithStats() throws InterruptedException, ExecutionException {
        int numConnections = 100;
        int totalRecords = 1_000_000;
        ExecutorService executorService = Executors.newFixedThreadPool(numConnections);
        List<Long> times = Collections.synchronizedList(new ArrayList<>());

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        // Подготовка списка ID заранее
        List<Integer> ids = IntStream.range(0, totalRecords).boxed().collect(Collectors.toList());
        Collections.shuffle(ids); // Перемешиваем для случайного доступа

        List<Future<Void>> futures = new ArrayList<>();

        for (int i = 0; i < numConnections; i++) {
            futures.add(executorService.submit(() -> {
                for (int j = 0; j < totalRecords / numConnections; j++) {
                    int randomId = ids.get(j); // Используем подготовленный список ID
                    long startTime = System.currentTimeMillis();
                    ResponseEntity<User> response = restTemplate.getForEntity("/start/" +
                            randomId, User.class);
                    long endTime = System.currentTimeMillis();

                    if (response.getStatusCode() == HttpStatus.OK) {
                        times.add(endTime - startTime);
                    }
                }
                return null;
            }));
        }

        // Ждем завершения всех задач
        for (Future<Void> future : futures) {
            future.get();
        }

        stopWatch.stop();

        long totalTime = stopWatch.getTotalTimeMillis();
        System.out.println("Общее время, затраченное на отбор 1 млн пользователей: " + totalTime + " ms");

        double averageTime = times.stream().mapToLong(Long::longValue).average().orElse(0.0);
        System.out.println("Среднее время выполнения одного запроса: " + averageTime + " ms");


        Collections.sort(times);

        if (times.isEmpty()) {
            System.out.println("Время не записано.");
        } else {
            double medianTime;
            int size = times.size();
            if (size % 2 == 0) {
                medianTime = (times.get(size / 2 - 1) + times.get(size / 2)) / 2.0;
            } else {
                medianTime = times.get(size / 2);
            }
            System.out.println("Медианное время выполнения одного запроса: " + medianTime + " ms");
        }
        executorService.shutdown();
    }
}

