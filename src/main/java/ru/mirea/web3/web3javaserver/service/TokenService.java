package ru.mirea.web3.web3javaserver.service;

import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.mirea.web3.web3javaserver.repository.UserRepository;

@Service
public class TokenService {
    private UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String generateNewToken() {
        boolean isGenerated = false;
        String generated = null;
        while (!isGenerated) {
            generated = RandomString.make(32);
            if (userRepository.findById(generated).orElse(null) == null) {
                isGenerated = true;
            }
        }
        return generated;
    }
}
