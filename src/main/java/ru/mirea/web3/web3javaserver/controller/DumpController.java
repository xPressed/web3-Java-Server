package ru.mirea.web3.web3javaserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.mirea.web3.web3javaserver.repository.UserRepository;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@RestController
public class DumpController {
    @Autowired
    private UserRepository userRepository;
    @GetMapping("/dump")
    @Transactional
    public byte[] createDump() throws IOException {
        new File("C:/Jabba").mkdirs();
        userRepository.createDump();

        return Files.readAllBytes(new File("C:/Jabba/dump.csv").toPath());
    }
}
