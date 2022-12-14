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

    private Long enrollId;

    private String username;

    @Enumerated(EnumType.STRING)
    private Approval approval;

    public EnrollResponse(Enrollment enrollment) {
        this.enrollId = enrollment.getId();
        this.username = enrollment.getMember().getUsername();
        this.approval = enrollment.getApproval();
    }
}
