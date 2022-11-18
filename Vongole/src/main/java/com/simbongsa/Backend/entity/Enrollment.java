package com.simbongsa.Backend.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;


import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Enrollment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(name = "approval")
    @Enumerated(EnumType.STRING)
    private Approval approval;


    public Enrollment(Member member, Board board) {
        this.member = member;
        this.board = board;
        this.approval = Approval.WAITING;
    }

    public void approve() {
        this.approval = Approval.PASS;
    }

    public void disapprove() {
        this.approval = Approval.FAIL;
    }


}
