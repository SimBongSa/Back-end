package com.simbongsa.Backend.dto.request;

import com.simbongsa.Backend.entity.Tag;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import java.util.List;

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

    @NotEmpty(message = "해시태그를 선택하세요.")
    private List<Tag> tags;
}
