package com.simbongsa.Backend.dto.response;

import com.simbongsa.Backend.entity.Approval;
import com.simbongsa.Backend.entity.Enrollment;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Getter
@NoArgsConstructor
public class EnrollResponse {

    private Long memberId;

    private String username;

    // enum 타입을 String 으로 해줌
    @Enumerated(EnumType.STRING)
    private Approval approval;

    public EnrollResponse(Enrollment enrollment) {
        this.memberId = enrollment.getMember().getMemberId();
        this.username = enrollment.getMember().getUsername();
        this.approval = this.getApproval();
    }
}
