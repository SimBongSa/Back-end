package com.simbongsa.Backend.dto.response;

import com.simbongsa.Backend.shared.Authority;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberResponseDto {
    private Long id;
    private String username;
    private Authority authority;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}
