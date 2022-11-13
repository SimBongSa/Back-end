package com.simbongsa.Backend.dto.response;

import com.simbongsa.Backend.entity.Approval;
import com.simbongsa.Backend.entity.Volunteer;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class VolunteerResponse {

    private String username;

    private Approval approval;

    public VolunteerResponse(Volunteer volunteer) {
        this.username = volunteer.getMember().getUsername();
        this.approval = volunteer.getApproval();
    }
}
