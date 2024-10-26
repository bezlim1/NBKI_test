package com.example.nbki_test.controller;

import com.example.nbki_test.entity.User;
import com.example.nbki_test.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/start")
public class UserController {
    private UserService userService;

    @GetMapping("/get/{id}")
    public Optional<User> searchUserById(@PathVariable Integer id) {
        Optional<User> searchUser = userService.findUserById(id);
        return searchUser;
    }

    @PostMapping("/add")
    public User saveUser(@RequestBody User user) {
        User addUser = userService.saveUser(user);
        return addUser;
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
