package com.simbongsa.Backend.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Likes {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private Long boardId;

    public Likes(String username, Long boardId) {
        this.username = username;
        this.boardId = boardId;
    }
}
