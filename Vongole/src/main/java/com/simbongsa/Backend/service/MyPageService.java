package com.simbongsa.Backend.service;

import com.simbongsa.Backend.dto.request.MyUpdateRequest;
import com.simbongsa.Backend.dto.response.*;
import com.simbongsa.Backend.entity.Approval;
import com.simbongsa.Backend.entity.Comment;
import com.simbongsa.Backend.entity.Enrollment;
import com.simbongsa.Backend.entity.Member;
import com.simbongsa.Backend.repository.CommentRepository;
import com.simbongsa.Backend.repository.EnrollRepository;
import com.simbongsa.Backend.util.Check;
import com.simbongsa.Backend.util.S3Uploader;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MyPageService {

    private final S3Uploader s3Uploader;

    private final CommentRepository commentRepository;

    private final EnrollRepository enrollRepository;

    private final Check check;
    public ResponseDto<MyResponse> getMyProfile(Member member) {
        check.isNotMember(member);
        return ResponseDto.success(new MyResponse(member));
    }

    @Transactional
    public ResponseDto<MyResponse> updateMyProfile(Member member, MyUpdateRequest myUpdateRequest) throws IOException {
        check.isNotMember(member);

        String profileImage = (myUpdateRequest.getProfileImage().getOriginalFilename().equals(""))?
                null:s3Uploader.uploadFiles(myUpdateRequest.getProfileImage(), "member");
        member.myupdate(myUpdateRequest, profileImage);

        return ResponseDto.success(new MyResponse(member));
    }
    public ResponseDto<List<CommentResponse>> getMyComments(Member member,int page,int size) {
        Pageable pageable = PageRequest.of(page,size);
        Page<Comment> comments = commentRepository.findAllByMember(member,pageable);
        List<CommentResponse> commentResponses = new ArrayList<>();
        for (Comment comment : comments) {
            commentResponses.add(new CommentResponse(comment));
        }
        return ResponseDto.success(commentResponses);
    }

    public ResponseDto<List<BoardResponse>> getMyEnroll(Member member,int page,int size) {
        Pageable pageable = PageRequest.of(page,size);
        Page<Enrollment> enrollments = enrollRepository.findAllByMember(member,pageable);
        List<BoardResponse> boardResponses = new ArrayList<>();
        for (Enrollment enrollment : enrollments) {
            boardResponses.add(new BoardResponse(enrollment.getBoard()));
        }

        return ResponseDto.success(boardResponses);
    }
    public ResponseDto<List<BoardResponse>> getMyEnrollWait(Member member,int page,int size) {
        Pageable pageable = PageRequest.of(page,size);
        Page<Enrollment> enrollments = enrollRepository.findAllByMember(member,pageable);
        List<BoardResponse> boardResponses = new ArrayList<>();
        for (Enrollment enrollment : enrollments) {
            if(enrollment.getApproval().equals(Approval.WAITING)){
                boardResponses.add(new BoardResponse(enrollment.getBoard()));
            }
        }

        return ResponseDto.success(boardResponses);
    }
    public ResponseDto<List<BoardResponse>> getMyEnrollPass(Member member,int page,int size) {
        Pageable pageable = PageRequest.of(page,size);
        Page<Enrollment> enrollments = enrollRepository.findAllByMember(member,pageable);

        List<BoardResponse> boardResponses = new ArrayList<>();
        for (Enrollment enrollment : enrollments) {
            if(enrollment.getApproval().equals(Approval.PASS)){
                boardResponses.add(new BoardResponse(enrollment.getBoard()));
            }
        }

        return ResponseDto.success(boardResponses);
    }


    public ResponseDto<List<BoardResponse>> getMyEnrollFail(Member member,int page,int size) {
        Pageable pageable = PageRequest.of(page,size);
        Page<Enrollment> enrollments = enrollRepository.findAllByMember(member,pageable);
        List<BoardResponse> boardResponses = new ArrayList<>();
        for (Enrollment enrollment : enrollments) {
            if(enrollment.getApproval().equals(Approval.FAIL)){
                boardResponses.add(new BoardResponse(enrollment.getBoard()));
            }
        }
        return ResponseDto.success(boardResponses);
    }

}
