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
public class MemberRequestDto {

    @NotBlank(message = "아이디를 입력해주세요")
    @Size(min = 4, max = 12)
    @Pattern(regexp = "[a-zA-Z\\d]*${3,12}")
    private String username;

    @NotBlank(message = "닉네입을 입력해주세요")
    @Size(min = 4, max = 12)
    @Pattern(regexp = "[a-zA-Z\\d]*${3,12}")
    private String nickname;

    @NotBlank(message = "비밀번호를 입력해주세요")
    @Size(min = 4, max = 12)
    @Pattern(regexp = "[a-z\\d]*${3,12}")
    private String password;

    @NotBlank
    private String passwordConfirm;

    @NotBlank(message = "이메일을 입력해주세요")
    private String email;

    private String phoneNum;

    private String name;

    private String gender;

    private int age;


}
