package ru.mirea.web3.web3javaserver.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private String token;
    private String username;
    private String password;
    private byte[] profilePicture;
    private String description;
    private List<String> subs;
}
