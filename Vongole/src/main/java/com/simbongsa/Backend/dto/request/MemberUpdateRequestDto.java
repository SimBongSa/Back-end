package com.simbongsa.Backend.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@AllArgsConstructor
public class MemberUpdateRequestDto {
    private String nickname;                        // nickname 아래로 수정,
    private MultipartFile memberImage;
    private String introduction;
    private String password;
    private String email;
    private String phoneNumber;
}
