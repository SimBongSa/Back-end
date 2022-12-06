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
@NoArgsConstructor
//@AllArgsConstructor
@Setter
public class BoardUpdateRequest {

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

    private List<Tag> tags;

    public BoardUpdateRequest(String title, String content, String dueDay, String startDate, String endDate,
                        String area, String detailArea, List<Tag> tags) {
        this.title = title;
        this.content = content;
        this.boardImage = null;
        this.dueDay = dueDay;
        this.startDate = startDate;
        this.endDate = endDate;
        this.area = area;
        this.detailArea = detailArea;
        this.tags = tags;
    }
}
