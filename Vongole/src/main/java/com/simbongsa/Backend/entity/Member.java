package com.simbongsa.Backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.simbongsa.Backend.shared.Authority;
import lombok.*;

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

    @Column(name = "profile_image")
    private String profileImage;

    @Column(name = "email")
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

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

    @Column(name = "license_number")
    private String licenseNumber;

    @Column(name = "license_image")
    private String licenseImage;

    @Column(name = "user_type")
    private int userType;


    @Column(unique = true)
    private Long kakaoId;





    public void setKakaoId(Long kakaoId){
        this.kakaoId = kakaoId;
    }

}
