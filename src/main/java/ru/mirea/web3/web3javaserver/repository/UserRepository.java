package ru.mirea.web3.web3javaserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mirea.web3.web3javaserver.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
}
