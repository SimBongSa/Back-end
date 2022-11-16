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
public class CompanyPageController {

    private final CompanyPageService companyPageService;

    /**
     * 내 프로필 정보 조회
     * @param userDetails
     * @return
     */
    @GetMapping()
    public ResponseDto<CompanyResponse> getMyProfile(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return companyPageService.getMyProfile(userDetails.getMember());
    }


    @PutMapping()
    public ResponseDto<CompanyResponse> updateMyProfile(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                        @ModelAttribute CompanyUpdateRequest companyUpdateRequest) throws IOException {
        return companyPageService.updateMyProfile(userDetails.getMember(), companyUpdateRequest);
    }


    /**
     * 내가 작성한 게시물 조회
     * @param userDetails
     * @return
     */
    @GetMapping("/boards")
    public ResponseDto<List<BoardResponse>> getMyBoards(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return companyPageService.getMyBoards(userDetails.getMember());
    }

    /**
     * 봉사 활동 지원자 목록
     * @param userDetails
     * @param boardId
     * @return
     */
    @GetMapping("/boards/{boardId}")
    public ResponseDto<List<VolunteerResponse>> getVolunteers(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                              @PathVariable Long boardId) {
        return companyPageService.getVolunteers(userDetails.getMember(),boardId);
    }


    /**
     * 지원자 승인
     * @param userDetails
     * @param memberId
     * @return
     */
    @PutMapping("/approve/{memberId}")
    public ResponseDto<MsgResponse> approveMember(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                  @PathVariable Long memberId) {
        return companyPageService.approveMember(userDetails.getMember(), memberId);
    }

    /**
     * 지원자 거절
     * @param userDetails
     * @param memberId
     * @return
     */
    @PutMapping("/disapprove/{memberId}")
    public ResponseDto<MsgResponse> disapproveMember(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                     @PathVariable Long memberId) {
        return companyPageService.disapproveMember(userDetails.getMember(), memberId);
    }
}
