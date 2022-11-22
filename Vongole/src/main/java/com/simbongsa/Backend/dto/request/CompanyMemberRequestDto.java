package com.simbongsa.Backend.dto.request;

import com.simbongsa.Backend.shared.Authority;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;


@Getter
@AllArgsConstructor
public class CompanyMemberRequestDto {


    private String username;



    private String password;

    private String passwordConfirm;

    private String email;

    private String phoneNumber;

    private String name;

    private String licenseNumber;

    private MultipartFile licenseImage;

    private Authority authority;


}
