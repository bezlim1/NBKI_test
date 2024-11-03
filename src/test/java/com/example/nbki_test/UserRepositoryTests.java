package com.example.nbki_test;

import com.example.nbki_test.entity.User;
import com.example.nbki_test.repository.UserRepository;
import com.example.nbki_test.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {UserService.class})
public class UserRepositoryTests {

    @MockBean
    private static UserRepository userRepository;

    @Autowired
    public UserService userService;

    @Test
    void test2() {
        User expectedUser = new User(1, "name", "surname");
        when(userRepository.save(expectedUser)).thenReturn(expectedUser);

        var actualUser = userService.saveUser(expectedUser);
        assertEquals(actualUser, expectedUser);


    }

    @Test
    void updateUserTest() {
        User expectedUser = new User(1, "name", "surname");

        when(userRepository.save(expectedUser)).thenReturn(expectedUser);
        when(userRepository.findById(expectedUser.getId())).thenReturn(Optional.of(expectedUser));
        var actualUser = userService.updateUser(1, "name");

        assertEquals(expectedUser, actualUser);

    }

    @Test
    public void testCreate100kUsers() {
        long startTime = System.currentTimeMillis();

        for (int i = 0; i < 100_000; i++) {
            User user = new User();
            user.setName("User" + i);
            //         System.out.println("Trying to save user with name: " + user.getName());
            userService.saveUser(user);
        }

        long endTime = System.currentTimeMillis();
        System.out.println("Время, затраченное на создание 100 000 пользователей: " + (endTime - startTime) + " ms");
    }


    @Test
    public void testSelect1MillionUsers() throws InterruptedException, ExecutionException {
        User user = new User(1, "name", "surname");
        when(userRepository.findById(any())).thenReturn(Optional.of(user));

        ExecutorService executor = Executors.newFixedThreadPool(100);
        List<Long> executionTimes = Collections.synchronizedList(new ArrayList<>());

        // Создаем задачи для выборки произвольных пользователей
        Callable<Long> task = () -> {
            int randomId = (int) (Math.random() * 1000000); // Предполагается, что у нас уже есть 1 млн пользователей
            long startTime = System.currentTimeMillis();
            userRepository.findById(randomId);
            long endTime = System.currentTimeMillis();

            return endTime - startTime; // Возвращаем время выполнения
        };

        // Запускаем 1 миллион задач
        Future<Long>[] futures = new Future[1000000];
        for (int i = 0; i < 1000000; i++) {
            futures[i] = executor.submit(task);
        }

        // Ждем завершения всех задач и собираем времена выполнения
        for (Future<Long> future : futures) {
            executionTimes.add(future.get());
        }

        executor.shutdown();

        // Рассчитываем статистику
        long totalExecutionTime = executionTimes.stream().mapToLong(Long::longValue).sum();
        double median = calculateMedian(executionTimes);
        double percentile95 = calculatePercentile(executionTimes, 95);
        double percentile99 = calculatePercentile(executionTimes, 99);

        System.out.println("Общее время, затраченное на отбор 1 000 000 пользователей: " + totalExecutionTime + " ms");
        System.out.println("Медиана времени выполнения: " + median + " ms");
        System.out.println("95-й процентиль времени выполнения: " + percentile95 + " ms");
        System.out.println("99-й процентиль времени выполнения: " + percentile99 + " ms");
    }

    private double calculateMedian(List<Long> times) {
        Collections.sort(times);
        int size = times.size();
        if (size % 2 == 0) {
            return (times.get(size / 2 - 1) + times.get(size / 2)) / 2.0;
        } else {
            return times.get(size / 2);
        }
    }

    private double calculatePercentile(List<Long> times, double percentile) {
        Collections.sort(times);
        int index = (int) Math.ceil(percentile / 100.0 * times.size()) - 1;
        return times.get(index);
    }
}
