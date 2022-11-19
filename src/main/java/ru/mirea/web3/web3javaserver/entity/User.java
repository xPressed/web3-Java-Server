package ru.mirea.web3.web3javaserver.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @NonNull
    private String username;

    @NonNull
    private String password;
}
