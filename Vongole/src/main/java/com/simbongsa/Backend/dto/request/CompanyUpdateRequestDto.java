package com.simbongsa.Backend.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@AllArgsConstructor
public class CompanyUpdateRequestDto {
    private MultipartFile companyImage;
    private String introduction;
    private String password;
    private String email;
    private String companyPhoneNum;

}
