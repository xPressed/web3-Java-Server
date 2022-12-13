package ru.mirea.web3.web3javaserver.entity;

import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    private String token;

    private String username;
    private String password;

    @Lob
    private byte[] profilePicture;
    private String description;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "user")
    @Fetch(FetchMode.SUBSELECT)
    private List<Post> postList;

    @ElementCollection(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_token")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<String> subs;
}
