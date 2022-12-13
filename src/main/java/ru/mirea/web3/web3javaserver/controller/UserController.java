package ru.mirea.web3.web3javaserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.mirea.web3.web3javaserver.entity.User;
import ru.mirea.web3.web3javaserver.entity.dto.UserDTO;
import ru.mirea.web3.web3javaserver.repository.UserRepository;
import ru.mirea.web3.web3javaserver.service.TokenService;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {
    private UserRepository userRepository;
    private TokenService tokenService;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setTokenService(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @GetMapping("/user")
    @Transactional
    public UserDTO getUser(@RequestParam("token") String token) {
        User user = userRepository.findById(token).orElse(null);
        if (user != null) {
            return new UserDTO(user.getToken(), user.getUsername(), user.getPassword(), user.getProfilePicture(), user.getDescription(), user.getSubs());
        }
        return null;
    }

    @GetMapping("/user/rebuild")
    public UserDTO rebuildToken(@RequestParam("token") String token) {
        User oldUser = userRepository.findById(token).orElse(null);
        if (oldUser != null) {
            oldUser.setToken(tokenService.generateNewToken());
            userRepository.deleteById(token);
            userRepository.save(oldUser);
            return new UserDTO(oldUser.getToken(), oldUser.getUsername(), oldUser.getPassword(), oldUser.getProfilePicture(), oldUser.getDescription(), oldUser.getSubs());
        }
        return null;
    }

    @GetMapping("/users")
    @Transactional
    public List<UserDTO> getUsers() {
        List<User> userList = userRepository.findAll();
        List<UserDTO> userDTOList = new ArrayList<>();
        for (User user : userList) {
            userDTOList.add(new UserDTO(user.getToken(), user.getUsername(), user.getPassword(), user.getProfilePicture(), user.getDescription(), user.getSubs()));
        }
        return userDTOList;
    }

    @PostMapping("/user")
    @Transactional
    public UserDTO postUser(@RequestBody UserDTO userDTO) {
        if (userDTO.getToken() == null) {
            User user = new User(tokenService.generateNewToken(), userDTO.getUsername(), userDTO.getPassword(), userDTO.getProfilePicture(), userDTO.getDescription(), null, null);
            userRepository.save(user);
            userDTO.setToken(user.getToken());
            return userDTO;
        }

        User user = userRepository.findById(userDTO.getToken()).orElse(null);
        if (user != null) {
            user.setUsername(userDTO.getUsername());
            user.setPassword(userDTO.getPassword());
            user.setDescription(userDTO.getDescription());
            user.setProfilePicture(userDTO.getProfilePicture());
            user.setSubs(userDTO.getSubs());
            userRepository.save(user);
        }
        return userDTO;
    }

    @DeleteMapping("/user")
    @Transactional
    public void deleteUser(@RequestParam("token") String token) {
        userRepository.deleteById(token);
    }
}
