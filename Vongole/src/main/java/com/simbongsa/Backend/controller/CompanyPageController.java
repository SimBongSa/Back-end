package com.simbongsa.Backend.controller;

import com.simbongsa.Backend.dto.request.CompanyUpdateRequest;
import com.simbongsa.Backend.dto.response.*;
import com.simbongsa.Backend.entity.UserDetailsImpl;
import com.simbongsa.Backend.service.CompanyPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/companypage")
// TODO secured 어노테이션 공부
//@Secured()
public class CompanyPageController {
    private final CompanyPageService companyPageService;

    /**
     * 내 프로필 정보 조회
     */
    @GetMapping()
    public ResponseDto<CompanyResponse> getMyProfile(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return companyPageService.getMyProfile(userDetails.getMember());
    }


    /**
     * 내 프로필 정보 수정
     */
    @PutMapping()
    public ResponseDto<MsgResponse> updateMyProfile(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                        @ModelAttribute CompanyUpdateRequest companyUpdateRequest) throws IOException {
        return companyPageService.updateMyProfile(userDetails.getMember(), companyUpdateRequest);
    }


    /**
     * 내가 작성한 게시물 조회
     */
    @GetMapping("/boards")
    public ResponseDto<List<BoardResponse>> getMyBoards(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                        @RequestParam(name = "page", defaultValue = "1") int page,
                                                        @RequestParam(name = "size", defaultValue = "4") int size) {
        return companyPageService.getMyBoards(userDetails.getMember(), page - 1, size);
    }

    /**
     * 내 게시물 지원자 전체 조회
     */
    @GetMapping("/applicants")
    public ResponseDto<List<EnrollDetailResponse>> getVolunteers(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                                 @RequestParam(name = "page", defaultValue = "1") int page,
                                                                 @RequestParam(name = "size", defaultValue = "4") int size) {
        return companyPageService.getVolunteers(userDetails.getMember(), page - 1, size);
    }

    /**
     * 게시물 별 봉사 활동 지원자 목록(안쓸 수도 있음)
     */
    @GetMapping("/boards/{boardId}")
    public ResponseDto<List<EnrollResponse>> getVolunteersByBoardId(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                                    @PathVariable Long boardId,
                                                                    @RequestParam(name = "page", defaultValue = "1") int page,
                                                                    @RequestParam(name = "size", defaultValue = "4") int size) {
        return companyPageService.getVolunteersByBoardId(userDetails.getMember(), boardId, page - 1, size);
    }


    /**
     * 지원자 승인
     */
    @PutMapping("/approve/{enrollId}")
    public ResponseDto<MsgResponse> approveMember(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                  @PathVariable Long enrollId) {
        return companyPageService.approveMember(userDetails.getMember(), enrollId);
    }

    /**
     * 지원자 거절
     */
    @PutMapping("/disapprove/{enrollId}")
    public ResponseDto<MsgResponse> disapproveMember(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                     @PathVariable Long enrollId) {
        return companyPageService.disapproveMember(userDetails.getMember(), enrollId);
    }
}
