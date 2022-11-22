package com.simbongsa.Backend.dto.response;

import com.simbongsa.Backend.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MyResponse {
    private String username;

    private String name;

    private String profileImage;

    private String email;

    private String phoneNumber;

    private String introduction;

    public MyResponse(Member member) {
        this.username = member.getUsername();
        this.name = member.getName();
        this.profileImage = member.getProfileImage();
        this.email = member.getEmail();
        this.phoneNumber = member.getPhoneNumber();
        this.introduction = member.getIntroduction();
    }
}
