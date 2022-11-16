package com.simbongsa.Backend.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@AllArgsConstructor
public class CompanyUpdateRequest {

    private String nickname;

    private String name;

    private MultipartFile profileImage;

    private String email;

    private String phoneNumber;

    private String introduction;
}
