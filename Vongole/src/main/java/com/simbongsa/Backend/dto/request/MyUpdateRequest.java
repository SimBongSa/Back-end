package com.simbongsa.Backend.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Email;
import java.time.LocalDate;

@Getter
@AllArgsConstructor
@Setter

public class MyUpdateRequest {

    private String name;

    private MultipartFile profileImage;

    @Email(message = "이메일 형식에 맞춰서 입력해주세요.")
    private String email;

    private String phoneNumber;

    private String gender;

    private String introduction;

    private LocalDate birthdate;

    public MyUpdateRequest(String name, String email, String phoneNumber, String gender, String introduction, LocalDate birthdate) {
        this.name = name;
        this.profileImage = null;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        this.introduction = introduction;
        this.birthdate = birthdate;
    }
}
