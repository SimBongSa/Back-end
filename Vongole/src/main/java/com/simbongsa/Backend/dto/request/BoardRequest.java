package com.simbongsa.Backend.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
public class BoardRequest {

    @NotEmpty(message = "빈 칸을 채워 주세요.")
    private String title;

    @NotEmpty(message = "빈 칸을 채워 주세요.")
    private String content;

    private MultipartFile boardImage;

    @NotEmpty(message = "봉사 활동 날짜를 정해 주세요.")
    private String dueDay;

    @NotEmpty(message = "지원 시작 날짜를 정해 주세요.")
    private String startDate;

    @NotEmpty(message = "지원 마감 날짜를 정해 주세요.")
    private String endDate;

    @NotEmpty(message = "봉사 활동 위치를 정해 주세요.")
    private String area;

    @NotEmpty(message = "상세 주소를 작성해 주세요.")
    private String detailArea;

//    @NotBlank(message = "봉사 활동 종류를 선택해 주세요.")
//    private String category;
}
