package com.simbongsa.Backend.dto.response;

import com.simbongsa.Backend.entity.Approval;
import com.simbongsa.Backend.entity.Volunteer;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Getter
@NoArgsConstructor
public class VolunteerResponse {

    private Long memberId;

    private String username;

    // enum 타입을 String 으로 해줌
    @Enumerated(EnumType.STRING)
    private Approval approval;

    public VolunteerResponse(Volunteer volunteer) {
        this.memberId = volunteer.getMember().getMemberId();
        this.username = volunteer.getMember().getUsername();
        this.approval = volunteer.getApproval();
    }
}
