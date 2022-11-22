package com.simbongsa.Backend.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class MyUpdateRequest {

    private String name;

    private MultipartFile profileImage;

    private String email;

    private String phoneNumber;

    private String gender;

    private String introduction;

    private LocalDate birthdate;
}
