package com.simbongsa.Backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.simbongsa.Backend.shared.Authority;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Member extends Timestamped {
    @Id @GeneratedValue
    private Long memberId;

    @Column(nullable = false)
    private String username;

//    @Column
//    private String nickname;

    @JsonIgnore
    @Column(nullable = false)
    private String password;

    @Column()
    private String memberImage;

    @Column()
    private String email;

    @Column()
    private String area;

    @Column()
    private String phone_num;

    @JsonIgnore
    @Enumerated(EnumType.STRING)
    private Authority authority;

}
