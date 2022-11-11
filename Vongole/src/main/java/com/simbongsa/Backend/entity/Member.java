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
    @Column(name = "member_id")
    private Long memberId;

    @Column(name = "username", nullable = false)
    private String username;


    @Column(name = "nickname", nullable = false)
    private String nickname;

    @JsonIgnore
    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "member_image")
    private String memberImage;

    @Column(name = "email")
    private String email;

    @Column(name = "phone_num")
    private String phoneNum;

    @Column(name = "name")
    private String name;

    @Column(name = "gender")
    private String gender;

    @Column(name = "age")
    private int age;

    @Column(name = "introduction")
    private String introduction;

    @JsonIgnore
    @Enumerated(EnumType.STRING)
    private Authority authority;


    @Column(unique = true)
    private Long kakaoId;


    public void setKakaoId(Long kakaoId){
        this.kakaoId = kakaoId;
    }

    public void updateMember() {

    }
}
