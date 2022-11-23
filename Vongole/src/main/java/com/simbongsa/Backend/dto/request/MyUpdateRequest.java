package com.simbongsa.Backend.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Email;
import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class MyUpdateRequest {

    private String name;

    private MultipartFile profileImage;

    @Email(message = "이메일 형식에 맞춰서 입력해주세요.")
    private String email;

    private String phoneNumber;

    private String gender;

    private String introduction;

    private LocalDate birthdate;
}
