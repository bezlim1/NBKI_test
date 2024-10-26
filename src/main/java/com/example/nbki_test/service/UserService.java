package com.example.nbki_test.service;

import com.example.nbki_test.entity.User;
import com.example.nbki_test.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public Optional<User> findUserById(Integer id) {
        return userRepository.findById(id);
    }

    public void deleteUserById(Integer id) {
        userRepository.deleteById(id);
    }

    public User updateUser(Integer id, String updateName) {
        User user = userRepository.findById(id).get();
        user.setName(updateName);
        return userRepository.save(user);
    }
}
