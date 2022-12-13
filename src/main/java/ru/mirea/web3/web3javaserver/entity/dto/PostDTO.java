package ru.mirea.web3.web3javaserver.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostDTO {
    private Integer id;
    private byte[] picture;
    private Date date;
    private String description;
    private String userToken;
}
