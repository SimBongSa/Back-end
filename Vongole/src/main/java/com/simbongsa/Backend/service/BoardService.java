package com.simbongsa.Backend.service;

import com.simbongsa.Backend.dto.request.BoardRequest;
import com.simbongsa.Backend.dto.response.ResponseDto;
import com.simbongsa.Backend.entity.Member;
import com.simbongsa.Backend.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    /**
     * 게시물 생성 (관리자만 생성 가능)
     * @param member
     * @param boardRequest
     * @param multipartFile
     */
    public ResponseDto<BoardRequest> createBoard(Member member, BoardRequest boardRequest, MultipartFile multipartFile) {
        // 관리자인지 확인

        // entity 객체 생성 후 db에 저장

        // dto로 변환 후 리턴

        return null;
    }
}


