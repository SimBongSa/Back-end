package com.simbongsa.Backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.simbongsa.Backend.dto.request.CompanyUpdateRequest;
import com.simbongsa.Backend.dto.request.MyUpdateRequest;
import com.simbongsa.Backend.shared.Authority;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

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

    @Column(name = "birthdate")
    private LocalDate birthdate;

    @Column(name = "introduction")
    private String introduction;


    @Column(name = "license_number")
    private String licenseNumber;

    @Column(name = "license_image")
    private String licenseImage;

    @JsonIgnore
    @Enumerated(EnumType.STRING)
    private Authority authority;

    @Column(unique = true)
    private Long kakaoId;

    @Column
    private String chatRoomIdList;


    public void update(CompanyUpdateRequest companyUpdateRequest, String profileImage) {
        this.password = companyUpdateRequest.getPassword();
        this.email = companyUpdateRequest.getEmail();
        this.introduction = companyUpdateRequest.getIntroduction();
        this.phoneNumber = companyUpdateRequest.getPhoneNumber();
        this.profileImage = profileImage;
    }

    public void myupdate(MyUpdateRequest myUpdateRequest, String profileImage) {
        this.profileImage = profileImage;
        this.email = myUpdateRequest.getEmail();
        this.phoneNumber = myUpdateRequest.getPhoneNumber();
        this.name = myUpdateRequest.getName();
        this.gender = myUpdateRequest.getGender();
        this.birthdate = myUpdateRequest.getBirthdate();
        this.introduction = myUpdateRequest.getIntroduction();
    }

    public void setKakaoId(Long kakaoId){
        this.kakaoId = kakaoId;
    }
    public void setChatRoomIdList(String chatRoomIdList){
        this.chatRoomIdList = chatRoomIdList;
    }

}
