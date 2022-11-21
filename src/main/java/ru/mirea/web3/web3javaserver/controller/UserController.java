package ru.mirea.web3.web3javaserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.mirea.web3.web3javaserver.entity.User;
import ru.mirea.web3.web3javaserver.repository.UserRepository;

import javax.transaction.Transactional;
import java.util.List;

@RestController
public class UserController {
    private UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping(value = "/user", params = "username")
    @Transactional
    public User getUser(@RequestParam("username") String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    @GetMapping("/user")
    @Transactional
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @PostMapping("/user")
    public User postUser(@RequestBody User user) {
        return userRepository.save(user);
    }

    @DeleteMapping("/user")
    @Transactional
    public Integer deleteUser(@RequestParam("username") String username) {
        return userRepository.deleteByUsername(username);
    }
}
