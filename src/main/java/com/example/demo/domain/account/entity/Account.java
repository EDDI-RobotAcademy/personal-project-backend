package com.example.demo.domain.account.entity;

import com.example.demo.domain.playlist.entity.Playlist;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    @Setter
    private String password;
    @Setter
    private String nickname;

    private RoleType role;

    @OneToMany(mappedBy = "account", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JsonIgnore
    private List<Playlist> playlist;

    public Account(String email, String password, String nickname) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.role = RoleType.NORMAL;
    }
}
