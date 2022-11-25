package com.simbongsa.Backend.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Hashtag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long boardId;

    @Column
    private String tag;

//    @Column
//    private Long tagCount;

    public Hashtag(Long boardId, String tag) {
        this.boardId = boardId;
        this.tag = tag;
    }
}
