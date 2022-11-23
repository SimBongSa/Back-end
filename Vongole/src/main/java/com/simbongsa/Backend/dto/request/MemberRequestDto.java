package com.simbongsa.Backend.dto.request;

import com.simbongsa.Backend.shared.Authority;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberRequestDto {

    @NotBlank(message = "아이디를 입력해주세요")
    @Pattern(regexp = "[a-zA-Z0-9].{3,16}$", message = "아이디는 4~16자로 구성됩니다.")
    private String username;

    @NotBlank(message = "비밀번호를 입력해주세요")
    @Pattern(regexp = "^(?=.*[a-zA-Z])((?=.*\\d)(?=.*\\W)).{8,16}$", message = "영문자+숫자+특수문자를 최소 1개 이상 포함하여 8~16자로 구성됩니다.")
    private String password;

    @NotBlank
    private String passwordConfirm;

    @Email(message = "이메일 형식에 맞춰서 입력해주세요.")
    @NotBlank(message = "이메일을 입력해주세요")
    private String email;

    private String phoneNumber;

    private String name;

    private String gender;

    private LocalDate birthdate;

    private Authority authority;


}
