package com.simbongsa.Backend.dto.response;

import com.simbongsa.Backend.entity.Approval;
import com.simbongsa.Backend.entity.Enrollment;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class EnrollDetailResponse {

    // board data
    private String title;

    private String content;

    private LocalDate dueDay;

    private LocalDate startDate;

    private LocalDate endDate;

    private String area;

    private String detailArea;

    private String boardImage;

    // enrollment data
    private Long enrollId;

    @Enumerated(EnumType.STRING)
    private Approval approval;

    // member data
    private String username;


    public EnrollDetailResponse(Enrollment enrollment) {
        this.title = enrollment.getBoard().getTitle();
        this.dueDay = enrollment.getBoard().getDueDay();
        this.startDate = enrollment.getBoard().getStartDate();
        this.endDate = enrollment.getBoard().getEndDate();
        this.area = enrollment.getBoard().getArea();
        this.detailArea = enrollment.getBoard().getDetailArea();
        this.boardImage = enrollment.getBoard().getBoardImage();
        this.content = enrollment.getBoard().getContent();

        this.enrollId = enrollment.getId();
        this.approval = enrollment.getApproval();

        this.username = enrollment.getMember().getUsername();
    }
}
