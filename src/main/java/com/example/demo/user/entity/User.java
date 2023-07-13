package com.example.demo.user.entity;

import com.example.demo.board.entity.Board;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


import static jakarta.persistence.CascadeType.ALL;

@Table(name = "USER")
@Entity
@Getter
@ToString
@NoArgsConstructor
public class User {
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(unique = true)
    private String name;

    private String password;

    private String nickName;

    @Column(unique = true)
    private String email;

    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY)
    private UserRole userRole;

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;

        userRole.setUser(this);
    }

    public User(String name, String password, String nickName, String email) {
        this.name = name;
        this.password = password;
        this.nickName = nickName;
        this.email = email;
    }
}
