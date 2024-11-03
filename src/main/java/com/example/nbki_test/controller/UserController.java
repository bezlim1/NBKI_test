package com.example.nbki_test.controller;

import com.example.nbki_test.entity.User;
import com.example.nbki_test.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/start")
public class UserController {
    private final UserService userService;

    @GetMapping("/get/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Integer id) {
        return userService.findUserById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/add")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User savedUser = userService.saveUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }


    @DeleteMapping("/delete")
    public void deleteUser(@RequestParam Integer id) {
        userService.deleteUserById(id);
    }

    @PutMapping("/update")
    public User updateUser(@RequestParam Integer id, @RequestParam String updateName) {
        User updateUser = userService.updateUser(id, updateName);
        return updateUser;
    }
}
