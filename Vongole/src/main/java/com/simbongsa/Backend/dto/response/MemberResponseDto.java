package com.simbongsa.Backend.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.simbongsa.Backend.shared.Authority;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDateTime;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberResponseDto {
    private Long id;
    private String username;

    @Enumerated(EnumType.STRING)
    private Authority authority;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime modifiedAt;
}
