package com.simbongsa.Backend.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@AllArgsConstructor
public class MemberUpdateRequestDto {
    private MultipartFile profileImage;
    private String introduction;
    private String password;

}
