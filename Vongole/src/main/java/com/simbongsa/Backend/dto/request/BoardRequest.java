package com.simbongsa.Backend.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;

@Getter
@AllArgsConstructor
public class BoardRequest {

    @NotBlank(message = "빈 칸을 채워 주세요.")
    private String title;

    @NotBlank(message = "빈 칸을 채워 주세요.")
    private String content;

    private MultipartFile boardImage;

    @NotBlank(message = "봉사 활동 날짜를 정해 주세요.")
    private String dueDay;

    @NotBlank(message = "지원 시작 날짜를 정해 주세요.")
    private String startDate;

    @NotBlank(message = "지원 마감 날짜를 정해 주세요.")
    private String endDate;

    @NotBlank(message = "봉사 활동 위치를 정해 주세요.")
    private String area;

    @NotBlank(message = "상세 주소를 작성해 주세요.")
    private String detailArea;

    @NotBlank(message = "봉사 활동 종류를 선택해 주세요.")
    private String category;
}
