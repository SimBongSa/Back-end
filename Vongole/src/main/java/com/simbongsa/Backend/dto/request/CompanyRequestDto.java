package com.simbongsa.Backend.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CompanyRequestDto {

    @NotBlank(message = "아이디를 입력해주세요")
    @Size(min = 4, max = 12)
    @Pattern(regexp = "[a-zA-Z\\d]*${3,12}")
    private String username;

    @NotBlank(message = "사업자번호를 입력해주세요")
    private String companyNum;

    @NotBlank(message = "비밀번호를 입력해주세요")
    @Size(min = 4, max = 32)
    @Pattern(regexp = "[a-z\\d]*${3,32}")
    private String password;

    @NotBlank
    private String passwordConfirm;

    @NotBlank(message = "이메일을 입력해주세요")
    private String email;

    private String phoneNum;

    @NotBlank(message = "회사명을 입력해주세요")
    private String companyName;









}
