package com.example.demo.user.entity;

import com.example.demo.board.entity.Board;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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

    @Column(name = "uid")
    private String uid;

    @Column(name = "nickName")
    private String nickName;

    @Column(name = "name",unique = true)
    private String name;

    @Column(name = "email",unique = true)
    private String email;

    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY)
    private UserRole userRole;

    @Setter
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @JsonManagedReference
    public Board getBoard() {
        return board;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;

        userRole.setUser(this);
    }

    public User(String uid, String email,String name, String nickName ) {
        this.nickName = nickName;
        this.name = name;
        this.email = email;
        this.uid = uid;
    }
    public User(String nickName, String name) {
        this.nickName = nickName;
        this.name = name;
    }
}
