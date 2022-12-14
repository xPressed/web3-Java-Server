package ru.mirea.web3.web3javaserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.mirea.web3.web3javaserver.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    @Modifying
    @Query(value = "COPY (SELECT * FROM \"user\",post) TO 'C:/Jabba/dump.csv' WITH CSV DELIMITER ',' HEADER ENCODING 'WIN1251';", nativeQuery = true)
    void createDump();
}
