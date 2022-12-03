package com.simbongsa.Backend.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.Nullable;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

@Getter
//@AllArgsConstructor
@Setter
@NoArgsConstructor
public class CompanyUpdateRequest {

    @Pattern(regexp = "^(?=.*[a-zA-Z])((?=.*\\d)(?=.*\\W)).{8,16}$", message = "영문자+숫자+특수문자를 최소 1개 이상 포함하여 8~16자로 구성됩니다.")
    private String password;

    private String passwordConfirm;

    private MultipartFile profileImage;

    @Email(message = "이메일 형식에 맞춰서 입력해주세요.")
    private String email;

    private String phoneNumber;

    private String introduction;

    public CompanyUpdateRequest(String password, String passwordConfirm, String email, String phoneNumber, String introduction) {
        this.password = password;
        this.passwordConfirm = passwordConfirm;
        this.profileImage = null;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.introduction = introduction;
    }

}
